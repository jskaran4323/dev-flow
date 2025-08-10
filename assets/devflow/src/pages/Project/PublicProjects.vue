<template>
  <BaseLayout>
    <!-- Header -->
    <section class="py-6">
      <h2 class="text-2xl font-semibold tracking-tight">🌍 Public Projects</h2>
      <p class="text-sm text-muted-foreground mt-1">
        Discover projects you can explore and join
      </p>
    </section>

    <!-- Loading -->
    <section v-if="publicProjectStore.loading" class="py-10">
      <p class="text-sm text-muted-foreground">Loading public projects...</p>
    </section>

    <!-- Empty -->
    <section v-else-if="filteredProjects.length === 0" class="py-10">
      <div class="card">
        <h3 class="text-lg font-medium">No public projects available</h3>
        <p class="text-sm text-muted-foreground mt-1">
          Check back later or create a project and make it public.
        </p>
      </div>
    </section>

    <!-- Grid -->
    <section v-else class="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">
      <article
        v-for="project in filteredProjects"
        :key="project.id"
        class="card h-full flex flex-col"
      >
        <header>
          <h3 class="text-lg font-semibold">{{ project.name }}</h3>
          <p class="text-sm text-muted-foreground mt-1 line-clamp-3">
            {{ project.description }}
          </p>
        </header>

        <footer class="mt-4 flex items-center justify-between">
          <div class="text-xs text-muted-foreground">
            by <span class="font-medium text-foreground">{{ project.owner.username }}</span>
          </div>
          <router-link
            :to="`/projects/${project.id}`"
            class="inline-flex h-9 items-center rounded-lg border border-border bg-background px-3 text-sm font-medium hover:bg-muted transition-colors"
          >
            View Details
          </router-link>
        </footer>
      </article>
    </section>
  </BaseLayout>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue'
import { usePublicProjectStore } from '../../stores/publicProject'
import { useAuthStore } from '../../stores/auth'
import BaseLayout from '../../layouts/BaseLayout.vue'

const publicProjectStore = usePublicProjectStore()
const authStore = useAuthStore()

onMounted(() => {
  publicProjectStore.fetchProjects()
})

const filteredProjects = computed(() => {
  const currentUsername = authStore.user?.username
  return publicProjectStore.publicProjects.filter(
    (project) => project.owner.username !== currentUsername
  )
})
</script>
