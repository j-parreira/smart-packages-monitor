// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  compatibilityDate: '2024-04-03',
  devtools: { enabled: true },
  runtimeConfig: {
    public: {
      API_URL: process.env.API_URL || 'http://localhost:8080/academics/api'
    }
  },
  modules: ['@pinia/nuxt', '@nuxtjs/tailwindcss', 'shadcn-nuxt', '@nuxt/icon'],
  shadcn: {
    prefix: '',
    componentDir: './components/ui'
  }
})