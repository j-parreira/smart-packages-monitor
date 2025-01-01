<script setup>
const props = defineProps({
  item: Object
})
</script>

<template>
  <Icon :name="item.icon" v-if="item.icon" class="mr-2" />
  <nuxt-link :to="item.to" v-if="item.to" exactActiveClass="text-blue-600">{{ item.label }}</nuxt-link>
  <a href="#" @click.prevent="item.onClick" v-else>{{ item.label }}</a>
</template>
