import { onMounted } from 'vue'
import { useErrorStore } from '~/store/error.js'
import { useAuthStore } from '~/store/storeAuth'
const storeAuth = useAuthStore()

import { useCart } from '~/store/storeCart'
const storeCart = useCart()

const storeError = useErrorStore()
const isLoading = ref(true)

import { useAPI } from '~/store/storeAPI'
const api = useAPI()

const fetchData = async () => {
  orders.value = await api.getOrders()
  customers.value = await api.getCustomers()
}
onMounted(async () => {
  await fetchData()
  isLoading.value = false
})
