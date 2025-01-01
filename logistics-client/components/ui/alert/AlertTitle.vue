<script setup lang="ts">
import type { HTMLAttributes } from 'vue'
import { cn } from '@/lib/utils'

const props = defineProps<{
  class?: HTMLAttributes['class']
}>()
</script>

<template>
  <div class="flex justify-between items-center">
    <h5 :class="cn('mb-1 font-medium leading-none tracking-tight', props.class)">
      <slot />
    </h5>
    <div>
      <button aria-label="Close" @click="$emit('close')">
        <svg class="w-4 h-4" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
          <path fill-rule="evenodd" clip-rule="evenodd" d="M10 8.586l3.293-3.293 1.414 1.414L11.414 10l3.293 3.293-1.414 1.414L10 11.414l-3.293 3.293-1.414-1.414L8.586 10 5.293 6.707l1.414-1.414L10 8.586z" />
        </svg>
      </button>
    </div>
  </div>
</template>
