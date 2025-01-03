// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  compatibilityDate: '2024-04-03',
  devtools: { enabled: false },
  runtimeConfig: {
    public: {
      API_URL: process.env.API_URL || 'http://localhost:8080/logistics/api/'
    }
  },
  modules: ['@pinia/nuxt', '@nuxtjs/tailwindcss', 'shadcn-nuxt', '@nuxt/icon'],
  app: {
    head: {
      title: 'DAE Smart Packages',
      link: [{ rel: 'icon', type: 'image/x-icon', href: '/logo.png' }]
    }
  }
})
