<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark border-bottom border-secondary shadow-sm">
    <div class="container">
      <router-link class="navbar-brand fw-bold text-white" to="/">DevFlow</router-link>

      <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
        <ul class="navbar-nav">
          <!-- Guest Links -->
          <li class="nav-item" v-if="!isAuthenticated">
            <router-link class="nav-link" to="/login">Login</router-link>
          </li>
          <li class="nav-item" v-if="!isAuthenticated">
            <router-link class="nav-link" to="/register">Register</router-link>
          </li>

          <!-- Authenticated Dropdown -->
          <li class="nav-item dropdown" v-if="isAuthenticated">
            <a
              class="nav-link dropdown-toggle"
              href="#"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              👤 Account
            </a>
            <ul class="dropdown-menu dropdown-menu-end bg-dark border-secondary shadow">
              <li>
                <router-link class="dropdown-item text-white" to="/profile">Profile</router-link>
              </li>
              <li>
                <hr class="dropdown-divider border-secondary" />
              </li>
              <li>
                <a class="dropdown-item text-danger" href="#" @click.prevent="handleLogout">Logout</a>
              </li>
            </ul>
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

const { isAuthenticated } = storeToRefs(auth)
const router = useRouter()

const handleLogout = () => {
  auth.logout()
  router.push("Login")
}
</script>
