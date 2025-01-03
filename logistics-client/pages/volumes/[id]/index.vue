<template>
  <div class="w-full p-3 bg-slate-50 border-b top-0">
    <Breadcrumb>
      <BreadcrumbList>
        <BreadcrumbItem>
          <BreadcrumbLink @click="$router.push('/')"> Home </BreadcrumbLink>
        </BreadcrumbItem>
        <BreadcrumbSeparator />
        <BreadcrumbItem>
          <BreadcrumbPage>Volumes</BreadcrumbPage>
        </BreadcrumbItem>
        <BreadcrumbSeparator />
        <BreadcrumbItem>
          <BreadcrumbPage>{{ volume.id }}</BreadcrumbPage>
        </BreadcrumbItem>
      </BreadcrumbList>
    </Breadcrumb>
  </div>
  <div class="mb-auto sm:w-full p-3">
    <div v-if="!isLoading">
      <h1 class="pb-3">Volume Details</h1>
      <div class="border rounded-lg">
        <Table>
          <TableBody>
            <TableRow>
              <TableHead class="bg-slate-50 w-1/2 border-r text-right">ID</TableHead>
              <TableCell>{{ volume.id }}</TableCell>
            </TableRow>
            <TableRow>
              <TableHead class="bg-slate-50 w-1/2 border-r text-right">Type</TableHead>
              <TableCell>{{ volume.type }}</TableCell>
            </TableRow>
            <TableRow>
              <TableHead class="bg-slate-50 w-1/2 border-r text-right">Volume Code</TableHead>
              <TableCell>{{ volume.volumeCode }}</TableCell>
            </TableRow>
            <TableRow>
              <TableHead class="bg-slate-50 w-1/2 border-r text-right">Status</TableHead>
              <TableCell>
                <Badge :variant="volume.status === 'PROCESSING' ? 'blue' : volume.status === 'DISPATCHED' ? 'green' : 'outline'">
                  {{ volume.status ?? 'PENDING' }}
                </Badge>
              </TableCell>
            </TableRow>
            <TableRow>
              <TableHead class="bg-slate-50 w-1/2 border-r text-right">Dispatched At</TableHead>
              <TableCell>{{ volume.dispatchedAt }}</TableCell>
            </TableRow>
            <TableRow>
              <TableHead class="bg-slate-50 w-1/2 border-r text-right">Updated At</TableHead>
              <TableCell>{{ volume.updatedAt }}</TableCell>
            </TableRow>
            <TableRow>
              <TableHead class="bg-slate-50 w-1/2 border-r text-right">Product</TableHead>
              <TableCell>{{ product?.name }}</TableCell>
            </TableRow>
          </TableBody>
        </Table>
      </div>
      <div class="mt-3 border rounded-lg">
        <Table>
          <TableBody>
            <TableRow>
              <TableHead colspan="7" class="bg-slate-50 text-center">Sensors</TableHead>
            </TableRow>
            <TableRow>
              <TableHead class="bg-slate-50">Sensor ID</TableHead>
              <TableHead class="bg-slate-50">Type</TableHead>
              <TableHead class="bg-slate-50">Status</TableHead>
              <TableHead class="bg-slate-50">Max Threshold</TableHead>
              <TableHead class="bg-slate-50">Min Threshold</TableHead>
              <TableHead class="bg-slate-50">Last Reading</TableHead>
              <TableHead class="bg-slate-50">Last Reading Timestamp</TableHead>
            </TableRow>
            <TableRow v-for="s in volume.sensors" :key="s.id">
              <TableCell>{{ s.id }}</TableCell>
              <TableCell>{{ s.type }}</TableCell>
              <TableCell>
                <Badge :variant="s.active ? 'green' : 'red'">
                  {{ s.active ? 'Online' : 'Offline' }}
                </Badge>
              </TableCell>
              <TableCell>{{ s.maxThreshold || '-'}}</TableCell>
              <TableCell>{{ s.minThreshold || '-'}}</TableCell>
              <TableCell>{{ s.type=='GPS' ? `LAT ${s.lastReading?.valueOne} LONG ${s.lastReading?.valueTwo}` : s.lastReading?.valueOne}}</TableCell>
              <TableCell>{{ s.lastReading?.timestamp }}</TableCell>
              <!-- <TableCell>{{ }}</TableCell> -->
              <!-- 
              <TableCell>
                <Badge :variant="s.type === 'ELECTRONICS' ? 'blue' : s.type === 'FROZEN_FOOD' ? 'yellow' : s.type === 'FRUITS' ? 'green' : 'red'">
                  {{ s.type === 'ELECTRONICS' ? 'Eletronics' : s.type === 'FROZEN_FOOD' ? 'Frozen Food' : s.type === 'FRUITS' ? 'Fruits' : 'Drinks' }}
                </Badge>
              </TableCell>
              <TableCell>{{ order.volumes.filter((v) => v.id == s.id)[0]?.volumeCode ?? '-' }}</TableCell>
              <TableCell class="flex justify-between gap-3">
                <Badge :variant="order.volumes.filter((v) => v.id == s.id)[0]?.status === 'PROCESSING' ? 'blue' : order.volumes.filter((v) => v.id == s.id)[0]?.status === 'DISPATCHED' ? 'green' : 'outline'">
                  {{ order.volumes.filter((v) => v.id == s.id)[0]?.status ?? 'PENDING' }}
                </Badge>
                <div v-if="order.volumes.filter((v) => v.id == s.id)[0]" class="text-nowrap flex flex-row items-center justify-end text-xs text-slate-500 hover:text-blue-600 cursor-pointer" @click="$router.push(`/volumes/${order.volumes.filter((v) => v.id == s.id)[0]?.id}`)">
                  Volume Details
                  <Icon name="stash:new-window-page-light" class="w-6 h-6" mode="svg" />
                </div> 
              </TableCell>
              -->
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
import { onMounted } from 'vue'
import { useAPI } from '~/store/storeAPI'

const route = useRoute()
const api = useAPI()
const volume = ref([])
const isLoading = ref(true)
const product = ref(null)

const fetchData = async () => {
  volume.value = await api.getVolume(route.params.id)
  product.value = await api.getProduct(volume.value.productId)
  for (const s of volume.value.sensors) {
    s.lastReading = await api.getSensorLastReading(s.id)
  }
}

onMounted(async () => {
  fetchData()
  isLoading.value = false
})
</script>
