<template>
  <div class="w-full p-3 bg-slate-50 border-b top-0">
    <Breadcrumb>
      <BreadcrumbList>
        <BreadcrumbItem>
          <BreadcrumbLink @click="$router.push('/')"> Home </BreadcrumbLink>
        </BreadcrumbItem>
        <BreadcrumbSeparator />
        <BreadcrumbItem>
          <BreadcrumbLink @click="$router.push('/employees')"> Employees </BreadcrumbLink>
        </BreadcrumbItem>
        <BreadcrumbSeparator />
        <BreadcrumbItem>
          <BreadcrumbPage>{{ employee.name }}</BreadcrumbPage>
        </BreadcrumbItem>
      </BreadcrumbList>
    </Breadcrumb>
  </div>
  <div class="mb-auto sm:w-full p-3">
    <div v-if="!isLoading">
      <h1 class="pb-3">Employee Details</h1>
      <div class="border rounded-lg flex items-center">
        <Table>
          <TableBody>
            <TableRow>
              <TableHead class="bg-slate-50 border-r text-right">ID</TableHead>
              <TableCell class="border-r">{{ employee.id }}</TableCell>
            </TableRow>
            <TableRow>
              <TableHead class="bg-slate-50 border-r text-right">Name</TableHead>
              <TableCell class="border-r">{{ employee.name }}</TableCell>
            </TableRow>
            <TableRow>
              <TableHead class="bg-slate-50 border-r text-right">Email</TableHead>
              <TableCell class="border-r">{{ employee.email }}</TableCell>
            </TableRow>
            <TableRow>
              <TableHead class="bg-slate-50 border-r text-right">Warehouse</TableHead>
              <TableCell class="border-r">{{ employee.warehouseId }}</TableCell>
            </TableRow>
          </TableBody>
        </Table>
        <div>
          <img src="/assets/img/avatar-none.png" class="w-1/2 mx-auto rounded-full" />
        </div>
      </div>

      <h1 class="py-3">Employee Processed Volumes</h1>
      <div class="border rounded-lg" v-if="employee.volumes.length > 0">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead class="w-10">ID</TableHead>
              <TableHead>Order</TableHead>
              <TableHead>Type</TableHead>
              <TableHead>Volume Code</TableHead>
              <TableHead>Product</TableHead>
              <TableHead>Dispatched At</TableHead>
              <TableHead>Updated At</TableHead>
              <TableHead colspan="2">Status</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            <TableRow v-for="v in employee.volumes" :key="v.id">
              <TableCell>{{ v.id }}</TableCell>
              <TableCell class="text-nowrap flex flex-row items-center hover:text-blue-600 cursor-pointer" @click="$router.push(`/orders/${v.orderId}`)">
                {{ v.orderId }}
                <Icon name="stash:new-window-page-light" class="w-6 h-6" />
              </TableCell>
              <TableCell>{{ v.type }}</TableCell>
              <TableCell>{{ v.volumeCode }}</TableCell>
              <TableCell>{{ products.filter((p) => p.id == v.productId)[0].name }}</TableCell>
              <TableCell>{{ v.dispatchedAt }}</TableCell>
              <TableCell>{{ v.updatedAt == v.dispatchedAt ? '-' : v.updatedAt }}</TableCell>
              <TableCell>
                <Badge :variant="v.status === 'PROCESSING' ? 'blue' : v.status === 'DISPATCHED' ? 'green' : 'outline'">
                  {{ v.status }}
                </Badge>
              </TableCell>
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
      <div v-else class="flex items-center justify-center h-24">
        <div class="text-slate-500">No volumes processed yet</div>
      </div>
    </div>
    <div v-else class="w-full h-full flex items-center justify-center">
      <Icon name="eos-icons:three-dots-loading" class="text-blue-950 w-12 h-12" />
    </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue'
import Badge from '~/components/ui/badge/Badge.vue'
import { useAPI } from '~/store/storeAPI'

const route = useRoute()
const api = useAPI()
const isLoading = ref(true)
const employee = ref([])
const products = ref([])

const fetchData = async () => {
  employee.value = await api.getEmployee(route.params.id)
  products.value = await api.getProducts()
}
onMounted(async () => {
  await fetchData()
  isLoading.value = false
})
</script>
