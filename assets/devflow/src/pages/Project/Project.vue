<template>
  <BaseLayout>
    <!-- Header -->
    <section class="py-6 flex flex-col gap-3 md:flex-row md:items-center md:justify-between">
      <h2 class="text-2xl font-semibold tracking-tight">📦 My Projects</h2>
      <Button as="router-link" :to="'/projects/new'" variant="primary" size="md">
        + New Project
      </Button>
    </section>

    <!-- Empty state -->
    <section v-if="projects.length === 0" class="py-6">
      <Card>
        <h3 class="text-lg font-medium">You don’t have any projects yet</h3>
        <p class="text-sm text-muted-foreground mt-1">
          Click “New Project” to create one.
        </p>
      </Card>
    </section>

    <!-- Project grid -->
    <section v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <Card
        v-for="project in projects"
        :key="project.id"
        clickable
        class="h-full flex flex-col"
        @click="goToProjectDetails(project.id)"
      >
        <!-- Body -->
        <div class="flex-1">
          <h3 class="text-lg font-semibold">{{ project.name }}</h3>
          <p class="mt-2 text-sm text-muted-foreground line-clamp-3">
            {{ project.description }}
          </p>
          <Badge variant="secondary" rounded class="mt-3">
            {{ project.status }}
          </Badge>
        </div>

        <!-- Footer actions -->
        <template #footer>
          <div class="pt-4 flex items-center justify-between" @click.stop>
            <div class="flex items-center gap-2">
              <Button
                as="router-link"
                :to="`/projects/${project.id}/edit`"
                variant="secondary"
                size="sm"
              >
                Edit
              </Button>
              <Button
                as="router-link"
                :to="`/projects/${project.id}/kanban`"
                variant="secondary"
                size="sm"
              >
                📊 Kanban
              </Button>
            </div>

            <Button
              variant="destructive"
              size="sm"
              @click="deleteProject(project.id)"
            >
              Delete
            </Button>
          </div>
        </template>
      </Card>
    </section>
  </BaseLayout>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { storeToRefs } from 'pinia'
import BaseLayout from '../../layouts/BaseLayout.vue'
import { useProjectStore } from '../../stores/project'
import Button from '../../components/ui/Button.vue'
import Card from '../../components/ui/Card.vue'
import Badge from '../../components/ui/Badge.vue'

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
