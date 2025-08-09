<!-- src/pages/Register.vue -->
<template>
  <BaseLayout>
    <div class="min-vh-100 d-flex align-items-center justify-content-center bg-dark text-white">
      <div class="card bg-secondary text-white p-4 shadow-lg" style="min-width: 350px; max-width: 400px;">
        <h2 class="text-center mb-4">📝 Register for DevFlow</h2>
        <form @submit.prevent="handleRegister">
          <div class="mb-3">
            <label for="fullName" class="form-label">Full Name</label>
            <input v-model="fullName" type="text" class="form-control" id="fullName" required />
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
          <div class="mb-3">
            <label for="role" class="form-label">Role</label>
            <select v-model="role" class="form-select" required>
              <option :value="2">Developer</option>
              <option :value="3">Tester</option>
              <option :value="4">Viewer</option>
            </select>
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
import { useRouter } from 'vue-router'
import { registerUser } from '../services/authRequests'

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
