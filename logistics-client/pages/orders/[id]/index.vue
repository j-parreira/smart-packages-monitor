<template>
  <div class="w-full p-3 bg-slate-50 border-b top-0 flex justify-between items-center">
    <Breadcrumb>
      <BreadcrumbList>
        <BreadcrumbItem>
          <BreadcrumbLink @click="$router.push('/')"> Home </BreadcrumbLink>
        </BreadcrumbItem>
        <BreadcrumbSeparator />
        <BreadcrumbItem>
          <BreadcrumbLink v-if="storeAuth.user?.role !== 'Customer'" @click="$router.push('/orders')"> Orders
          </BreadcrumbLink>
          <BreadcrumbLink v-else @click="$router.push('/my-orders')"> My Orders </BreadcrumbLink>
        </BreadcrumbItem>
        <BreadcrumbSeparator />
        <BreadcrumbItem>
          <BreadcrumbPage>{{ order.id }}</BreadcrumbPage>
        </BreadcrumbItem>
      </BreadcrumbList>
    </Breadcrumb>
    <Breadcrumb>
      <BreadcrumbList>
        <BreadcrumbItem>
          <BreadcrumbLink @click="$router.back"> Back </BreadcrumbLink>
        </BreadcrumbItem>
      </BreadcrumbList>
    </Breadcrumb>
  </div>
  <div class="mb-auto sm:w-full p-3">
    <div v-if="!isLoading">
      <h1 class="pb-3">Order Details</h1>
      <div class="border rounded-lg">
        <Table>
          <TableBody>
            <TableRow>
              <TableHead class="bg-slate-50 w-1/2 border-r text-right">ID</TableHead>
              <TableCell>{{ order.id }}</TableCell>
            </TableRow>
            <TableRow>
              <TableHead class="bg-slate-50 w-1/2 border-r text-right">Status</TableHead>
              <TableCell>{{ order.status }}</TableCell>
            </TableRow>
            <TableRow>
              <TableHead class="bg-slate-50 w-1/2 border-r text-right">Created At</TableHead>
              <TableCell>{{ order.createdAt }}</TableCell>
            </TableRow>
            <TableRow>
              <TableHead class="bg-slate-50 w-1/2 border-r text-right">Payment Type</TableHead>
              <TableCell>{{ order.paymentType }}</TableCell>
            </TableRow>
          </TableBody>
        </Table>
      </div>
      <div class="mt-3 border rounded-lg">
        <Table>
          <TableBody>
            <TableRow>
              <TableHead colspan="5" class="bg-slate-50 text-center">Products</TableHead>
            </TableRow>
            <TableRow>
              <TableHead class="bg-slate-50">Product ID</TableHead>
              <TableHead class="bg-slate-50">Product Name</TableHead>
              <TableHead class="bg-slate-50">Category</TableHead>
              <TableHead class="bg-slate-50">Volume</TableHead>
              <TableHead class="bg-slate-50">Status</TableHead>
            </TableRow>
            <TableRow v-for="p in order.products" :key="p.id">
              <TableCell>{{ p.id }}</TableCell>
              <TableCell>{{ p.name }}</TableCell>
              <TableCell>
                {{ p.type === 'ELECTRONICS' ? 'Eletronics' : p.type === 'FROZEN_FOOD' ? 'Frozen Food' : p.type ==='FRUITS' ? 'Fruits' : 'Drinks' }}
              </TableCell>
              <TableCell>{{ order.volumes.find(v => v.productId == p.id)?.volumeCode ?? '-' }}</TableCell>
              <TableCell class="flex justify-between gap-3">
                <Badge class="p-2"
                  :variant="order.volumes.find(v => v.productId == p.id)?.status === 'PROCESSING' ? 'blue' : order.volumes.find(v => v.productId == p.id)?.status === 'DISPATCHED' ? 'green' : order.volumes.find(v => v.productId == p.id)?.status === 'DAMAGED' ? 'red' : 'outline'">
                  {{ order.volumes.find(v => v.productId == p.id)?.status ?? 'PENDING' }}
                </Badge>
                <div v-if="order.volumes.find(v => v.productId == p.id)"
                  class="text-nowrap flex flex-row items-center justify-end text-xs text-slate-500 hover:text-blue-600 cursor-pointer"
                  @click="$router.push(`/volumes/${order.volumes.find(v => v.productId == p.id)?.id}`)">
                  Volume Details
                  <Icon name="stash:new-window-page-light" class="w-6 h-6" mode="svg" />
                </div>
                <Dialog v-else v-if="storeAuth.user?.role !== 'Customer'">
                  <DialogTrigger>
                    <Button variant="blue" class="text-xs" @click="() => {
                      volumeData.volumeType = ''
                      volumeData.sensorsAdded = []
                    }">Dispatch Volume</Button>
                  </DialogTrigger>
                  <DialogContent>
                    <DialogHeader>
                      <DialogTitle>Dispatch volume</DialogTitle>
                      <DialogDescription> Are you sure you want to dispatch this volume? </DialogDescription>
                    </DialogHeader>
                    <div class="grid gap-4 py-4">
                      <div class="grid grid-cols-4 items-center gap-4">
                        <Label class="text-right"> Order ID </Label>
                        <Input disabled class="col-span-3" v-model="order.id" />
                      </div>
                      <div class="grid grid-cols-4 items-center gap-4">
                        <Label class="text-right"> Category </Label>
                        <Input disabled class="col-span-3"
                          :default-value="p.type === 'ELECTRONICS' ? 'Eletronics' : p.type === 'FROZEN_FOOD' ? 'Frozen Food' : p.type === 'FRUITS' ? 'Fruits' : 'Drinks'" />
                      </div>
                      <div class="grid grid-cols-4 items-center gap-4">
                        <Label class="text-right"> Product ID </Label>
                        <Input disabled class="col-span-3" v-model="p.id" />
                      </div>
                      <div class="grid grid-cols-4 items-center gap-4">
                        <Label class="text-right"> Volume Type </Label>
                        <div class="col-span-3">
                          <Select v-model="volumeData.volumeType">
                            <SelectTrigger>
                              <SelectValue placeholder="Select a volume type" />
                            </SelectTrigger>
                            <SelectContent>
                              <SelectGroup>
                                <SelectItem :disabled="p.type != 'FROZEN_FOOD'" value="COOLER_BOX"> Cooler Box
                                </SelectItem>
                                <SelectItem :disabled="p.type != 'ELECTRONICS'" value="CARDBOARD_BOX"> Carboard Box
                                </SelectItem>
                                <SelectItem :disabled="p.type == 'ELECTRONICS' || p.type == 'FROZEN_FOOD'"
                                  value="WOODEN_CRATE"> Wooden Crate </SelectItem>
                              </SelectGroup>
                            </SelectContent>
                          </Select>
                        </div>
                      </div>
                      <div v-if="volumeData.volumeType" class="grid grid-cols-4 items-center gap-4">
                        <Label class="text-right"> Add Sensors </Label>
                        <div class="col-span-3">
                          <ToggleGroup type="multiple" class="flex flex-col items-start"
                            v-model="volumeData.sensorsAdded">
                            <div class="flex items-center w-full gap-1">
                              <ToggleGroupItem value="GPS" variant="outline" class="w-full"
                                :disabled="volumeData.volumeType == 'CARDBOARD_BOX'">GPS</ToggleGroupItem>
                            </div>
                            <div class="flex items-center w-full gap-1">
                              <ToggleGroupItem value="ACCELERATION" variant="outline" class="w-full"
                                :disabled="volumeData.volumeType == 'CARDBOARD_BOX'"> ACCELERATION </ToggleGroupItem>
                              <Popover>
                                <PopoverTrigger as-child>
                                  <Button variant="outline" class="h-10">
                                    <Settings2 class="w-4 h-4 text-muted-foreground cursor-pointer" />
                                  </Button>
                                </PopoverTrigger>
                                <PopoverContent class="w-80">
                                  <div class="grid gap-4">
                                    <div class="space-y-2">
                                      <h4 class="font-medium leading-none">ACCELERATION Sensor</h4>
                                      <p class="text-sm text-muted-foreground">Set the maximum and minimum values for
                                        threshold values.</p>
                                    </div>
                                    <div class="grid gap-3">
                                      <div class="grid grid-cols-3 items-center gap-4">
                                        <Label for="width">Max</Label>
                                        <NumberField v-model="volumeData.sensors[1].maxThreshold" :max="100"
                                          :min="volumeData.sensors[1].minThreshold" class="col-span-2 h-8">
                                          <NumberFieldContent>
                                            <NumberFieldDecrement />
                                            <NumberFieldInput />
                                            <NumberFieldIncrement />
                                          </NumberFieldContent>
                                        </NumberField>
                                      </div>
                                      <div class="grid grid-cols-3 items-center gap-4">
                                        <Label for="maxWidth">Min.</Label>
                                        <NumberField v-model="volumeData.sensors[1].minThreshold" :min="0"
                                          :max="volumeData.sensors[1].maxThreshold" class="col-span-2 h-8">
                                          <NumberFieldContent>
                                            <NumberFieldDecrement />
                                            <NumberFieldInput />
                                            <NumberFieldIncrement />
                                          </NumberFieldContent>
                                        </NumberField>
                                      </div>
                                    </div>
                                  </div>
                                </PopoverContent>
                              </Popover>
                            </div>
                            <div class="flex items-center w-full gap-1">
                              <ToggleGroupItem value="HUMIDITY" variant="outline" class="w-full"> HUMIDITY
                              </ToggleGroupItem>
                              <Popover>
                                <PopoverTrigger as-child>
                                  <Button variant="outline" class="h-10">
                                    <Settings2 class="w-4 h-4 text-muted-foreground cursor-pointer" />
                                  </Button>
                                </PopoverTrigger>
                                <PopoverContent class="w-80">
                                  <div class="grid gap-4">
                                    <div class="space-y-2">
                                      <h4 class="font-medium leading-none">HUMIDITY Sensor</h4>
                                      <p class="text-sm text-muted-foreground">Set the maximum and minimum values for
                                        threshold values (%)</p>
                                    </div>
                                    <div class="grid gap-3">
                                      <div class="grid grid-cols-3 items-center gap-4">
                                        <Label for="width">Max</Label>
                                        <NumberField :format-options="{ style: 'unit', unit: 'percent' }"
                                          v-model="volumeData.sensors[2].maxThreshold" :max="100"
                                          :min="volumeData.sensors[2].minThreshold" class="col-span-2 h-8">
                                          <NumberFieldContent>
                                            <NumberFieldDecrement />
                                            <NumberFieldInput />
                                            <NumberFieldIncrement />
                                          </NumberFieldContent>
                                        </NumberField>
                                      </div>
                                      <div class="grid grid-cols-3 items-center gap-4">
                                        <Label for="maxWidth">Min.</Label>
                                        <NumberField :format-options="{ style: 'unit', unit: 'percent' }"
                                          v-model="volumeData.sensors[2].minThreshold" :min="0"
                                          :max="volumeData.sensors[2].maxThreshold" class="col-span-2 h-8">
                                          <NumberFieldContent>
                                            <NumberFieldDecrement />
                                            <NumberFieldInput />
                                            <NumberFieldIncrement />
                                          </NumberFieldContent>
                                        </NumberField>
                                      </div>
                                    </div>
                                  </div>
                                </PopoverContent>
                              </Popover>
                            </div>
                            <div class="flex items-center w-full gap-1">
                              <ToggleGroupItem value="TEMPERATURE" variant="outline" class="w-full"> TEMPERATURE
                              </ToggleGroupItem>
                              <Popover>
                                <PopoverTrigger as-child>
                                  <Button variant="outline" class="h-10">
                                    <Settings2 class="w-4 h-4 text-muted-foreground cursor-pointer" />
                                  </Button>
                                </PopoverTrigger>
                                <PopoverContent class="w-80">
                                  <div class="grid gap-4">
                                    <div class="space-y-2">
                                      <h4 class="font-medium leading-none">TEMPERATURE Sensor</h4>
                                      <p class="text-sm text-muted-foreground">Set the maximum and minimum values for
                                        threshold values (Cº)</p>
                                    </div>
                                    <div class="grid gap-3">
                                      <div class="grid grid-cols-3 items-center gap-4">
                                        <Label for="width">Max</Label>
                                        <NumberField 
                                          :format-options="{ style: 'unit', unit: 'celsius' }"
                                          v-model="volumeData.sensors[3].maxThreshold" :max="100"
                                          :min="volumeData.sensors[3].minThreshold" class="col-span-2 h-8">
                                          <NumberFieldContent>
                                            <NumberFieldDecrement />
                                            <NumberFieldInput />
                                            <NumberFieldIncrement />
                                          </NumberFieldContent>
                                        </NumberField>
                                      </div>
                                      <div class="grid grid-cols-3 items-center gap-4">
                                        <Label for="maxWidth">Min.</Label>
                                        <NumberField 
                                        :format-options="{ style: 'unit', unit: 'celsius' }"
                                        v-model="volumeData.sensors[3].minThreshold" :min="0"
                                        :max="volumeData.sensors[3].maxThreshold" class="col-span-2 h-8">
                                          <NumberFieldContent>
                                            <NumberFieldDecrement />
                                            <NumberFieldInput />
                                            <NumberFieldIncrement />
                                          </NumberFieldContent>
                                        </NumberField>
                                      </div>
                                    </div>
                                  </div>
                                </PopoverContent>
                              </Popover>
                            </div>
                          </ToggleGroup>
                        </div>
                      </div>
                    </div>
                    <DialogFooter>
                      <DialogClose as-child>
                        <Button variant="green" @click="dispatchVolume(p.id)"
                          :disabled="volumeData.sensorsAdded.length == 0"> Dispatch Volume </Button>
                      </DialogClose>
                    </DialogFooter>
                  </DialogContent>
                </Dialog>
              </TableCell>
            </TableRow>
          </TableBody>
        </Table>
      </div>
    </div>
    <div v-else class="w-full h-full flex items-center justify-center">
      <Icon name="eos-icons:three-dots-loading" class="text-blue-950 w-12 h-12" />
    </div>
  </div>
</template>

<script setup>
import { Dialog, DialogClose, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger } from '@/components/ui/dialog'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { Popover, PopoverContent, PopoverTrigger } from '@/components/ui/popover'
import { ToggleGroup, ToggleGroupItem } from '@/components/ui/toggle-group'
import { Settings2, X } from 'lucide-vue-next'
import { onMounted, watch } from 'vue'
import { useAPI } from '~/store/storeAPI'
import { useAuthStore } from '~/store/storeAuth'
import { NumberField, NumberFieldContent, NumberFieldDecrement, NumberFieldIncrement, NumberFieldInput } from '@/components/ui/number-field'
import { autoResetRef } from '@vueuse/core'
const storeAuth = useAuthStore()
const route = useRoute()
const api = useAPI()
const order = ref([])
const isLoading = ref(true)

const volumeData = ref({
  orderId: parseInt(route.params.id),
  volumeType: null,
  sensorsAdded: [],
  sensors: [
    {
      type: 'GPS',
      maxThreshold: 0,
      minThreshold: 0
    },
    {
      type: 'ACCELERATION',
      maxThreshold: 80,
      minThreshold: 30
    },
    {
      type: 'HUMIDITY',
      maxThreshold: 50,
      minThreshold: 20
    },
    {
      type: 'TEMPERATURE',
      maxThreshold: 30,
      minThreshold: 10
    }
  ],
})

watch(
  () => volumeData.value.volumeType,
  (newValue) => {
    updateSensors(newValue)
  }
)

const updateSensors = (newValue) => {
  if (newValue === 'CARDBOARD_BOX') {
    volumeData.value.sensorsAdded = ['GPS', 'ACCELERATION']
  }
}

const dispatchVolume = async (productId) => {
  volumeData.value.productId = productId
  volumeData.value.dispatchedByEmployeeId = storeAuth.user?.id
  console.log(volumeData.value);
  
  await api.dispatchVolume(volumeData.value)
  fetchData()
}

const fetchData = async () => {
  order.value = await api.getOrder(route.params.id)
}

onMounted(async () => {
  fetchData()
  isLoading.value = false
})
</script>
