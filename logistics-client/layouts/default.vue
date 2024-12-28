<script setup>
import { useAuthStore } from '~/store/storeAuth.js'
import Card from '@/components/ui/card/Card.vue'

const router = useRouter()
const authStore = useAuthStore()

function logout() {
  authStore.logout()
  router.push('/')
}

const menuItems = [
  { label: 'Profile', icon: 'material-symbols:account-circle', to: '/login' },
  { label: 'Settings', icon: 'material-symbols:settings', to: '/' },
  { label: 'Logout', icon: 'material-symbols:logout-rounded', onClick: logout }
]
</script>

<template>
  <div class="bg-slate-50">
    <div class="min-w-screen min-h-screen p-3 container flex">
      <Card class="w-full">
        <div class="flex items-center justify-between p-3 h-14">
          <div class="flex flex-row items-center gap-3">
            <img src="/logo.png" alt="logo" class="h-10" />
            <div class="font-semibold">Smart Packages Manager</div>
          </div>
          <div class="flex items-center gap-x-3 h-full">
            <div v-for="item in menuItems" :key="item.label" class="border rounded h-full flex items-center px-2 bg-gray-100 border-gray-200">
              <Icon :name="item.icon" v-if="item.icon" class="mr-2" />
              <nuxt-link :to="item.to" v-if="item.to">{{ item.label }}</nuxt-link>
              <a href="#" @click.prevent="item.onClick" v-else>{{ item.label }}</a>
            </div>
          </div>
        </div>
        <hr />
        <div class="flex-grow p-3">
          <slot />
        </div>
      </Card>
    </div>
  </div>
</template>
