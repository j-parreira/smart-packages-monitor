import { cva, type VariantProps } from 'class-variance-authority'

export { default as Badge } from './Badge.vue'

export const badgeVariants = cva('inline-flex items-center rounded-full border-1 px-2.5 py-1 text-xs transition-colors focus:outline-none focus:ring-1 focus:ring-ring focus:ring-offset-2', {
  variants: {
    variant: {
      default: 'bg-primary text-primary-foreground shadow hover:bg-primary/90',
      destructive: 'bg-destructive text-destructive-foreground shadow-sm hover:bg-destructive/90',
      outline: 'border border-input bg-background shadow-sm hover:bg-accent hover:text-accent-foreground',
      secondary: 'bg-secondary text-secondary-foreground shadow-sm hover:bg-secondary/80',
      ghost: 'hover:bg-accent hover:text-accent-foreground',
      link: 'text-primary underline-offset-4 hover:underline',
      gray: 'bg-gray-50 text-gray-600 ring-1 ring-inset ring-gray-500/10 hover:bg-gray-100',
      red: 'bg-red-50 text-red-700 ring-1 ring-inset ring-red-600/10 hover:bg-red-100',
      yellow: 'bg-yellow-50 text-yellow-800 ring-1 ring-inset ring-yellow-600/20 hover:bg-yellow-100',
      green: 'bg-green-50 text-green-700 ring-1 ring-inset ring-green-600/20 hover:bg-green-100',
      blue: 'bg-blue-50 text-blue-700 ring-1 ring-inset ring-blue-700/10 hover:bg-blue-100',
      indigo: 'bg-indigo-50 text-indigo-700 ring-1 ring-inset ring-indigo-700/10 hover:bg-indigo-100',
      purple: 'bg-purple-50 text-purple-700 ring-1 ring-inset ring-purple-700/10 hover:bg-purple-100',
      pink: 'bg-pink-50 text-pink-700 ring-1 ring-inset ring-pink-700/10 hover:bg-pink-100'
    }
  },
  defaultVariants: {
    variant: 'default'
  }
})

export type BadgeVariants = VariantProps<typeof badgeVariants>
