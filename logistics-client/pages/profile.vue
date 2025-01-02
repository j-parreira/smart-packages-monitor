<template>
  <div class="w-full p-3 bg-slate-50 border-b top-0">
    <Breadcrumb>
      <BreadcrumbList>
        <BreadcrumbItem>
          <BreadcrumbLink @click="$router.push('/')"> Home </BreadcrumbLink>
        </BreadcrumbItem>
        <BreadcrumbSeparator />
        <BreadcrumbItem>
          <BreadcrumbPage>Profile</BreadcrumbPage>
        </BreadcrumbItem>
      </BreadcrumbList>
    </Breadcrumb>
  </div>
  <div class="mb-auto sm:w-full p-3">
    <div v-if="!isLoading">
      <h1 class="pb-3">Profile</h1>
      <div class="border rounded-lg flex items-center">
        <Table>
          <TableBody>
            <TableRow>
              <TableHead class="bg-slate-50 border-r text-right">ID</TableHead>
              <TableCell class="border-r">{{ me.id }}</TableCell>
            </TableRow>
            <TableRow>
              <TableHead class="bg-slate-50 border-r text-right">Name</TableHead>
              <TableCell class="border-r">{{ me.name }}</TableCell>
            </TableRow>
            <TableRow>
              <TableHead class="bg-slate-50 border-r text-right">Email</TableHead>
              <TableCell class="border-r">{{ me.email }}</TableCell>
            </TableRow>
            <TableRow>
              <TableHead class="bg-slate-50 border-r text-right">Address</TableHead>
              <TableCell class="border-r">{{ me.address ?? 'None' }}</TableCell>
            </TableRow>
            <TableRow v-if="me.role === 'Employee' || me.role === 'Manager'">
              <TableHead class="bg-slate-50 border-r text-right">Warehouse</TableHead>
              <TableCell class="border-r">{{ me.warehouse.name ?? 'None' }}</TableCell>
            </TableRow>
            <TableRow v-if="me.role === 'Manager'">
              <TableHead class="bg-slate-50 border-r text-right">Office</TableHead>
              <TableCell class="border-r">{{ me.office ?? 'None' }}</TableCell>
            </TableRow>
          </TableBody>
        </Table>
        <div>
          <img src="/assets/img/avatar-none.png" class="w-1/2 mx-auto rounded-full" />
        </div>
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

const api = useAPI()
const me = ref([])
const isLoading = ref(true)

const fetchData = async () => {
  me.value = await api.getAuthUser()
  const role = me.value.role
  const id = me.value.id
  switch (me.value.role) {
    case 'Customer':
      me.value = await api.getCustomer(id)
      break
    case 'Manager':
      me.value = await api.getManager(id)
      break
    case 'Employee':
      me.value = await api.getEmployee(id)
      break
  }
  me.value.role = role
}

onMounted(async () => {
  await fetchData()
  isLoading.value = false
})
</script>
