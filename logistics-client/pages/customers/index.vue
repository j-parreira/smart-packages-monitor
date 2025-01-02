<template>
  <div class="w-full p-3 bg-slate-50 border-b">
    <Breadcrumb>
      <BreadcrumbList>
        <BreadcrumbItem>
          <BreadcrumbLink @click="$router.push('/')"> Home </BreadcrumbLink>
        </BreadcrumbItem>
        <BreadcrumbSeparator />
        <BreadcrumbItem>
          <BreadcrumbPage>Customers</BreadcrumbPage>
        </BreadcrumbItem>
      </BreadcrumbList>
    </Breadcrumb>
  </div>
  <div class="mb-auto sm:w-full p-3 ">
    <h1 class="text-slate-700 pb-3">List of Customers</h1>
    <div class="border rounded-lg">
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead class="w-[100px]"> ID </TableHead>
            <TableHead>Name</TableHead>
            <TableHead>Email</TableHead>
            <TableHead> Address </TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          <TableRow v-for="customer in customers" :key="customer.id" @click="$router.push(`/customers/${customer.id}`)" class="hover:cursor-pointer">
            <TableCell class="font-medium"> {{ customer.id }} </TableCell>
            <TableCell>{{ customer.name }}</TableCell>
            <TableCell>{{ customer.email }}</TableCell>
            <TableCell> {{ customer.address }} </TableCell>
          </TableRow>
        </TableBody>
      </Table>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios'
import { useAuthStore } from '~/store/storeAuth.js'
import { onMounted } from 'vue'

const storeAuth = useAuthStore()

const customers = ref([])

const fetchCustomers = async () => {
  try {
    const response = await axios.get('customers')
    customers.value = response.data
    console.log(response.data)
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  fetchCustomers()
})
</script>
