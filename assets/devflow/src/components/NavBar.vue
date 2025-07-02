<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark border-bottom border-secondary shadow-sm">
    <div class="container">
      <router-link class="navbar-brand fw-bold text-white" to="/">DevFlow</router-link>

      <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
        <ul class="navbar-nav">
          <li class="nav-item" v-if="!isAuthenticated">
  <router-link class="nav-link" to="/login">Login</router-link>
</li>
<li class="nav-item" v-if="!isAuthenticated">
  <router-link class="nav-link" to="/register">Register</router-link>
</li>
<li class="nav-item" v-if="isAuthenticated">
  <a class="nav-link" href="#" @click.prevent="handleLogout">Logout</a>
</li>

        </ul>
      </div>
    </div>
  </nav>
</template>

<script setup lang="ts">
import { storeToRefs } from 'pinia'
import { useAuthStore } from '../stores/auth'
import { useRouter } from 'vue-router'

const auth = useAuthStore()
const router = useRouter()
const { isAuthenticated } = storeToRefs(auth)
const handleLogout = () => {
  auth.logout()
  router.push('/login')
}
</script>
