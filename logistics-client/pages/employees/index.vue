<template>
  <div class="w-full p-3 bg-slate-50 border-b flex justify-between items-center">
    <Breadcrumb>
      <BreadcrumbList>
        <BreadcrumbItem>
          <BreadcrumbLink @click="$router.push('/')"> Home </BreadcrumbLink>
        </BreadcrumbItem>
        <BreadcrumbSeparator />
        <BreadcrumbItem>
          <BreadcrumbPage>Employees</BreadcrumbPage>
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
      <h1 class="text-slate-700 pb-3">List of Employees</h1>
      <div class="border rounded-lg">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead class="w-[100px]"> ID </TableHead>
              <TableHead>Name</TableHead>
              <TableHead>Email</TableHead>
              <TableHead> Warehouse </TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            <TableRow v-for="e in employees" :key="e.id">
              <TableCell class="font-medium"> {{ e.id }} </TableCell>
              <TableCell>{{ e.name }}</TableCell>
              <TableCell>{{ e.email }}</TableCell>
              <TableCell class="flex justify-between gap-2">
                {{ e.warehouseId }}
                <div
                  class="text-nowrap flex flex-row items-center justify-end text-xs text-slate-500 hover:text-blue-600 cursor-pointer"
                  @click="$router.push(`/employees/${e.id}`)">
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
import { onMounted } from 'vue'
import { useAPI } from '~/store/storeAPI'
import { useRouter } from 'vue-router'
const employees = ref([])
const isLoading = ref(true)
const api = useAPI()
const router = useRouter()
const fetchData = async () => {
  employees.value = await api.getEmployees()
}
onMounted(async () => {
  try {
    await fetchData()
  } catch (error) {
    router.push('/error/403')
  }
  isLoading.value = false
})
</script>
