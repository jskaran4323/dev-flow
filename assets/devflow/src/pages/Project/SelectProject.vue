<template>
  <BaseLayout>
    <!-- Header -->
    <section class="py-6 text-center">
      <h2 class="text-2xl font-semibold tracking-tight">📁 Select a Project to Continue</h2>
    </section>

    <!-- Card -->
    <Card class="max-w-3xl mx-auto">
      <!-- Empty State -->
      <div v-if="projectStore.projects.length === 0" class="text-center py-6">
        <p class="text-sm text-muted-foreground">No projects available.</p>
      </div>

      <!-- Project List -->
      <ul v-else class="divide-y divide-border">
        <li
          v-for="project in projectStore.projects"
          :key="project.id"
          class="flex items-center justify-between py-4"
        >
          <div>
            <h5 class="text-base font-semibold">{{ project.name }}</h5>
            <small class="text-xs text-muted-foreground">ID: {{ project.id }}</small>
          </div>

          <Button size="sm" variant="secondary" @click="selectProject(project.id)">
            Continue →
          </Button>
        </li>
      </ul>
    </Card>
  </BaseLayout>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useProjectStore } from '../../stores/project'
import BaseLayout from '../../layouts/BaseLayout.vue'
import Card from '../../components/ui/Card.vue'
import Button from '../../components/ui/Button.vue'

const router = useRouter()
const projectStore = useProjectStore()

onMounted(async () => {
  if (projectStore.projects.length === 0) {
    await projectStore.fetchProjects()
  }
})

const selectProject = (projectId: string) => {
  router.push({
    name: 'ProjectIssues',
    params: { projectId }
  })
}
</script>
