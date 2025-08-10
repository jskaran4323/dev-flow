<template>
  <nav class="bg-background border-b border-border shadow-sm">
    <div class="container flex h-14 items-center justify-between">
      <!-- Brand -->
      <router-link
        to="/"
        class="text-lg font-semibold text-foreground hover:text-primary transition-colors"
      >
        DevFlow
      </router-link>

      <!-- Desktop Menu -->
      <div class="hidden md:flex items-center gap-6">
        <!-- Guest Links -->
        <template v-if="!isAuthenticated">
          <router-link
            to="/login"
            class="text-sm font-medium text-muted-foreground hover:text-primary transition-colors"
          >
            Login
          </router-link>
          <router-link
            to="/register"
            class="text-sm font-medium text-muted-foreground hover:text-primary transition-colors"
          >
            Register
          </router-link>
        </template>

        <!-- Authenticated Dropdown -->
        <div v-else class="relative" @mouseleave="dropdownOpen = false">
          <button
            @click="dropdownOpen = !dropdownOpen"
            class="flex items-center gap-2 text-sm font-medium text-muted-foreground hover:text-primary transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 rounded-md px-2 py-1"
          >
            👤 Account
            <svg
              class="w-4 h-4 transition-transform"
              :class="{ 'rotate-180': dropdownOpen }"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="M19 9l-7 7-7-7" />
            </svg>
          </button>

          <!-- Dropdown menu -->
          <transition name="fade">
            <ul
              v-if="dropdownOpen"
              class="absolute right-0 mt-2 w-40 rounded-md border border-border bg-popover shadow-soft p-1"
            >
              <li>
                <router-link
                  to="/profile"
                  class="block w-full rounded-sm px-3 py-2 text-sm text-foreground hover:bg-muted hover:text-foreground transition-colors"
                >
                  Profile
                </router-link>
              </li>
              <li><div class="my-1 border-t border-border"></div></li>
              <li>
                <button
                  @click.prevent="handleLogout"
                  class="block w-full rounded-sm px-3 py-2 text-sm text-destructive hover:bg-muted transition-colors text-left"
                >
                  Logout
                </button>
              </li>
            </ul>
          </transition>
        </div>
      </div>
    </div>
  </nav>
</template>

<script setup lang="ts">
import { storeToRefs } from 'pinia'
import { useAuthStore } from '../stores/auth'
import { useRouter } from 'vue-router'
import { ref } from 'vue'

const auth = useAuthStore()
const { isAuthenticated } = storeToRefs(auth)
const router = useRouter()

const dropdownOpen = ref(false)

const handleLogout = () => {
  auth.logout()
  router.push('Login')
}
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.15s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
