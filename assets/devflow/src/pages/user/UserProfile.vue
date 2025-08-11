<template>
  <BaseLayout>
    <!-- Header -->
    <section class="py-6">
      <h2 class="text-2xl font-semibold tracking-tight">👤 My Profile</h2>
      <p class="text-sm text-muted-foreground mt-1">Your account details</p>
    </section>

    <!-- Loading -->
    <section v-if="!authStore.user" class="py-6 flex items-center gap-3">
      <Spinner />
      <span class="text-sm text-muted-foreground">Loading profile…</span>
    </section>

    <!-- Profile card -->
    <section v-else class="max-w-md">
      <Card>
        <div class="flex items-center gap-4">
          <Avatar :fallback="initials" sizeClass="w-12 h-12" />
          <div>
            <h3 class="text-lg font-semibold">{{ authStore.user.fullName }}</h3>
            <p class="text-sm text-muted-foreground">@{{ authStore.user.username }}</p>
          </div>
        </div>

        <div class="mt-5 space-y-3">
          <div class="flex items-center justify-between">
            <span class="text-sm text-muted-foreground">Full Name</span>
            <span class="text-sm font-medium text-foreground">{{ authStore.user.fullName }}</span>
          </div>
          <div class="flex items-center justify-between">
            <span class="text-sm text-muted-foreground">Username</span>
            <span class="text-sm font-medium text-foreground">{{ authStore.user.username }}</span>
          </div>
          <div class="flex items-center justify-between">
            <span class="text-sm text-muted-foreground">User Type</span>
            <span class="text-sm font-medium text-foreground">{{ authStore.user.userType }}</span>
          </div>
        </div>

        <template #footer>
          <div class="pt-4 flex justify-start">
            <Button as="router-link" :to="'/'" variant="secondary">
              ← Back to Dashboard
            </Button>
          </div>
        </template>
      </Card>
    </section>
  </BaseLayout>
</template>

<script setup lang="ts">
import { onMounted, computed, watch } from 'vue'
import BaseLayout from '../../layouts/BaseLayout.vue'
import { useAuthStore } from '../../stores/auth'
import Card from '../../components/ui/Card.vue'
import Button from '../../components/ui/Button.vue'
import Spinner from '../../components/ui/Spinner.vue'
import Avatar from '../../components/ui/Avatar.vue'

const authStore = useAuthStore()

onMounted(async () => {
  if (!authStore.user) {
    // Store can decide internally whether it has a token to fetch with
    await authStore.fetchUser().catch(() => {})
  }
})

watch(
  () => authStore.user,
  () => {},
  { immediate: true }
)

const initials = computed(() => {
  const name = authStore.user?.fullName || ''
  return name
    .split(' ')
    .map((n) => n[0])
    .join('')
    .toUpperCase()
    .slice(0, 2) || '👤'
})
</script>

