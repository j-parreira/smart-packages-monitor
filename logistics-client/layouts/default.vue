<script setup>
import MenuItem from '@/components/MenuItem.vue'
import Card from '@/components/ui/card/Card.vue'
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuLabel, DropdownMenuSeparator, DropdownMenuTrigger } from '@/components/ui/dropdown-menu'
import { ShoppingBasket } from 'lucide-vue-next'
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAPI } from '~/store/storeAPI'
import { useAuthStore } from '~/store/storeAuth.js'
import { useCart } from '~/store/storeCart'
import { useToast } from '~/components/ui/toast/use-toast'

const storeCart = useCart()
const router = useRouter()
const storeAuth = useAuthStore()
const isLoading = ref(true)
const api = useAPI()
const { toast } = useToast()
const activeSensors = ref(0)
function logout() {
  storeAuth.logout()
  localStorage.removeItem('cart')
  storeCart.cart = []
  router.push('/')
}

const cartCheckout = ref({
  paymentType: null,
})

const checkout = async () => {
  cartCheckout.value.customerId = storeAuth.user?.id
  cartCheckout.value.products = storeCart.cart.map(p => ({ id: p.id }))
  await api.createOrder(cartCheckout.value)
  storeCart.clearCart()
  toast({
    title: 'Order created successfully',
    description: 'Your order was created successfully',
    variant: 'green',
  })
}

onMounted(async () => {
  if (localStorage.getItem('token')) {
    await storeAuth.checkAuth()
    if (localStorage.getItem('cart')) {
      storeCart.loadCart()
    }
  }
  if (storeAuth.user) {
    const sensors = await api.getSensorsActive()
    activeSensors.value = sensors.length
  }
  isLoading.value = false
})

const sendNewReadings = async () => {
  const sensors = await api.getSensorsActive()
  activeSensors.value = sensors.length
  for (const sensor of sensors) {
    api.createRandomReading(sensor.id, sensor.type, sensor.minThreshold, sensor.maxThreshold, true)
  }
  toast({
    description: 'New readings were sent to all active sensors',
    variant: 'blue',
  })
}
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
          <div v-if="!storeAuth.user" class="pr-3 gap-3 flex">
            <MenuItem :item="{ label: 'Login', to: '/login' }" />
            <MenuItem :item="{ label: 'Register', to: '/register' }" />
          </div>
          <!-- Authenticated -->
          <div v-else>
            <div class="flex items-center gap-3">
              <DropdownMenu>
                <DropdownMenuTrigger
                  class="border-l border-b hover:bg-slate-50 px-3 h-14 flex items-center gap-3 hover:cursor-pointer justify-center">
                  <img src="/assets/img/avatar-none.png" alt="avatar" class="h-10 w-10 rounded-full" />
                  <div class="flex flex-col items-start">
                    <div class="text-blue-950 font-medium">{{ storeAuth.user?.name ?? 'Username' }}</div>
                    <div class="text-blue-800 text-xs">{{ storeAuth.user?.role ?? 'Role' }}</div>
                  </div>
                </DropdownMenuTrigger>
                <DropdownMenuContent>
                  <DropdownMenuLabel>My Account</DropdownMenuLabel>
                  <DropdownMenuSeparator />
                  <DropdownMenuItem v-if="storeAuth.user?.role === 'Employee'"
                    @click="router.push('/employees/' + storeAuth.user?.id)">Profile</DropdownMenuItem>
                  <DropdownMenuItem v-if="storeAuth.user?.role === 'Manager'" @click="router.push('/profile')">Profile
                  </DropdownMenuItem>
                  <DropdownMenuItem v-if="storeAuth.user?.role === 'Customer'"
                    @click="router.push('/customers/' + storeAuth.user?.id)">Profile</DropdownMenuItem>
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
      <div class="flex items-center text-sm justify-end gap-5 h-10 p-3 border-b"
        v-if="storeAuth.user && storeAuth.user?.role !== 'Customer'">
        <MenuItem :item="{ label: 'Customers', to: '/customers' }" />
        <MenuItem v-if="storeAuth.user?.role === 'Manager'" :item="{ label: 'Employees', to: '/employees' }" />
        <MenuItem :item="{ label: 'Orders', to: '/orders' }" />
        <MenuItem :item="{ label: 'Products', to: '/products' }" />
      </div>
      <!-- Authenticated -->
      <div class="flex items-center text-sm justify-end gap-5 min-h-10 p-3 border-b"
        v-if="storeAuth.user && storeAuth.user?.role == 'Customer'">
        <MenuItem :item="{ label: 'My Orders', to: '/my-orders' }" />
        <MenuItem :item="{ label: 'Store', to: '/store' }" />
        <Dialog>
          <DialogTrigger>
            <Button variant="blue" class="cursor-default">
              <ShoppingBasket class="h-6 w-6" />
              <span class="text-blue-950 font-bold">{{ storeCart.cart.length ?? 0 }}</span>
            </Button>
          </DialogTrigger>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>My Cart</DialogTitle>
              <DialogDescription>
                Resume of your cart
              </DialogDescription>
            </DialogHeader>
            <div class="border rounded-lg">
              <Table v-if="storeCart.cart.length > 0">
                <TableHeader>
                  <TableRow>
                    <TableHead class="w-1/2">Product</TableHead>
                    <TableHead class="w-1/4">Type</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  <TableRow v-for="p in storeCart.cart" :key="p.id">
                    <TableCell>{{ p.name }}</TableCell>
                    <TableCell>{{ p.type }}</TableCell>
                    <TableCell class="text-xs text-red-600 cursor-pointer text-end"
                      @click="storeCart.removeFromCart(p)">Remove</TableCell>
                  </TableRow>
                  <TableRow>
                    <TableCell colspan="3">
                      <div class="grid grid-cols-2 gap-3 items-center">
                        <Label class="text-end">Payment Type</Label>
                        <Select v-model="cartCheckout.paymentType">
                          <SelectTrigger>
                            <SelectValue />
                          </SelectTrigger>
                          <SelectContent>
                            <SelectItem value="MBWAY">MBWAY</SelectItem>
                            <SelectItem value="MB">MB</SelectItem>
                            <SelectItem value="PAYPAL">PAYPAL</SelectItem>
                          </SelectContent>
                        </Select>
                      </div>
                    </TableCell>
                  </TableRow>
                  <TableRow>
                    <TableCell colspan="3" class="text-end">
                      <DialogClose as-child>
                        <Button :disabled="!cartCheckout.paymentType" variant="blue" @click="checkout()">Finish</Button>
                      </DialogClose>
                    </TableCell>
                  </TableRow>
                </TableBody>
              </Table>
              <div v-else class="text-center p-3">No products in the cart</div>
            </div>
          </DialogContent>
        </Dialog>

      </div>


      <div class="flex-grow flex flex-col items-center justify-center">
        <slot />
      </div>

      <div v-if="storeAuth.user"
        class="flex items-center justify-center h-10 p-3 border-t bg-blue-100 text-sm text-blue-800">
        Create random readings for all active sensors
        <Button @click="sendNewReadings()" variant='blue' class="ml-3">Send Readings</Button>
      </div>

      <div class="w-full bg-white border-t">

        <div class="p-3 bg-white md:flex md:items-center md:justify-between">
          <span class="flex text-sm text-gray-500 justify-center text-center">
            IPLeiria | Enterprise Applications Development | 2024-2025
          </span>
          <div class="flex justify-center space-x-2 rtl:space-x-reverse">
            <a id="DA" href="https://www.linkedin.com/in/diogo-abegao/"
              class="text-gray-400 hover:text-blue-800 text-sm p-1" @mouseover="showFullName('DA')"
              @mouseleave="hideFullName('DA')"> DA </a>
            <div class="text-gray-400 text-sm p-1">|</div>
            <a id="JP" href="https://www.linkedin.com/in/joao-parreira-dev/"
              class="text-gray-400 hover:text-blue-800 text-sm p-1" @mouseover="showFullName('JP')"
              @mouseleave="hideFullName('JP')"> JP </a>
            <div class="text-gray-400 text-sm p-1">|</div>
            <a id="MO" href="https://www.linkedin.com/in/marcelo-oliveira-89602b212/"
              class="text-gray-400 hover:text-blue-800 text-sm p-1" @mouseover="showFullName('MO')"
              @mouseleave="hideFullName('MO')"> MO </a>
            <div class="text-gray-400 text-sm p-1">|</div>
            <a id="PB" href="https://www.linkedin.com/in/pedrobarbeiro/"
              class="text-gray-400 hover:text-blue-800 text-sm p-1" @mouseover="showFullName('PB')"
              @mouseleave="hideFullName('PB')"> PB </a>
          </div>
        </div>
      </div>
    </Card>
  </div>
</template>
