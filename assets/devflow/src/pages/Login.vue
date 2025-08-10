<!-- src/pages/Login.vue -->
<template>
  <BaseLayout>
    <section class="py-12 flex items-center justify-center">
      <div class="card w-full max-w-sm">
        <h2 class="text-center text-2xl font-semibold tracking-tight">🔐 Login to DevFlow</h2>

        <form @submit.prevent="handleLogin" class="mt-6 space-y-4">
          <!-- Username / Email -->
          <div>
            <label for="identifier" class="block text-sm font-medium mb-1">Username or Email</label>
            <input
              v-model="identifier"
              id="identifier"
              type="text"
              required
              class="w-full rounded-lg border border-input bg-background px-3 py-2 text-sm outline-none focus-visible:ring-2 focus-visible:ring-ring"
            />
          </div>

          <!-- Password -->
          <div>
            <label for="password" class="block text-sm font-medium mb-1">Password</label>
            <input
              v-model="password"
              id="password"
              type="password"
              required
              class="w-full rounded-lg border border-input bg-background px-3 py-2 text-sm outline-none focus-visible:ring-2 focus-visible:ring-ring"
            />
          </div>

          <!-- Submit -->
          <button
            type="submit"
            class="w-full inline-flex h-10 items-center justify-center rounded-lg bg-primary px-4 text-sm font-medium text-primary-foreground hover:opacity-90 transition-opacity"
          >
            Login
          </button>

          <!-- Error -->
          <p v-if="errorMessage" class="text-sm text-destructive">
            {{ errorMessage }}
          </p>
        </form>

        <p class="mt-4 text-center text-sm text-muted-foreground">
          Don’t have an account?
          <router-link to="/register" class="font-medium text-foreground hover:text-primary transition-colors">
            Register here
          </router-link>
        </p>
      </div>
    </section>
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
    const res = await loginUser({ identifier: identifier.value, password: password.value })
    auth.login(res.token)
    router.push('/dashboard')
  } catch (error: any) {
    errorMessage.value = error.response?.data?.errorMessage || 'login failed'
  }
}
</script>
