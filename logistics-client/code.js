import { onMounted } from 'vue'
import { useAPI } from '~/store/storeAPI'

import { useAuthStore } from '~/store/storeAuth'
const storeAuth = useAuthStore()

const storeError = useErrorStore()
const isLoading = ref(true)
const api = useAPI()

const fetchData = async () => {
  orders.value = await api.getOrders()
  customers.value = await api.getCustomers()
}
onMounted(async () => {
  await fetchData()
  isLoading.value = false
})
