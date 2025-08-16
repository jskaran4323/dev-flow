<template>
  <BaseLayout>
    <section class="max-w-xl mx-auto">
      <Card>
        <h4 class="text-xl font-semibold">
          {{ isEditMode ? 'Edit Project' : 'New Project' }}
        </h4>

        <form @submit.prevent="handleSubmit" class="mt-6 space-y-4">
          <!-- Name -->
          <div>
            <label class="block text-sm font-medium mb-1">Name</label>
            <Input v-model="project.name" type="text" required />
          </div>

          <!-- Description -->
          <div>
            <label class="block text-sm font-medium mb-1">Description</label>
            <Textarea v-model="project.description" :rows="3" />
          </div>

          <!-- Status -->
          <div>
            <label class="block text-sm font-medium mb-1">Status</label>
            <Select v-model="project.status">
              <option :value="ProjectStatusType.COMPLETED">Completed</option>
              <option :value="ProjectStatusType.ACTIVE">Active</option>
              <option :value="ProjectStatusType.IN_PROGRESS">In Progress</option>
            </Select>
          </div>

          <!-- Error -->
          <p v-if="errorMessage" class="text-sm text-destructive">
            {{ errorMessage }}
          </p>

          <!-- Actions -->
          <div class="flex items-center gap-3 pt-2">
            <Button type="submit" variant="primary">
              {{ isEditMode ? 'Update' : 'Create' }}
            </Button>
            <Button as="router-link" :to="'/projects'" variant="secondary">
              Cancel
            </Button>
          </div>
        </form>
      </Card>
    </section>
  </BaseLayout>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import BaseLayout from '../../layouts/BaseLayout.vue'
import { ProjectStatusType } from '../../enums/ProjectStatusType'
import { useProjectStore } from '../../stores/project'

// UI primitives
import Card from '../../components/ui/Card.vue'
import Input from '../../components/ui/Input.vue'
import Textarea from '../../components/ui/Textarea.vue'
import Select from '../../components/ui/Select.vue'
import Button from '../../components/ui/Button.vue'

const route = useRoute()
const router = useRouter()
const isEditMode = route.params.id !== undefined

const projectStore = useProjectStore()
const project = reactive({
  name: '',
  description: '',
  status: ProjectStatusType.ACTIVE
})

const errorMessage = ref('')

const handleSubmit = async () => {
  try {
    await projectStore.createProject(project)
    router.push({name: "Project"})
  } catch (error: any) {
    errorMessage.value = error?.response?.data?.errorMessage || 'Failed to save project'
  }
}
</script>
