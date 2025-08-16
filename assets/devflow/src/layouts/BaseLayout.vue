<!-- src/layouts/BaseLayout.vue -->
<template>
  <div class="min-h-screen bg-background text-foreground flex flex-col">
    <!-- Floating NavBar -->
    <div class="fixed top-0 left-0 right-0 z-50">
      <NavBar />
    </div>

    <!-- Page content wrapper with top padding -->
    <main class="container flex-1 pt-16" :class="contentClass">
      <slot />
    </main>
  </div>
</template>
<script setup lang="ts">
import NavBar from '../components/NavBar.vue'

// Allow passing in tailwind classes from pages
withDefaults(defineProps<{ contentClass?: string }>(), {
  contentClass: ''
})
</script>
