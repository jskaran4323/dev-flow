<script setup lang="ts">
import { computed } from 'vue'

interface BadgeProps {
  variant?: 'default' | 'secondary' | 'success' | 'warning' | 'danger' | 'info'
  rounded?: boolean
}

const props = withDefaults(
  defineProps<BadgeProps>(),
  { variant: 'default', rounded: false }
)

const variantClasses = {
  default: 'bg-accent text-accent-foreground',
  secondary: 'bg-secondary text-foreground',
  success: 'bg-[hsl(var(--success))] text-[hsl(var(--success-foreground))]',
  warning: 'bg-[hsl(var(--warning))] text-[hsl(var(--warning-foreground))]',
  danger: 'bg-destructive text-destructive-foreground',
  info: 'bg-[hsl(var(--info))] text-[hsl(var(--info-foreground))]',
} as const

const cls = computed(() => {
  const base = 'inline-flex items-center px-2 py-0.5 text-xs font-semibold'
  const shape = props.rounded ? 'rounded-full' : 'rounded'
  const color = variantClasses[props.variant]
  return [base, shape, color].join(' ')
})
</script>

<template>
  <span :class="cls"><slot /></span>
</template>
