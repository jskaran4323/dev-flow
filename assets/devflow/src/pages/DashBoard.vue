<template>
  <BaseLayout>
    <!-- Page header -->
    <section class="py-6">
      <h2 class="text-2xl font-semibold tracking-tight">📊 Dashboard</h2>
      <p class="text-sm text-muted-foreground mt-1">Overview of your workspace</p>
    </section>

    <!-- Metrics -->
    <section class="grid grid-cols-1 md:grid-cols-3 gap-6">
      <div class="card">
        <div class="flex items-start justify-between">
          <h5 class="text-sm font-medium text-muted-foreground">📝 Total Issues</h5>
        </div>
        <p class="mt-3 text-4xl font-semibold">{{ totalIssues }}</p>
      </div>

      <div class="card">
        <div class="flex items-start justify-between">
          <h5 class="text-sm font-medium text-muted-foreground">📦 Projects</h5>
        </div>
        <p class="mt-3 text-4xl font-semibold">{{ totalProjects }}</p>
      </div>

      <div class="card">
        <div class="flex items-start justify-between">
          <h5 class="text-sm font-medium text-muted-foreground">💬 Comments</h5>
        </div>
        <p class="mt-3 text-4xl font-semibold">{{ totalComments }}</p>
      </div>
    </section>

    <!-- Quick Actions -->
    <section class="mt-8">
      <h4 class="text-lg font-semibold mb-3">Quick Actions</h4>
      <div class="flex flex-wrap gap-3">
        <router-link
          to="/projects"
          class="inline-flex h-9 items-center rounded-lg border border-border bg-background px-4 text-sm font-medium text-foreground hover:bg-muted transition-colors"
          >View Projects</router-link
        >
        <router-link
          to="/issues"
          class="inline-flex h-9 items-center rounded-lg border border-border bg-background px-4 text-sm font-medium text-foreground hover:bg-muted transition-colors"
          >View Issues</router-link
        >
        <router-link
          to="/select-project?next=create-issue"
          class="inline-flex h-9 items-center rounded-lg bg-primary px-4 text-sm font-medium text-primary-foreground hover:opacity-90 transition-opacity"
          >+ Create Issue</router-link
        >
      </div>
    </section>

    <!-- Public Projects -->
    <section class="mt-8">
      <router-link
        to="/public-projects"
        class="inline-flex h-9 items-center rounded-lg border border-border bg-background px-4 text-sm font-medium text-foreground hover:bg-muted transition-colors"
      >
        View All Public Projects
      </router-link>
    </section>
  </BaseLayout>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import BaseLayout from '../layouts/BaseLayout.vue'
import { useProjectStore } from '../stores/project'
import { usePublicProjectStore } from '../stores/publicProject'

const projectStore = useProjectStore()
const publicProjectStore = usePublicProjectStore()

onMounted(async () => {
  await projectStore.fetchAllProjects()
  await publicProjectStore.fetchProjects()
})

const totalProjects = computed(() => projectStore.projects.length)
const totalIssues = ref(12)
const totalComments = ref(27)
</script>
