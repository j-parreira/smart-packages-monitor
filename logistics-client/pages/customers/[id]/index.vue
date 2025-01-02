<template>
  <div class="w-full p-3 bg-slate-50 border-b">
    <Breadcrumb>
      <BreadcrumbList>
        <BreadcrumbItem>
          <BreadcrumbLink @click="$router.push('/')"> Home </BreadcrumbLink>
        </BreadcrumbItem>
        <BreadcrumbSeparator />
        <BreadcrumbItem>
          <BreadcrumbLink @click="$router.push('/customers')"> Customers </BreadcrumbLink>
        </BreadcrumbItem>
        <BreadcrumbSeparator />
        <BreadcrumbItem>
          <BreadcrumbPage>{{ customer.name }}</BreadcrumbPage>
        </BreadcrumbItem>
      </BreadcrumbList>
    </Breadcrumb>
  </div>
  <div class="mb-auto sm:w-full p-3 ">
    <h1 class="pb-3">Customer Details</h1>
    <div class="border rounded-lg flex items-center">
      <Table>
        <TableBody>
          <TableRow>
            <TableHead class="bg-slate-50 border-r text-right">ID</TableHead>
            <TableCell class="border-r">{{ customer.id }}</TableCell>
          </TableRow>
          <TableRow>
            <TableHead class="bg-slate-50 border-r text-right">Name</TableHead>
            <TableCell class="border-r">{{ customer.name }}</TableCell>
          </TableRow>
          <TableRow>
            <TableHead class="bg-slate-50 border-r text-right">Email</TableHead>
            <TableCell class="border-r">{{ customer.email }}</TableCell>
          </TableRow>
          <TableRow>
            <TableHead class="bg-slate-50 border-r text-right">Address</TableHead>
            <TableCell class="border-r">{{ customer.address }}</TableCell>
          </TableRow>
        </TableBody>
      </Table>
      <div>
        <img src="/assets/img/avatar-none.png" class="w-1/2 mx-auto rounded-full" />
      </div>
    </div>

    <h1 class="py-3">Customer Orders</h1>
    <div class="border rounded-lg">
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead class="w-[100px]">ID</TableHead>
            <TableHead>Order Date</TableHead>
            <TableHead>Amount</TableHead>
            <TableHead>Status</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          <TableRow v-for="order in customer.orders" :key="order.id">
            <TableCell>{{ order.id }}</TableCell>
            <TableCell>{{ order.orderDate }}</TableCell>
            <TableCell>{{ order.amount }}</TableCell>
            <TableCell>{{ order.status }}</TableCell>
          </TableRow>
        </TableBody>
      </Table>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios'
import { onMounted } from 'vue'

const route = useRoute()

const customer = ref([])
const orders = ref([])

const fetchCustomer = async () => {
  try {
    const response = await axios.get(`customers/${route.params.id}`)
    customer.value = response.data
    console.log(response.data)
  } catch (error) {
    console.error(error)
  }
}

const fetchCustomerOrders = async () => {
  try {
    const response = await axios.get(`customers/${route.params.id}/orders`)
    orders.value = response.data
    console.log(response.data)
  } catch (error) {
    console.error(error)
  }
}
onMounted(() => {
  fetchCustomer()
  fetchCustomerOrders()
})
</script>
