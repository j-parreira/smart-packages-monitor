import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import axios from 'axios'
import { useErrorStore } from './error.js'
import { useToast } from '@/components/ui/toast/use-toast'
import { useAuthStore } from './storeAuth.js'

export const useAPI = defineStore('apiStore', () => {
  const { toast } = useToast()
  const storeError = useErrorStore()
  const storeAuth = useAuthStore()
  const router = useRouter()

  /* Auth */
  const getAuthUser = async () => {
    storeError.resetMessages()
    try {
      const response = await axios.get('auth/user')
      return response.data
    } catch (e) {
      storeError.setErrorMessages(e.response.statusText, e.response.data.parameterViolations, e.response.status, 'Error getting user data!')
      return false
    }
  }

  /* Manager */
  const getManager = async (userID) => {
    storeError.resetMessages()
    let manager = {}
    try {
      let response = await axios.get(`managers/${userID}`)
      manager = response.data

      response = await axios.get(`warehouses/${response.data.warehouseId}`)
      manager.warehouse = response.data
      return manager
    } catch (e) {
      storeError.setErrorMessages(e.response.statusText, e.response.data.parameterViolations, e.response.status, 'Error getting manager data!')
      return false
    }
  }

  /* Employee */
  const getEmployee = async (userID) => {
    storeError.resetMessages()
    let employee = {}
    try {
      let response = await axios.get(`employees/${userID}/volumes`)
      employee = response.data

      response = await axios.get(`warehouses/${response.data.warehouseId}`)
      employee.warehouse = response.data

      return employee
    } catch (e) {
      storeError.setErrorMessages(e.response.statusText, e.response.data.parameterViolations, e.response.status, 'Error getting manager data!')
      return false
    }
  }
  const getEmployees = async () => {
    storeError.resetMessages()
    try {
      const response = await axios.get(`employees`)
      return response.data
    } catch (e) {
      storeError.setErrorMessages(e.response.statusText, e.response.data.parameterViolations, e.response.status, 'Error getting employees data!')
      return false
    }
  }
  const updateEmployee = async (userID, data) => {
    storeError.resetMessages()
    try {
      const response = await axios.put(`employees/${userID}`, data)
      return response.data
    } catch (e) {
      storeError.setErrorMessages(e.response.data, e.response.data.parameterViolations, e.response.status, 'Error updating employee data!')
      return
    }
  }
  const deleteEmployee = async (userID) => {
    storeError.resetMessages()
    try {
      const response = await axios.delete(`employees/${userID}`)
      return response.data
    } catch (e) {
      storeError.setErrorMessages(e.response.statusText, e.response.data.parameterViolations, e.response.status, 'Error deleting employee!')
      return false
    }
  }

  /* Customer */
  const getCustomer = async (userID) => {
    storeError.resetMessages()
    try {
      const response = await axios.get(`customers/${userID}/orders`)
      return response.data
    } catch (e) {
      storeError.setErrorMessages(e.response.statusText, e.response.data.parameterViolations, e.response.status, 'Error getting customer data!')
      return false
    }
  }
  const getCustomers = async () => {
    storeError.resetMessages()
    try {
      const response = await axios.get(`customers`)
      return response.data
    } catch (e) {
      storeError.setErrorMessages(e.response.statusText, e.response.data.parameterViolations, e.response.status, 'Error getting customers data!')
      return false
    }
  }
  const createCustomer = async (data) => {
    storeError.resetMessages()
    try {
      const response = await axios.post('customers', data)
      return response.data
    } catch (e) {
      storeError.setErrorMessages(e.response.data, e.response.data.parameterViolations, e.response.status, 'Error registering user!')
      return false
    }
  }
  const deleteCustomer = async (userID) => {
    storeError.resetMessages()
    try {
      const response = await axios.delete(`customers/${userID}`)
      return response.data
    } catch (e) {
      storeError.setErrorMessages(e.response.statusText, e.response.data.parameterViolations, e.response.status, 'Error deleting user!')
      return false
    }
  }
  const updateCustomer = async (userID, data) => {
    storeError.resetMessages()
    try {
      const response = await axios.put(`customers/${userID}`, data)
      return response.data
    } catch (e) {
      storeError.setErrorMessages(e.response.data, e.response.data.parameterViolations, e.response.status, 'Error updating user data!')
      return false
    }
  }

  /* Orders */
  const getOrder = async (orderID) => {
    storeError.resetMessages()
    let order = {}
    try {
      let response = await axios.get(`orders/${orderID}`)
      order = response.data

      response = await axios.get(`orders/${orderID}/products`)
      order.products = response.data.products

      response = await axios.get(`orders/${orderID}/volumes`)
      order.volumes = response.data.volumes

      return order
    } catch (e) {
      storeError.setErrorMessages(e.response.statusText, e.response.data.parameterViolations, e.response.status, 'Error getting order data!')
      return false
    }
  }
  const getOrders = async () => {
    storeError.resetMessages()
    try {
      const response = await axios.get(`orders`)
      return response.data
    } catch (e) {
      storeError.setErrorMessages(e.response.statusText, e.response.data.parameterViolations, e.response.status, 'Error getting orders data!')
      return false
    }
  }

  /* Products */
  const getProducts = async () => {
    storeError.resetMessages()
    try {
      const response = await axios.get(`products`)
      return response.data
    } catch (e) {
      storeError.setErrorMessages(e.response.statusText, e.response.data.parameterViolations, e.response.status, 'Error getting products data!')
      return false
    }
  }

  const getWarehouses = async () => {
    storeError.resetMessages()
    try {
      const response = await axios.get(`warehouses`)
      return response.data
    } catch (e) {
      storeError.setErrorMessages(e.response.statusText, e.response.data.parameterViolations, e.response.status, 'Error getting warehouses data!')
      return false
    }
  }

  return {
    getAuthUser,
    getManager,
    getEmployee,
    getEmployees,
    updateEmployee,
    deleteEmployee,
    getCustomer,
    getCustomers,
    deleteCustomer,
    createCustomer,
    updateCustomer,
    getOrder,
    getOrders,
    getProducts,
    getWarehouses
  }
})
