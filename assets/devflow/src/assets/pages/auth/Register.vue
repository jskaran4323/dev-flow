<template>
    <div class="min-h-screen flex items-center justify-center bg-surface text-text px-4">
      <div class="bg-card p-8 rounded-2xl shadow-xl w-full max-w-md">
        <h2 class="text-2xl font-bold mb-6 text-center">📝 Register for DevFlow</h2>
        <form @submit.prevent="handleRegister">
          <input v-model="username" type="text" placeholder="Username"
            class="w-full p-3 mb-4 bg-surface border border-gray-600 rounded-xl" />
          <input v-model="email" type="email" placeholder="Email"
            class="w-full p-3 mb-4 bg-surface border border-gray-600 rounded-xl" />
          <input v-model="password" type="password" placeholder="Password"
            class="w-full p-3 mb-6 bg-surface border border-gray-600 rounded-xl" />
          <button type="submit"
            class="w-full bg-primary hover:bg-emerald-600 text-white font-semibold py-2 rounded-xl transition duration-300">
            Register
          </button>
        </form>
        <p class="mt-4 text-sm text-center text-gray-400">
          Already have an account?
          <RouterLink to="/login" class="text-primary hover:underline">Login</RouterLink>
        </p>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref } from 'vue'
  
  const username = ref('')
  const email = ref('')
  const password = ref('')
  
  const handleRegister = () => {
    console.log('Registering:', username.value, email.value, password.value)
    // Call auth service here
  }
  </script>
  