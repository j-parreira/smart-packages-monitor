<template>
  <div class="w-full p-3 bg-slate-50 border-b top-0">
    <Breadcrumb>
      <BreadcrumbList>
        <BreadcrumbItem>
          <BreadcrumbLink @click="$router.push('/')"> Home </BreadcrumbLink>
        </BreadcrumbItem>
        <BreadcrumbSeparator />
        <BreadcrumbItem>
          <BreadcrumbLink v-if="storeAuth.user?.role!=='Customer'" @click="$router.push('/orders')"> Orders </BreadcrumbLink>
          <BreadcrumbLink v-else @click="$router.push('/my-orders')"> My Orders </BreadcrumbLink>
        </BreadcrumbItem>
        <BreadcrumbSeparator />
        <BreadcrumbItem>
          <BreadcrumbPage>{{ order.id }}</BreadcrumbPage>
        </BreadcrumbItem>
      </BreadcrumbList>
    </Breadcrumb>
  </div>
  <div class="mb-auto sm:w-full p-3">
    <div v-if="!isLoading">
      <h1 class="pb-3">Employee Details</h1>
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
                <Badge :variant="p.type === 'ELECTRONICS' ? 'blue' : p.type === 'FROZEN_FOOD' ? 'yellow' : p.type === 'FRUITS' ? 'green' : 'red'">
                  {{ p.type === 'ELECTRONICS' ? 'Eletronics' : p.type === 'FROZEN_FOOD' ? 'Frozen Food' : p.type === 'FRUITS' ? 'Fruits' : 'Drinks' }}
                </Badge>
              </TableCell>
              <TableCell>{{ order.volumes.filter((v) => v.id == p.id)[0]?.volumeCode ?? '-' }}</TableCell>
              <TableCell class="flex justify-between gap-3">
                <Badge :variant="order.volumes.filter((v) => v.id == p.id)[0]?.status === 'PROCESSING' ? 'blue' : order.volumes.filter((v) => v.id == p.id)[0]?.status === 'DISPATCHED' ? 'green' : 'outline'">
                  {{ order.volumes.filter((v) => v.id == p.id)[0]?.status ?? 'PENDING' }}
                </Badge>
                <div v-if="order.volumes.filter((v) => v.id == p.id)[0]" class="text-nowrap flex flex-row items-center justify-end text-xs text-slate-500 hover:text-blue-600 cursor-pointer" @click="$router.push(`/volumes/${order.volumes.filter((v) => v.id == p.id)[0]?.id}`)">
                  Volume Details
                  <Icon name="stash:new-window-page-light" class="w-6 h-6" mode="svg" />
                </div>
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
import { onMounted } from 'vue'
import { useAPI } from '~/store/storeAPI'
import { useAuthStore } from '~/store/storeAuth'
const storeAuth = useAuthStore()
const route = useRoute()
const api = useAPI()
const order = ref([])
const isLoading = ref(true)

const fetchData = async () => {
  order.value = await api.getOrder(route.params.id)
}

onMounted(async () => {
  fetchData()
  isLoading.value = false
})
</script>
