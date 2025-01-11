import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import axios from 'axios'
import { useErrorStore } from './error.js'
import { useToast } from '@/components/ui/toast/use-toast'
import { useAuthStore } from './storeAuth.js'
import { toRaw } from 'vue'
export const useCart = defineStore('cartStore', () => {
  const { toast } = useToast()
  const storeError = useErrorStore()
  const storeAuth = useAuthStore()
  const router = useRouter()

  const cart = ref([])

  const isOnCart = (product) => {
    return cart.value.some((item) => item.id === product.id)
  }

  const addToCart = async (product) => {
    const productInCart = cart.value.find((item) => item.id === product.id)
    if (!productInCart) {
      cart.value.push({ ...product })
      localStorage.setItem('cart', JSON.stringify(toRaw(cart.value)))
      toast({
        description: 'Product added to cart',
        variant: 'green'
      })
    }
  }

  const removeFromCart = (product) => {
    const productInCart = cart.value.find((item) => item.id === product.id)
    if (productInCart) {
      cart.value = cart.value.filter((item) => item.id !== product.id)
      localStorage.setItem('cart', JSON.stringify(toRaw(cart.value)))
      toast({
        description: 'Product removed from cart',
        variant: 'red'
      })
    }
  }

  const loadCart = () => {
    const cartData = localStorage.getItem('cart')
    if (cartData) {
      cart.value = JSON.parse(cartData)
    }
  }

  const clearCart = () => {
    cart.value = []
    localStorage.removeItem('cart')
  }

  return {
    cart,
    addToCart,
    removeFromCart,
    isOnCart,
    loadCart,
    clearCart
  }
})
