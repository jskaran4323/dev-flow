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
            <Input id="fullName" v-model="fullName" type="text" required />
          </div>

          <!-- Username -->
          <div>
            <label for="username" class="block text-sm font-medium mb-1">Username</label>
            <Input id="username" v-model="username" type="text" required />
          </div>

          <!-- Email -->
          <div>
            <label for="email" class="block text-sm font-medium mb-1">Email</label>
            <Input id="email" v-model="email" type="email" required />
          </div>

          <!-- Password -->
          <div>
            <label for="password" class="block text-sm font-medium mb-1">Password</label>
            <Input id="password" v-model="password" type="password" required />
          </div>

          <!-- Role -->
          <div>
            <label for="role" class="block text-sm font-medium mb-1">Role</label>
            <Select
              id="role"
              v-model="role"
              :options="roleOptions"
              required
            />
          </div>

          <!-- Submit -->
          <Button type="submit" variant="primary" size="lg" class="w-full">
            Register
          </Button>

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
import BaseLayout from '../layouts/BaseLayout.vue'
import { useRouter } from 'vue-router'
import { registerUser } from '../services/authRequests'
import Input from '../components/ui/Input.vue'
import Select from '../components/ui/Select.vue'
import Button from '../components/ui/Button.vue'

const fullName = ref('')
const username = ref('')
const email = ref('')
const password = ref('')
// Select emits strings; allow string|number here, coerce before submit
const role = ref<string | number>(4)

const errorMessage = ref('')
const router = useRouter()

const roleOptions = [
  { label: 'Developer', value: 2 },
  { label: 'Tester', value: 3 },
  { label: 'Viewer', value: 4 },
]

const handleRegister = async () => {
  try {
    await registerUser({
      fullName: fullName.value,
      username: username.value,
      email: email.value,
      password: password.value,
      type: Number(role.value), // ensure backend gets a number
    })
    router.push('/login')
  } catch (error: any) {
    errorMessage.value = error.response?.data?.message || 'Registration failed'
  }
}
</script>
