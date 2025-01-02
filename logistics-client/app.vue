<template>
  <NuxtLayout>
    <Toaster />
    <NuxtPage />
  </NuxtLayout>
</template>

<script setup>
import { onMounted } from 'vue'
import { useAuthStore } from '~/store/storeAuth.js'

const storeAuth = useAuthStore()

onMounted(async () => {
  await storeAuth.checkAuth()
})
</script>