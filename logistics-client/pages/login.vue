<template>
  <div class="w-full p-3 bg-slate-50 border-b">
    <Breadcrumb>
      <BreadcrumbList>
        <BreadcrumbItem>
          <BreadcrumbLink @click="$router.push('/')"> Home </BreadcrumbLink>
        </BreadcrumbItem>
        <BreadcrumbSeparator />
        <BreadcrumbItem>
          <BreadcrumbPage>Login</BreadcrumbPage>
        </BreadcrumbItem>
      </BreadcrumbList>
    </Breadcrumb>
  </div>
  <div class="my-auto sm:w-full">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm mb-5">
      <h2 class="mt-10 text-center text-2xl/9 font-bold tracking-tight text-gray-900">Sign in to your account</h2>
    </div>

    <div class="w-full md:w-1/3 mx-auto p-3">
      <form>
        <div class="grid items-center w-full gap-5">
          <div class="flex flex-col space-y-1.5">
            <Label for="email">Email</Label>
            <Input id="email" type="email" placeholder="User Email" v-model="credentials.email" />
            <ErrorMessage :errorMessage="storeError.fieldMessage('email')"></ErrorMessage>
          </div>
          <div class="flex flex-col space-y-1.5">
            <Label for="password">Password</Label>
            <Input @keydown.enter="login" id="password" type="password" placeholder="Password" v-model="credentials.password" />
            <ErrorMessage :errorMessage="storeError.fieldMessage('password')"></ErrorMessage>
          </div>
        </div>
      </form>

      <div class="flex flex-row justify-between items-center gap-4 mt-5">
        <Button variant="outline" @click="cancel"> Cancel </Button>
        <Button @click="login" variant="blue"> Login </Button>
      </div>

      <p class="mt-10 text-center text-sm/6 text-gray-500">
        Not a member?
        <a href="#" class="font-semibold text-blue-600 hover:text-blue-500">Click Here to Register</a>
      </p>
    </div>
  </div>
</template>

<script setup>
import { useAuthStore } from '~/store/storeAuth.js'
import { useErrorStore } from '~/store/error.js'
import { useRouter } from 'vue-router'
import ErrorMessage from '~/components/ErrorMessage.vue'
import Input from '~/components/ui/input/Input.vue'
import Label from '~/components/ui/label/Label.vue'

const router = useRouter()
const storeAuth = useAuthStore()
const storeError = useErrorStore()

const credentials = ref({
  email: '',
  password: ''
})

const cancel = () => {
  router.back()
}

const login = async () => {
  const user = await storeAuth.login(credentials.value)
  if (user) {
    router.back()
  }
}
</script>
