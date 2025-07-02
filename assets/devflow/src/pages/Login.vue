<!-- src/pages/Login.vue -->
<template>
  <BaseLayout>
    <div class="min-vh-100 d-flex align-items-center justify-content-center bg-dark text-white">
      <div class="card bg-secondary text-white p-4 shadow-lg" style="min-width: 350px; max-width: 400px;">
        <h2 class="text-center mb-4">🔐 Login to DevFlow</h2>
        <form @submit.prevent="handleLogin">
          <div class="mb-3">
            <label for="identifier" class="form-label">Username or Email</label>
            <input v-model="identifier" type="text" class="form-control" id="identifier" required />
          </div>
          <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input v-model="password" type="password" class="form-control" id="password" required />
          </div>
          <button type="submit" class="btn btn-success w-100">Login</button>
          <!-- Changed text-red-500 to Bootstrap's text-danger -->
          <p class="text-danger mt-2" v-if="errorMessage">{{ errorMessage }}</p>                     
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
import { useRouter } from 'vue-router'
import { loginUser } from '../services/authRequests'
import { useAuthStore } from '../stores/auth'

const identifier = ref('')
const password = ref('')
const errorMessage = ref('')
const router = useRouter()
const auth = useAuthStore()

const handleLogin = async () => {
  try {

    const res = await loginUser({identifier: identifier.value, password: password.value})
    
    auth.login(res.token)  
  
    router.push('/dashboard')
  }
  catch(error: any) {
    errorMessage.value = error.response?.data?.errorMessage || "login failed"
  }
}
</script>