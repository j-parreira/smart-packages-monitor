import { cva, type VariantProps } from 'class-variance-authority'

export { default as Toggle } from './Toggle.vue'

export const toggleVariants = cva(
  'inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors hover:bg-muted hover:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-80 data-[state=on]:bg-blue-50 data-[state=on]:border-blue-100 data-[state=on]:text-accent-foreground',
  {
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
        pink: 'bg-pink-50 text-pink-700 ring-1 ring-inset ring-pink-700/10 hover:bg-pink-100',
      },
      size: {
        default: 'h-10 px-3',
        sm: 'h-9 px-2.5',
        lg: 'h-11 px-5',
      },
    },
    defaultVariants: {
      variant: 'default',
      size: 'default',
    },
  },
)

export type ToggleVariants = VariantProps<typeof toggleVariants>
