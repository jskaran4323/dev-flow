<!-- src/pages/Register.vue -->
<template>
    <BaseLayout>
      <div class="min-vh-100 d-flex align-items-center justify-content-center bg-dark text-white">
        <div class="card bg-secondary text-white p-4 shadow-lg" style="min-width: 350px; max-width: 400px;">
          <h2 class="text-center mb-4">📝 Register for DevFlow</h2>
          <form @submit.prevent="handleRegister">
            <div class="mb-3">
              <label for="fullName" class="form-label">Full Name</label>
              <input v-model="fullName" type="fullName" class="form-control" id="fullName" required />
            </div>
            <div class="mb-3">
              <label for="username" class="form-label">Username</label>
              <input v-model="username" type="text" class="form-control" id="username" required />
            </div>
            <div class="mb-3">
              <label for="email" class="form-label">Email</label>
              <input v-model="email" type="email" class="form-control" id="email" required />
            </div>
            <div class="mb-3">
              <label for="password" class="form-label">Password</label>
              <input v-model="password" type="password" class="form-control" id="password" required />
            </div>
          
            <button type="submit" class="btn btn-primary w-100">Register</button>
          </form>
          <p class="mt-3 text-center text-muted">
            Already have an account?
            <router-link to="/login" class="text-white text-decoration-underline">Login here</router-link>
          </p>
        </div>
      </div>
    </BaseLayout>
  </template>
  
  <script setup lang="ts">
  import { ref } from 'vue'
import BaseLayout from '../layouts/BaseLayout.vue'
import { registerUser } from '../services/authRequests'

import { useRouter } from 'vue-router'
  
  const username = ref('')
  const email = ref('')
  const password = ref('')
  const fullName = ref('')
  const errorMessage = ref('')

  const router = useRouter()
  const handleRegister = async() => {
    try{
      await registerUser({username: username.value,email: email.value, password: password.value, fullName: fullName.value}
      )
      router.push('/login')
    }catch (error: any) {
    errorMessage.value = error.response?.data?.message || 'Registration failed'
  }
  }
  </script>
  