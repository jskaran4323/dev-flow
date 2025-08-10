<template>
  <BaseLayout>
    <!-- Header -->
    <section class="py-6 flex flex-col gap-3 md:flex-row md:items-center md:justify-between">
      <h2 class="text-2xl font-semibold tracking-tight">📦 My Projects</h2>
      <router-link
        to="/projects/new"
        class="inline-flex h-9 items-center rounded-lg bg-primary px-4 text-sm font-medium text-primary-foreground hover:opacity-90 transition-opacity"
      >
        + New Project
      </router-link>
    </section>

    <!-- Empty state -->
    <section v-if="projects.length === 0" class="py-6">
      <div class="card">
        <h3 class="text-lg font-medium">You don’t have any projects yet</h3>
        <p class="text-sm text-muted-foreground mt-1">
          Click “New Project” to create one.
        </p>
      </div>
    </section>

    <!-- Project grid -->
    <section v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <article
        v-for="project in projects"
        :key="project.id"
        class="card h-full flex flex-col cursor-pointer hover:bg-muted/50 transition-colors"
        @click="goToProjectDetails(project.id)"
      >
        <!-- Body -->
        <div class="flex-1">
          <h3 class="text-lg font-semibold">{{ project.name }}</h3>
          <p class="mt-2 text-sm text-muted-foreground line-clamp-3">
            {{ project.description }}
          </p>
          <span
            class="mt-3 inline-flex items-center rounded-full bg-secondary px-2.5 py-1 text-xs font-medium text-foreground"
          >
            {{ project.status }}
          </span>
        </div>

        <!-- Footer actions -->
        <div
          class="mt-4 pt-4 border-t border-border flex items-center justify-between"
          @click.stop
        >
          <div class="flex items-center gap-2">
            <router-link
              :to="`/projects/${project.id}/edit`"
              class="inline-flex h-8 items-center rounded-lg border border-border bg-background px-3 text-sm font-medium hover:bg-muted transition-colors"
            >
              Edit
            </router-link>
            <router-link
              :to="`/projects/${project.id}/kanban`"
              class="inline-flex h-8 items-center rounded-lg border border-border bg-background px-3 text-sm font-medium hover:bg-muted transition-colors"
            >
              📊 Kanban
            </router-link>
          </div>

          <button
            class="inline-flex h-8 items-center rounded-lg border border-destructive/20 text-destructive px-3 text-sm font-medium hover:bg-destructive/10 transition-colors"
            @click="deleteProject(project.id)"
          >
            Delete
          </button>
        </div>
      </article>
    </section>
  </BaseLayout>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { storeToRefs } from 'pinia'
import BaseLayout from '../../layouts/BaseLayout.vue'
import { useProjectStore } from '../../stores/project'

const router = useRouter()
const projectStore = useProjectStore()
const { projects } = storeToRefs(projectStore)

onMounted(async () => {
  await projectStore.fetchAllProjects()
})

const goToProjectDetails = (projectId: string) => {
  router.push(`/projects/${projectId}`)
}

const deleteProject = async (id: string) => {
  try {
    await projectStore.deleteProject(id)
    await projectStore.fetchProjects()
  } catch (err) {
    console.error('Failed to delete project', err)
  }
}
</script>
