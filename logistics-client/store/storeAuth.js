import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import axios from 'axios'
import { useErrorStore } from './error.js'
import { useToast } from '@/components/ui/toast/use-toast'

export const useAuthStore = defineStore('authStore', () => {
  const storeError = useErrorStore()
  const router = useRouter()
  const token = ref(null)
  const user = ref(null)
  const { toast } = useToast()

  axios.defaults.baseURL = useRuntimeConfig().public.API_URL
  axios.defaults.headers.common['Content-Type'] = 'application/json'

  function logout() {
    clearUser()
  }

  const clearUser = () => {
    user.value = null
    token.value = ''
    localStorage.removeItem('token')
    axios.defaults.headers.common.Authorization = ''
  }

  const login = async (credentials) => {
    try {
      const responseLogin = await axios.post('auth/login', credentials)

      token.value = responseLogin.data
      localStorage.setItem('token', token.value)
      axios.defaults.headers.common.Authorization = 'Bearer ' + token.value

      const responseUser = await axios.get('auth/user')
      user.value = responseUser.data
      toast({
        title: 'Login Successful',
        variant: 'green'
      })
      return user.value
    } catch (e) {
      clearUser()
      storeError.setErrorMessages(e.response.statusText, e.response.data.parameterViolations, e.response.status, 'Authentication Error!')
      return false
    }
  }

  const checkAuth = async () => {
    if (localStorage.getItem('token')) {
      axios.defaults.headers.common.Authorization = 'Bearer ' + localStorage.getItem('token')
      try {
        const responseUser = await axios.get('auth/user')
        user.value = responseUser.data
        return true
      } catch (e) {
        clearUser()
        storeError.setErrorMessages(e.response.statusText, e.response.data.parameterViolations, e.response.status, 'Authentication Error!')
        return false
      }
    }
    return true
  }

  return {
    token,
    user,
    login,
    logout,
    checkAuth
  }
})
