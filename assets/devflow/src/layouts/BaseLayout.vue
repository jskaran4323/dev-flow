<!-- src/layouts/BaseLayout.vue -->
<template>
    <div class="min-vh-100 bg-dark text-white">
      <NavBar/>
      <main class="container py-4">
        <slot />
      </main>
    </div>
  </template>
  
  <script setup lang="ts">
  import NavBar from '../components/NavBar.vue';
  </script>
  