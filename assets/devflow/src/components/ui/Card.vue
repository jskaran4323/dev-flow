<script setup lang="ts">
import { computed } from 'vue'

interface CardProps {
  padded?: boolean        // add inner padding
  shadow?: boolean        // soft drop shadow
  clickable?: boolean     // hover state + cursor
}

const props = withDefaults(
  defineProps<CardProps>(),
  { padded: true, shadow: true, clickable: false }
)

const rootClass = computed(() => [
  'bg-card text-card-foreground border border-border rounded-xl',
  props.shadow && 'shadow-soft',
  props.clickable && 'hover:bg-muted/50 transition-colors cursor-pointer'
].filter(Boolean).join(' '))

const contentClass = computed(() => [
  props.padded ? 'p-6' : ''
].join(' '))
</script>

<template>
  <section :class="rootClass">
    <!-- Header slot (optional) -->
    <header v-if="$slots.header" class="px-6 pt-6" :class="!padded && 'px-6 pt-6'">
      <slot name="header" />
    </header>

    <!-- Main content -->
    <div :class="contentClass">
      <slot />
    </div>

    <!-- Footer slot (optional) -->
    <footer v-if="$slots.footer" class="px-6 pb-6 border-t border-border" :class="!padded && 'px-6 pb-6'">
      <slot name="footer" />
    </footer>
  </section>
</template>
