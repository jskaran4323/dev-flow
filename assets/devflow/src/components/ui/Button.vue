<script setup lang="ts">
import { computed, defineProps } from 'vue'
import { RouterLink } from 'vue-router'

type Variant = 'primary' | 'secondary' | 'outline' | 'ghost' | 'destructive'
type Size = 'sm' | 'md' | 'lg'

const props = defineProps<{
  as?: 'button' | 'a' | 'router-link'
  to?: string | Record<string, any> // for router-link
  href?: string                     // for anchor
  type?: 'button' | 'submit' | 'reset'
  disabled?: boolean
  variant?: Variant
  size?: Size
  class?: string
}>()

const base =
  'inline-flex items-center justify-center rounded-lg font-medium transition-colors ' +
  'focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 ' +
  'disabled:opacity-50 disabled:pointer-events-none'

const variants: Record<Variant, string> = {
  primary: 'bg-primary text-primary-foreground hover:opacity-90',
  secondary: 'border border-border bg-background text-foreground hover:bg-muted',
  outline: 'border border-border bg-transparent hover:bg-muted',
  ghost: 'bg-transparent hover:bg-muted',
  destructive:
    'border border-destructive/20 text-destructive hover:bg-destructive/10'
}

const sizes: Record<Size, string> = {
  sm: 'h-8 px-3 text-sm',
  md: 'h-9 px-4 text-sm',
  lg: 'h-10 px-5 text-base'
}

const cls = computed(() => {
  const v = props.variant ?? 'secondary'
  const s = props.size ?? 'md'
  return [base, variants[v], sizes[s], props.class].filter(Boolean).join(' ')
})
</script>

<template>
  <!-- Router link -->
  <RouterLink
    v-if="as === 'router-link' && to"
    :to="to"
    :class="cls"
  >
    <slot />
  </RouterLink>

  <!-- Anchor -->
  <a
    v-else-if="as === 'a' && href"
    :href="href"
    :class="cls"
  >
    <slot />
  </a>

  <!-- Button -->
  <button
    v-else
    :type="type ?? 'button'"
    :disabled="disabled"
    :class="cls"
  >
    <slot />
  </button>
</template>
