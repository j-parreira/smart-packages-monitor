import type { ToastRootProps } from 'radix-vue'
import type { HTMLAttributes } from 'vue'

export { default as Toast } from './Toast.vue'
export { default as ToastAction } from './ToastAction.vue'
export { default as ToastClose } from './ToastClose.vue'
export { default as ToastDescription } from './ToastDescription.vue'
export { default as Toaster } from './Toaster.vue'
export { default as ToastProvider } from './ToastProvider.vue'
export { default as ToastTitle } from './ToastTitle.vue'
export { default as ToastViewport } from './ToastViewport.vue'
export { toast, useToast } from './use-toast'

import { cva, type VariantProps } from 'class-variance-authority'

export const toastVariants = cva(
  'group pointer-events-auto relative flex w-full items-center justify-between space-x-4 overflow-hidden rounded-md p-6 pr-8 shadow-lg transition-all data-[swipe=cancel]:translate-x-0 data-[swipe=end]:translate-x-[--radix-toast-swipe-end-x] data-[swipe=move]:translate-x-[--radix-toast-swipe-move-x] data-[swipe=move]:transition-none data-[state=open]:animate-in data-[state=closed]:animate-out data-[swipe=end]:animate-out data-[state=closed]:fade-out-80 data-[state=closed]:slide-out-to-right-full data-[state=open]:slide-in-from-top-full data-[state=open]:sm:slide-in-from-bottom-full',
  {
    variants: {
      variant: {
        default: 'rounded-md bg-gray-50 text-gray-600 ring-1 ring-inset ring-gray-500/10',
        red: 'rounded-md bg-red-50 text-red-700 ring-1 ring-inset ring-red-600/10',
        yellow: 'rounded-md bg-yellow-50 text-yellow-800 ring-1 ring-inset ring-yellow-600/20',
        green: 'rounded-md bg-green-50 text-green-700 ring-1 ring-inset ring-green-600/20',
        blue: 'rounded-md bg-blue-50 text-blue-700 ring-1 ring-inset ring-blue-700/10',
        indigo: 'rounded-md bg-indigo-50 text-indigo-700 ring-1 ring-inset ring-indigo-700/10',
        purple: 'rounded-md bg-purple-50 text-purple-700 ring-1 ring-inset ring-purple-700/10',
        pink: 'rounded-md bg-pink-50 text-pink-700 ring-1 ring-inset ring-pink-700/10',
        outline: 'border border-input bg-background shadow-sm hover:bg-accent hover:text-accent-foreground',
        secondary: 'bg-secondary text-secondary-foreground shadow-sm hover:bg-secondary/80',
        ghost: 'hover:bg-accent hover:text-accent-foreground',
        link: 'text-primary underline-offset-4 hover:underline'
      }
    },
    defaultVariants: {
      variant: 'default'
    }
  }
)

type ToastVariants = VariantProps<typeof toastVariants>

export interface ToastProps extends ToastRootProps {
  class?: HTMLAttributes['class']
  variant?: ToastVariants['variant']
  onOpenChange?: ((value: boolean) => void) | undefined
}
