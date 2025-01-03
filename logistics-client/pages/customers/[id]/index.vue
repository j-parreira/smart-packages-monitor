<template>
  <div class="w-full p-3 bg-slate-50 border-b top-0">
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
  <div class="mb-auto sm:w-full p-3">
    <div v-if="!isLoading">
      <h1 class="pb-3">Customer Details</h1>
      <Card class="mb-3">
        <CardContent class="p-3">
          <div class="flex items-center justify-between">
            <div>
              <div class="text-xl font-semibold">{{ customer.name }}</div>
              <div class="text-sm text-slate-500">{{ customer.email }}</div>
            </div>
            <div class="flex items-center gap-2">
              <AlertDialog>
                <AlertDialogTrigger as-child>
                  <Button variant="blue">Edit</Button>
                </AlertDialogTrigger>
                <AlertDialogContent>
                  <AlertDialogHeader>
                    <AlertDialogTitle>Edit {{ customer.name }}</AlertDialogTitle>
                    <AlertDialogDescription>
                      <div class="flex flex-col gap-3">
                        <div class="flex flex-row items-center gap-3">
                          <span class="w-1/2 text-right"> Username </span>
                          <Input v-model="user.name" label="Name" />
                        </div>
                        <div class="flex flex-row items-center gap-3">
                          <span class="w-1/2 text-right"> Email </span>
                          <Input v-model="user.email" label="Email" />
                        </div>
                        <div class="flex flex-row items-center gap-3">
                          <span class="w-1/2 text-right"> Address </span>
                          <Input v-model="user.address" label="Address" />
                        </div>
                        <div class="flex flex-row items-center gap-3">
                          <span class="w-1/2 text-right"> Password </span>
                          <Input v-model="user.password" label="Password" />
                        </div>
                      </div>
                    </AlertDialogDescription>
                  </AlertDialogHeader>
                  <AlertDialogFooter>
                    <AlertDialogCancel>Cancel</AlertDialogCancel>
                    <AlertDialogAction @click="updateCustomer">Save</AlertDialogAction>
                  </AlertDialogFooter>
                </AlertDialogContent>
              </AlertDialog>
              <Button variant="red" @click="deleteCustomer">Delete</Button>
            </div>
          </div>
        </CardContent>
      </Card>
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
      <div class="border rounded-lg" v-if="customer.orders.length > 0">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead class="w-[100px]">ID</TableHead>
              <TableHead>Status</TableHead>
              <TableHead>Order Date</TableHead>
              <TableHead>Order Time</TableHead>
              <TableHead>Payment Type</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            <TableRow v-for="order in customer.orders" :key="order.id">
              <TableCell>{{ order.id }}</TableCell>
              <TableCell>
                <Badge :variant="order.status === 'PROCESSING' ? 'blue' : order.status === 'DISPATCHED' ? 'yellow' : 'green'">
                  {{ order.status }}
                </Badge>
              </TableCell>
              <TableCell>{{ format(parse(order.createdAt, 'dd-MM-yyyy HH:mm:ss', new Date()), 'dd-MM-yyyy') }}</TableCell>
              <TableCell>{{ format(parse(order.createdAt, 'dd-MM-yyyy HH:mm:ss', new Date()), 'HH:mm') }}</TableCell>
              <TableCell class="flex justify-between gap-2">
                {{ order.paymentType }}
                <div class="text-nowrap flex flex-row items-center justify-end text-xs text-slate-500 hover:text-blue-600 cursor-pointer" @click="$router.push(`/orders/${order.id}`)">
                  Show Details
                  <Icon name="stash:new-window-page-light" class="w-6 h-6" />
                </div>
              </TableCell>
            </TableRow>
          </TableBody>
        </Table>
      </div>
      <div v-else class="flex items-center justify-center h-24">
        <div class="text-slate-500">No orders found</div>
      </div>
    </div>
    <div v-else class="w-full h-full flex items-center justify-center">
      <Icon name="eos-icons:three-dots-loading" class="text-blue-950 w-12 h-12" />
    </div>
  </div>
</template>

<script setup>
import { AlertDialog, AlertDialogAction, AlertDialogCancel, AlertDialogContent, AlertDialogDescription, AlertDialogFooter, AlertDialogHeader, AlertDialogTitle, AlertDialogTrigger } from '@/components/ui/alert-dialog'
import { format, parse } from 'date-fns'
import { onMounted } from 'vue'
import { useAPI } from '~/store/storeAPI'
import { useRouter, useRoute } from 'vue-router'

const route = useRoute()
const customer = ref([])
const isLoading = ref(true)
const api = useAPI()
const router = useRouter()

const user = ref({
  name: '',
  email: '',
  address: '',
  password: ''
})

const deleteCustomer = async () => {
  if(!confirm('Are you sure you want to delete this customer?')) return
  await api.deleteCustomer(route.params.id)
  router.push('/customers')
}

const updateCustomer = async () => {
  await api.updateCustomer(route.params.id, user.value)
  await fetchData()
}

const fetchData = async () => {
  customer.value = await api.getCustomer(route.params.id)
  user.value = {
    name: customer.value.name,
    email: customer.value.email,
    address: customer.value.address,
    password: ''
  }
}
onMounted(async () => {
  await fetchData()
  isLoading.value = false
})
</script>
