<template>
  <div class="min-h-screen bg-background text-foreground flex flex-col">
    <!-- Global Navbar -->
    <NavBar />

    <!-- Page Content -->
    <main class="container flex-1 py-6">
      <slot />
    </main>
  </div>
</template>

<script setup lang="ts">
import NavBar from '../components/NavBar.vue'
</script>
