<template>
  <div class="w-full p-3 bg-slate-50 border-b flex justify-between items-center">
    <Breadcrumb>
      <BreadcrumbList>
        <BreadcrumbItem>
          <BreadcrumbLink @click="$router.push('/')"> Home </BreadcrumbLink>
        </BreadcrumbItem>
        <BreadcrumbSeparator />
        <BreadcrumbItem>
          <BreadcrumbPage>Register</BreadcrumbPage>
        </BreadcrumbItem>
      </BreadcrumbList>
    </Breadcrumb>
    <Breadcrumb>
      <BreadcrumbList>
        <BreadcrumbItem>
          <BreadcrumbLink @click="$router.back"> Back </BreadcrumbLink>
        </BreadcrumbItem>
      </BreadcrumbList>
    </Breadcrumb>
  </div>
  <div class="my-auto sm:w-full">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm mb-5">
      <h2 class="mt-10 text-center text-2xl/9 font-bold tracking-tight text-gray-900">Create new account</h2>
    </div>

    <div class="w-full md:w-1/3 mx-auto p-3">
      <form>
        <div class="grid items-center w-full gap-5">
          <div class="flex flex-col space-y-1.5">
            <Label for="name">Username</Label>
            <Input id="name" type="text" placeholder="Username" v-model="credentials.name" />
            <ErrorMessage :errorMessage="storeError.fieldMessage('name')"></ErrorMessage>
          </div>
          <div class="flex flex-col space-y-1.5">
            <Label for="email">Email</Label>
            <Input id="email" type="email" placeholder="User Email" v-model="credentials.email" />
            <ErrorMessage :errorMessage="storeError.fieldMessage('email')"></ErrorMessage>
          </div>
          <div class="flex flex-col space-y-1.5">
            <Label for="password">Password</Label>
            <Input @keydown.enter="submit" id="password" type="password" placeholder="Password" v-model="credentials.password" />
            <ErrorMessage :errorMessage="storeError.fieldMessage('password')"></ErrorMessage>
          </div>
          <div class="flex flex-col space-y-1.5">
            <Label for="address">Address</Label>
            <Input id="address" type="text" placeholder="Adress" v-model="credentials.address" />
            <ErrorMessage :errorMessage="storeError.fieldMessage('address')"></ErrorMessage>
          </div>
        </div>
      </form>

      <div class="flex flex-row justify-between items-center gap-4 mt-5">
        <Button variant="outline" @click="cancel"> Cancel </Button>
        <Button @click="submit" variant="blue"> Submit </Button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import ErrorMessage from '~/components/ErrorMessage.vue'
import Input from '~/components/ui/input/Input.vue'
import Label from '~/components/ui/label/Label.vue'
import { useErrorStore } from '~/store/error.js'
import { useAPI } from '~/store/storeAPI'
import { useToast } from '@/components/ui/toast/use-toast'
import { useAuthStore } from '~/store/storeAuth'

const storeAuth = useAuthStore()
const api = useAPI()
const router = useRouter()
const storeError = useErrorStore()
const { toast } = useToast()

const credentials = ref({
  name: '',
  email: '',
  password: '',
  address: ''
})

const cancel = () => {
  router.back()
}

const submit = async () => {
  storeError.resetMessages()
  if (!credentials.value.name) {
    storeError.setFieldError('name', 'Username is required')
  }
  if (!credentials.value.email) {
    storeError.setFieldError('email', 'Email is required')
  }
  if (!credentials.value.email.includes('@')) {
    storeError.setFieldError('email', 'Invalid email')
  }
  if (!credentials.value.password) {
    storeError.setFieldError('password', 'Password is required')
  }
  if (credentials.value.password.length < 6) {
    storeError.setFieldError('password', 'Password must be at least 6 characters')
  }
  if (!credentials.value.address) {
    storeError.setFieldError('address', 'Address is required')
  }
  if (storeError.hasFieldErrors()) {
    return
  }

  const user = await api.createCustomer(credentials.value)
  if (user) {
    toast({
      title: 'User created successfully',
      variant: 'green'
    })
    storeAuth.login({
      email: credentials.value.email,
      password: credentials.value.password
    })
    router.push('/')
  }
}
</script>
