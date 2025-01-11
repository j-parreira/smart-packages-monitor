<template>
  <div class="w-full p-3 bg-slate-50 border-b flex justify-between items-center">
    <Breadcrumb>
      <BreadcrumbList>
        <BreadcrumbItem>
          <BreadcrumbLink @click="$router.push('/')"> Home </BreadcrumbLink>
        </BreadcrumbItem>
        <BreadcrumbSeparator />
        <BreadcrumbItem>
          <BreadcrumbLink @click="$router.push('/products')"> Products </BreadcrumbLink>
        </BreadcrumbItem>
        <BreadcrumbSeparator />
        <BreadcrumbItem>
          <BreadcrumbPage>{{ route.params.id }}</BreadcrumbPage>
        </BreadcrumbItem>
        <BreadcrumbSeparator />
        <BreadcrumbItem>
          <BreadcrumbPage>Volumes</BreadcrumbPage>
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
    <div v-if="isLoading" class="flex justify-center items-center">
      <Icon name="eos-icons:three-dots-loading" class="text-blue-950 w-12 h-12" />
    </div>
    <div v-else>
      <h1 class="text-slate-700 pb-3">List of Volumes</h1>
      <div v-if="volumes.volumes.length > 0" class="border rounded-lg">
        <Table class="overflow-x-scroll">
          <TableRow>
            <TableHead class="w-10">ID</TableHead>
            <TableHead>Order</TableHead>
            <TableHead>Type</TableHead>
            <TableHead>Volume Code</TableHead>
            <TableHead>Status</TableHead>
            <TableHead>Dispatched By</TableHead>
            <TableHead>Dispatched At</TableHead>
            <TableHead colspan="2">Updated At</TableHead>
          </TableRow>
          <TableBody>
            <TableRow v-for="v in volumes.volumes" :key="v.id">
              <TableCell class="font-medium"> {{ v.id }} </TableCell>
              <TableCell class=" flex flex-row items-center hover:text-blue-600 cursor-pointer" @click="$router.push(`/orders/${v.orderId}`)">
                {{ v.orderId }}
                <Icon name="stash:new-window-page-light" class="w-6 h-6" />
              </TableCell>
              <TableCell> {{ v.type }} </TableCell>
              <TableCell> {{ v.volumeCode }} </TableCell>
              <TableCell>
                <Badge :variant="v.status === 'PROCESSING' ? 'blue' : v.status === 'DISPATCHED' ? 'green' : v.status === 'DAMAGED' ? 'red': 'outline'">
                  {{ v.status ?? 'PENDING' }}
                </Badge>
              </TableCell>
              <TableCell class="hover:text-blue-600 cursor-pointer text-slate-600" @click="$router.push(`/employees/${v.dispatchedByEmployeeId}`)">{{ employees.filter((c) => c.id == v.dispatchedByEmployeeId)[0].name }}</TableCell>
              <TableCell>{{ format(parse(v.dispatchedAt, 'dd-MM-yyyy HH:mm:ss', new Date()), 'dd-MM-yyyy HH:mm') }}</TableCell>
              <TableCell>{{ format(parse(v.updatedAt, 'dd-MM-yyyy HH:mm:ss', new Date()), 'dd-MM-yyyy HH:mm') }}</TableCell>
              <TableCell class="flex justify-between gap-2">
                <div class="text-nowrap flex flex-row items-center justify-end text-xs text-slate-500 hover:text-blue-600 cursor-pointer" @click="$router.push(`/volumes/${v.id}`)">
                  Show Details
                  <Icon name="stash:new-window-page-light" class="w-6 h-6" />
                </div>
              </TableCell>
            </TableRow>
          </TableBody>
        </Table>
      </div>
      <div v-else class="flex justify-center items-center h-40">
        <p class="text-slate-500">No volumes with product '{{ route.params.id }}' found</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { format, parse } from 'date-fns'
import { onMounted } from 'vue'
import { useAPI } from '~/store/storeAPI'

const volumes = ref([])
const employees = ref([])
const route = useRoute()

const isLoading = ref(true)
const api = useAPI()

const fetchData = async () => {
  volumes.value = await api.getProductVolumes(route.params.id)
  employees.value = await api.getEmployees()
}
onMounted(async () => {
  await fetchData()
  isLoading.value = false
})
</script>
