
<template>
  <div class="min-vh-100 bg-dark text-white d-flex flex-column">
    <!-- Global Navbar -->
    <NavBar />

    <!-- Page Content -->
    <main class="container flex-grow-1 py-4">
      <slot />
    </main>
  </div>
</template>

<script setup lang="ts">
import NavBar from '../components/NavBar.vue'
</script>
