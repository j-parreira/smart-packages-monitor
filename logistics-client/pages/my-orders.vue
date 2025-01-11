<template>
  <div class="w-full p-3 bg-slate-50 border-b flex justify-between items-center">
    <Breadcrumb>
      <BreadcrumbList>
        <BreadcrumbItem>
          <BreadcrumbLink @click="$router.push('/')"> Home </BreadcrumbLink>
        </BreadcrumbItem>
        <BreadcrumbSeparator />
        <BreadcrumbItem>
          <BreadcrumbPage>My Orders</BreadcrumbPage>
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
      <h1 class="text-slate-700 pb-3">My Orders</h1>
      <div class="border rounded-lg">
        <Table>
          <TableRow>
            <TableHead class="w-[100px]">ID</TableHead>
            <TableHead>Status</TableHead>
            <TableHead>Order Date</TableHead>
            <TableHead>Order Time</TableHead>
            <TableHead>Payment Type</TableHead>
            <TableHead></TableHead>
          </TableRow>
          <TableBody>
            <TableRow v-for="o in orders" :key="o.id">
              <TableCell class="font-medium"> {{ o.id }} </TableCell>
              <TableCell>
                <Badge :variant="o.status === 'PROCESSING' ? 'blue' : o.status === 'DISPATCHED' ? 'yellow' : 'green'">
                  {{ o.status }}
                </Badge>
              </TableCell>
              <TableCell>{{ format(parse(o.createdAt, 'dd-MM-yyyy HH:mm:ss', new Date()), 'dd-MM-yyyy') }}</TableCell>
              <TableCell>{{ format(parse(o.createdAt, 'dd-MM-yyyy HH:mm:ss', new Date()), 'HH:mm') }}</TableCell>
              <TableCell>
                {{ o.paymentType }}
              </TableCell>
              <TableCell class="flex justify-end gap-2">
                <Button v-if="o.status === 'DISPATCHED'" class="mr-2" size="sm" variant="green" @click="confirmDelivery(o.id)">Confirm Delivery</Button>
                <div class="text-nowrap flex flex-row items-center justify-end text-xs text-slate-500 hover:text-blue-600 cursor-pointer" @click="$router.push(`/orders/${o.id}`)">
                  Show Details
                  <Icon name="stash:new-window-page-light" class="w-6 h-6" mode="svg" />
                </div>
              </TableCell>
            </TableRow>
          </TableBody>
        </Table>
      </div>
    </div>
  </div>
</template>

<script setup>
import { format, parse } from 'date-fns'
import { onMounted } from 'vue'
import { useAPI } from '~/store/storeAPI'
import { useAuthStore } from '~/store/storeAuth'

const storeAuth = useAuthStore()
const orders = ref([])
const customers = ref([])

const isLoading = ref(true)
const api = useAPI()

const confirmDelivery = async (orderId) => {
  await api.updateOrder(orderId)
  await fetchData()
}

const fetchData = async () => {
  orders.value = await api.getOrders()
  orders.value = orders.value.filter((o) => o.customerId == storeAuth.user.id)
  customers.value = await api.getCustomers()
}
onMounted(async () => {
  await fetchData()
  isLoading.value = false
})
</script>
