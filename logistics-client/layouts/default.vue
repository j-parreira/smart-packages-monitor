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
  await storeAuth.checkAuth()
  isLoading.value = false
})
</script>

<template>
  <div class="bg-blue-50 min-h-screen">
    <Card class="flex flex-col container min-h-screen p-0 rounded-none">
      <div class="flex items-center justify-between h-14 p-3 pr-0 border-b">
        <div class="flex items-center gap-3 hover:cursor-pointer" @click="router.push('/')">
          <img src="/assets/img/logo.png" alt="logo" class="h-12" />
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
                    <div class="text-blue-600 text-xs">{{ storeAuth.user?.role ?? 'Role' }}</div>
                  </div>
                </DropdownMenuTrigger>
                <DropdownMenuContent>
                  <DropdownMenuLabel>My Account</DropdownMenuLabel>
                  <DropdownMenuSeparator />
                  <DropdownMenuItem>Profile</DropdownMenuItem>
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
      <div class="flex items-center justify-end gap-3 h-10 p-3 border-b">
        <MenuItem :item="{ label: 'Customers', to: '/customers' }" />
        <MenuItem :item="{ label: 'Orders', to: '/customers' }" />
        <MenuItem :item="{ label: 'Warehouses', to: '/customers' }" />
      </div>

      <div class="flex-grow flex flex-col items-center justify-center">
        <slot />
      </div>
    </Card>
  </div>
</template>
