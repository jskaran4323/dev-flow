<template>
    <div class="min-h-screen flex items-center justify-center bg-surface text-text px-4">
      <div class="bg-card p-8 rounded-2xl shadow-xl w-full max-w-md">
        <h2 class="text-2xl font-bold mb-6 text-center">🔐 Login to DevFlow</h2>
        <form @submit.prevent="handleLogin">
          <input v-model="email" type="email" placeholder="Email"
            class="w-full p-3 mb-4 bg-surface border border-gray-600 rounded-xl focus:outline-none focus:ring-2 focus:ring-primary" />
          <input v-model="password" type="password" placeholder="Password"
            class="w-full p-3 mb-6 bg-surface border border-gray-600 rounded-xl focus:outline-none focus:ring-2 focus:ring-primary" />
          <button type="submit"
            class="w-full bg-primary hover:bg-emerald-600 text-white font-semibold py-2 rounded-xl transition duration-300">
            Login
          </button>
        </form>
        <p class="mt-4 text-sm text-center text-gray-400">
          Don’t have an account?
          <RouterLink to="/register" class="text-primary hover:underline">Register</RouterLink>
        </p>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref } from 'vue'
  
  const email = ref('')
  const password = ref('')
  
  const handleLogin = () => {
    console.log('Logging in with:', email.value, password.value)
    // Call auth service here
  }
  </script>
  