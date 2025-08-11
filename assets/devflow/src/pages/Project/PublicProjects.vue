<template>
  <BaseLayout>
    <!-- Header -->
    <section class="py-6">
      <h2 class="text-2xl font-semibold tracking-tight">🌍 Public Projects</h2>
    </section>

    <!-- Loading -->
    <section v-if="publicProjectStore.loading" class="py-10 flex items-center gap-3">
      <Spinner />
      <span class="text-sm text-muted-foreground">Loading public projects…</span>
    </section>

    <!-- Empty -->
    <section v-else-if="filteredProjects.length === 0" class="py-6">
      <Card>
        <h3 class="text-lg font-medium">No public projects available</h3>
        <p class="text-sm text-muted-foreground mt-1">
          Check back later for new projects from other users.
        </p>
      </Card>
    </section>

    <!-- Grid -->
    <section v-else class="grid grid-cols-1 md:grid-cols-2 gap-6">
      <Card
        v-for="project in filteredProjects"
        :key="project.id"
        class="h-full flex flex-col"
      >
        <h3 class="text-lg font-semibold">{{ project.name }}</h3>
        <p class="mt-2 text-sm text-muted-foreground line-clamp-3">
          {{ project.description }}
        </p>
        <p class="mt-3 text-xs text-muted-foreground">
          by <span class="font-medium text-foreground">{{ project.owner.fullName || project.owner.username }}</span>
        </p>

        <template #footer>
          <div class="pt-4 flex justify-end">
            <Button
              as="router-link"
              :to="`/projects/${project.id}`"
              variant="secondary"
              size="sm"
            >
              View Details
            </Button>
          </div>
        </template>
      </Card>
    </section>
  </BaseLayout>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue'
import BaseLayout from '../../layouts/BaseLayout.vue'
import { usePublicProjectStore } from '../../stores/publicProject'
import { useAuthStore } from '../../stores/auth'
import Card from '../../components/ui/Card.vue'
import Button from '../../components/ui/Button.vue'
import Spinner from '../../components/ui/Spinner.vue'

const publicProjectStore = usePublicProjectStore()
const authStore = useAuthStore()

onMounted(() => {
  publicProjectStore.fetchProjects()
})

const filteredProjects = computed(() => {
  const currentUsername = authStore.user?.username
  return publicProjectStore.publicProjects.filter(
    (p) => p.owner.username !== currentUsername
  )
})
</script>
