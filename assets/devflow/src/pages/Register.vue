<!-- src/pages/Register.vue -->
<template>
  <BaseLayout>
    <section class="py-12 flex items-center justify-center">
      <div class="card w-full max-w-sm">
        <h2 class="text-center text-2xl font-semibold tracking-tight">📝 Register for DevFlow</h2>

        <form @submit.prevent="handleRegister" class="mt-6 space-y-4">
          <!-- Full Name -->
          <div>
            <label for="fullName" class="block text-sm font-medium mb-1">Full Name</label>
            <input
              v-model="fullName"
              id="fullName"
              type="text"
              required
              class="w-full rounded-lg border border-input bg-background px-3 py-2 text-sm outline-none focus-visible:ring-2 focus-visible:ring-ring"
            />
          </div>

          <!-- Username -->
          <div>
            <label for="username" class="block text-sm font-medium mb-1">Username</label>
            <input
              v-model="username"
              id="username"
              type="text"
              required
              class="w-full rounded-lg border border-input bg-background px-3 py-2 text-sm outline-none focus-visible:ring-2 focus-visible:ring-ring"
            />
          </div>

          <!-- Email -->
          <div>
            <label for="email" class="block text-sm font-medium mb-1">Email</label>
            <input
              v-model="email"
              id="email"
              type="email"
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

          <!-- Role -->
          <div>
            <label for="role" class="block text-sm font-medium mb-1">Role</label>
            <select
              v-model="role"
              id="role"
              required
              class="w-full rounded-lg border border-input bg-background px-3 py-2 text-sm outline-none focus-visible:ring-2 focus-visible:ring-ring"
            >
              <option :value="2">Developer</option>
              <option :value="3">Tester</option>
              <option :value="4">Viewer</option>
            </select>
          </div>

          <!-- Submit -->
          <button
            type="submit"
            class="w-full inline-flex h-10 items-center justify-center rounded-lg bg-primary px-4 text-sm font-medium text-primary-foreground hover:opacity-90 transition-opacity"
          >
            Register
          </button>

          <!-- Error -->
          <p v-if="errorMessage" class="text-sm text-destructive">
            {{ errorMessage }}
          </p>
        </form>

        <p class="mt-4 text-center text-sm text-muted-foreground">
          Already have an account?
          <router-link to="/login" class="font-medium text-foreground hover:text-primary transition-colors">
            Login here
          </router-link>
        </p>
      </div>
    </section>
  </BaseLayout>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { registerUser } from '../services/authRequests'
import BaseLayout from '../layouts/BaseLayout.vue'

const fullName = ref('')
const username = ref('')
const email = ref('')
const password = ref('')
const role = ref(4) // default = Viewer

const errorMessage = ref('')
const router = useRouter()

const handleRegister = async () => {
  try {
    await registerUser({
      fullName: fullName.value,
      username: username.value,
      email: email.value,
      password: password.value,
      type: role.value
    })
    router.push('/login')
  } catch (error: any) {
    errorMessage.value = error.response?.data?.message || 'Registration failed'
  }
}
</script>
