<template>
  <div class="w-full p-3 bg-slate-50 border-b">
    <Breadcrumb>
      <BreadcrumbList>
        <BreadcrumbItem>
          <BreadcrumbLink @click="$router.push('/')"> Home </BreadcrumbLink>
        </BreadcrumbItem>
        <BreadcrumbSeparator />
        <BreadcrumbItem>
          <BreadcrumbPage>Orders</BreadcrumbPage>
        </BreadcrumbItem>
      </BreadcrumbList>
    </Breadcrumb>
  </div>
  <div class="mb-auto sm:w-full p-3">
    <div v-if="isLoading" class="flex justify-center items-center">
      <Icon name="eos-icons:three-dots-loading" class="text-blue-950 w-12 h-12" />
    </div>
    <div v-else>
      <h1 class="text-slate-700 pb-3">List of Orders</h1>
      <div class="border rounded-lg">
        <Table>
          <TableRow>
            <TableHead class="w-[100px]">ID</TableHead>
            <TableHead>Customer</TableHead>
            <TableHead>Status</TableHead>
            <TableHead>Order Date</TableHead>
            <TableHead>Order Time</TableHead>
            <TableHead>Payment Type</TableHead>
          </TableRow>
          <TableBody>
            <TableRow v-for="o in orders" :key="o.id">
              <TableCell class="font-medium"> {{ o.id }} </TableCell>
              <TableCell class="hover:text-blue-600 cursor-pointer text-slate-600" @click="$router.push(`/customers/${o.customerId}`)">{{ customers.filter((c) => c.id == o.customerId)[0].name }}</TableCell>
              <TableCell>
                <Badge :variant="o.status === 'PROCESSING' ? 'blue' : o.status === 'DISPATCHED' ? 'yellow' : 'green'">
                  {{ o.status }}
                </Badge>
              </TableCell>
              <TableCell>{{ format(parse(o.createdAt, 'dd-MM-yyyy HH:mm:ss', new Date()), 'dd-MM-yyyy') }}</TableCell>
              <TableCell>{{ format(parse(o.createdAt, 'dd-MM-yyyy HH:mm:ss', new Date()), 'HH:mm') }}</TableCell>
              <TableCell class="flex justify-between gap-2">
                {{ o.paymentType }}
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

const orders = ref([])
const customers = ref([])

const isLoading = ref(true)
const api = useAPI()

const fetchData = async () => {
  orders.value = await api.getOrders()
  customers.value = await api.getCustomers()
}
onMounted(async () => {
  await fetchData()
  isLoading.value = false
})
</script>
