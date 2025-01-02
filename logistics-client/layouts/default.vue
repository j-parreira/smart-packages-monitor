<script setup>
import MenuItem from '@/components/MenuItem.vue'
import Card from '@/components/ui/card/Card.vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '~/store/storeAuth.js'
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuLabel, DropdownMenuSeparator, DropdownMenuTrigger } from '@/components/ui/dropdown-menu'
import { ref, onMounted, onBeforeMount } from 'vue'

const router = useRouter()
const storeAuth = useAuthStore()
const isLoading = ref(true)

function logout() {
  storeAuth.logout()
  router.push('/')
}

onMounted(async () => {
  if (localStorage.getItem('token')) {
    await storeAuth.checkAuth()
  }
  isLoading.value = false
})

const names = {
  DA: 'Diogo Abegão',
  JP: 'João Parreira',
  MO: 'Marcelo Oliveira',
  PB: 'Pedro Barbeiro'
}

const showFullName = (id) => {
  document.getElementById(id).textContent = names[id]
}

const hideFullName = (id) => {
  document.getElementById(id).textContent = id
}
</script>

<template>
  <div class="bg-blue-50 min-h-screen">
    <Card class="flex flex-col container min-h-screen p-0 rounded-none">
      <div class="flex items-center justify-between h-14 p-3 pr-0 border-b">
        <div class="flex items-center gap-3 hover:cursor-pointer" @click="router.push('/')">
          <img src="/logo.png" alt="logo" class="h-12" />
          <div class="text-blue-950 font-medium">Smart Packages Management</div>
        </div>
        <div v-if="!isLoading" class="flex items-center gap-3 h-full">
          <!-- Guest -->
          <div v-if="!storeAuth.user" class="pr-3">
            <MenuItem :item="{ label: 'Login', to: '/login' }" />
          </div>
          <!-- Authenticated -->
          <div v-else>
            <div class="flex items-center gap-3">
              <DropdownMenu>
                <DropdownMenuTrigger class="border-l border-b hover:bg-slate-50 px-3 h-14 flex items-center gap-3 hover:cursor-pointer justify-center">
                  <img src="/assets/img/avatar-none.png" alt="avatar" class="h-10 w-10 rounded-full" />
                  <div class="flex flex-col items-start">
                    <div class="text-blue-950 font-medium">{{ storeAuth.user?.name ?? 'Username' }}</div>
                    <div class="text-blue-800 text-xs">{{ storeAuth.user?.role ?? 'Role' }}</div>
                  </div>
                </DropdownMenuTrigger>
                <DropdownMenuContent>
                  <DropdownMenuLabel>My Account</DropdownMenuLabel>
                  <DropdownMenuSeparator />
                  <DropdownMenuItem @click="router.push('/profile')">Profile</DropdownMenuItem>
                  <DropdownMenuItem @click="router.push('/profile')">Orders</DropdownMenuItem>
                  <DropdownMenuSeparator />
                  <DropdownMenuItem @click="logout" class="hover:cursor-pointer">Logout</DropdownMenuItem>
                </DropdownMenuContent>
              </DropdownMenu>
            </div>
          </div>
        </div>
        <div v-else class="flex items-center justify-center h-screen pr-5">
          <Icon name="eos-icons:three-dots-loading" class="text-blue-950 w-12 h-12" />
        </div>
      </div>

      <!-- Authenticated -->
      <div class="flex items-center text-sm justify-end gap-5 h-10 p-3 border-b" v-if="storeAuth.user && storeAuth.user?.role !== 'Customer'">
        <MenuItem :item="{ label: 'Customers', to: '/customers' }" />
        <MenuItem :item="{ label: 'Employees', to: '/employees' }" />
        <MenuItem :item="{ label: 'Orders', to: '/orders' }" />
        <MenuItem :item="{ label: 'Warehouses', to: '/customers' }" />
      </div>

      <div class="flex-grow flex flex-col items-center justify-center">
        <slot />
      </div>

      <div class="w-full bg-white border-t">
        <div class="p-3 bg-white md:flex md:items-center md:justify-between">
          <span class="flex text-sm text-gray-500 justify-center text-center">IPLeiria | Enterprise Applications Development | 2024-2025 </span>
          <div class="flex justify-center space-x-2 rtl:space-x-reverse">
            <a id="DA" href="https://www.linkedin.com/in/diogo-abegao/" class="text-gray-400 hover:text-blue-800 text-sm p-1" @mouseover="showFullName('DA')" @mouseleave="hideFullName('DA')"> DA </a>
            <div class="text-gray-400 text-sm p-1">|</div>
            <a id="JP" href="https://www.linkedin.com/in/joao-parreira-dev/" class="text-gray-400 hover:text-blue-800 text-sm p-1" @mouseover="showFullName('JP')" @mouseleave="hideFullName('JP')"> JP </a>
            <div class="text-gray-400 text-sm p-1">|</div>
            <a id="MO" href="https://www.linkedin.com/in/marcelo-oliveira-89602b212/" class="text-gray-400 hover:text-blue-800 text-sm p-1" @mouseover="showFullName('MO')" @mouseleave="hideFullName('MO')"> MO </a>
            <div class="text-gray-400 text-sm p-1">|</div>
            <a id="PB" href="https://www.linkedin.com/in/pedrobarbeiro/" class="text-gray-400 hover:text-blue-800 text-sm p-1" @mouseover="showFullName('PB')" @mouseleave="hideFullName('PB')"> PB </a>
          </div>
        </div>
      </div>
    </Card>
  </div>
</template>
