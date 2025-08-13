<template>
  <nav class="bg-background border-b border-border shadow-sm">
    <div class="container flex h-14 items-center justify-between">
      <router-link to="/" class="flex items-center gap-2" aria-label="DevFlow Home">
        <span class="text-3xl font-extrabold tracking-tight text-foreground">
          Dev<span class="text-primary">Flow</span>
        </span>
      </router-link>

      <div class="hidden md:flex items-center gap-6">
        <router-link to="/about" class="text-sm font-medium text-muted-foreground hover:text-primary transition-colors">About</router-link>
        <router-link to="/team" class="text-sm font-medium text-muted-foreground hover:text-primary transition-colors">Team</router-link>

        
        <template v-if="!initialized">
          <div class="text-sm text-muted-foreground animate-pulse">•••</div>
        </template>

       
        <template v-else-if="!isAuthenticated">
          <router-link to="/login" class="text-sm font-medium text-muted-foreground hover:text-primary transition-colors">Sign In</router-link>
          
        </template>

        
        <template v-else>
          <div class="relative" ref="menuRef">
            <button
              ref="buttonRef"
              @click="toggleDropdown"
              @keydown.escape="closeDropdown"
              class="flex items-center gap-2 text-sm font-medium text-muted-foreground hover:text-primary transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 rounded-md px-2 py-1"
              aria-haspopup="menu"
              :aria-expanded="dropdownOpen"
            >
              👤 {{ user?.username || 'Account' }}
              <svg class="w-4 h-4 transition-transform" :class="{ 'rotate-180': dropdownOpen }" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
              </svg>
            </button>

            <transition name="fade">
              <ul
                v-if="dropdownOpen"
                class="absolute right-0 mt-2 w-44 rounded-md border border-border bg-background shadow-soft p-1 z-50"
              
              >
                <li>
                  <router-link
                    to="/profile"
                    class="block w-full rounded-sm px-3 py-2 text-sm text-foreground hover:bg-muted transition-colors"
                    role="menuitem"
                    @click="closeDropdown"
                  >
                    Profile
                  </router-link>
                </li>
                <li><div class="my-1 border-t border-border"></div></li>
                <li>
                  <button
                    @click.prevent="handleLogout"
                    class="block w-full rounded-sm px-3 py-2 text-sm text-destructive hover:bg-muted transition-colors text-left"
                    role="menuitem"
                  >
                    Logout
                  </button>
                </li>
              </ul>
            </transition>
          </div>
        </template>
      </div>
    </div>
  </nav>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { storeToRefs } from 'pinia'
import { useAuthStore } from '../stores/auth'
import { useRouter } from 'vue-router'

const auth = useAuthStore()
const { isAuthenticated, initialized, user } = storeToRefs(auth)
const router = useRouter()

const dropdownOpen = ref(false)
const menuRef = ref<HTMLElement | null>(null)
const buttonRef = ref<HTMLElement | null>(null)

const toggleDropdown = () => (dropdownOpen.value = !dropdownOpen.value)
const closeDropdown = () => (dropdownOpen.value = false)

const onClickOutside = (e: MouseEvent) => {
  const t = e.target as Node
  if (!menuRef.value) return
  if (!menuRef.value.contains(t) && buttonRef.value && !buttonRef.value.contains(t)) {
    dropdownOpen.value = false
  }
}

onMounted(async () => {
  document.addEventListener('click', onClickOutside)
  

  if (!initialized.value) {
    await auth.initializeAuth()
  }
})

onBeforeUnmount(() => {
  document.removeEventListener('click', onClickOutside)
})

const handleLogout = async () => {
  try {
    await auth.logout()               
    closeDropdown()
    await router.replace({ name: 'LoginPage' })
  } catch (e) {
    console.error('Logout error', e)
  }
}
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active { transition: opacity 0.15s ease; }
.fade-enter-from,
.fade-leave-to { opacity: 0; }
</style>
