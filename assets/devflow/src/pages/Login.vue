<!-- src/pages/Login.vue -->
<template>
    <BaseLayout>
      <div class="min-vh-100 d-flex align-items-center justify-content-center bg-dark text-white">
        <div class="card bg-secondary text-white p-4 shadow-lg" style="min-width: 350px; max-width: 400px;">
          <h2 class="text-center mb-4">🔐 Login to DevFlow</h2>
          <form @submit.prevent="handleLogin">
            <div class="mb-3">
              <label for="username" class="form-label">Username</label>
              <input v-model="username" type="text" class="form-control" id="username" required />
            </div>
            <div class="mb-3">
              <label for="password" class="form-label">Password</label>
              <input v-model="password" type="password" class="form-control" id="password" required />
            </div>
            <button type="submit" class="btn btn-success w-100">Login</button>
          </form>
          <p class="mt-3 text-center text-muted">
            Don't have an account?
            <router-link to="/register" class="text-white text-decoration-underline">Register here</router-link>
          </p>
        </div>
      </div>
    </BaseLayout>
  </template>
  
  <script setup lang="ts">
  import { ref } from 'vue'
  import BaseLayout from '../layouts/BaseLayout.vue'
  
  const username = ref('')
  const password = ref('')
  
  function handleLogin() {
    console.log('Logging in with:', username.value, password.value)
  }
  </script>
  