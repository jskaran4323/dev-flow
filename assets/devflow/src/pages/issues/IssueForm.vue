<template>
    <BaseLayout>
      <div class="container py-5 text-white">
        <h2 class="mb-4">📝 Create New Issue</h2>
  
        <form @submit.prevent="handleSubmit" class="card bg-secondary text-white p-4 shadow-sm" style="max-width: 600px;">
          <div class="mb-3">
            <label class="form-label">Title</label>
            <input v-model="issue.title" type="text" class="form-control" required />
          </div>
  
          <div class="mb-3">
            <label class="form-label">Description</label>
            <textarea v-model="issue.description" class="form-control" rows="4"></textarea>
          </div>
  
          <div class="mb-3">
            <label class="form-label">Status</label>
            <select v-model="issue.status" class="form-select">
              <option value="OPEN">Open</option>
              <option value="IN_PROGRESS">In Progress</option>
              <option value="CLOSED">Closed</option>
            </select>
          </div>
  
          <div class="mb-3">
            <label class="form-label">Assignee ID</label>
            <input v-model="issue.assigneeId" type="text" class="form-control" />
          </div>
  
          <div class="mb-3">
            <label class="form-label">Label IDs (comma-separated)</label>
            <input v-model="labelInput" type="text" class="form-control" />
          </div>
  
          <button type="submit" class="btn btn-success w-100">Create Issue</button>
  
          <p class="text-danger mt-2" v-if="errorMessage">{{ errorMessage }}</p>
        </form>
      </div>
    </BaseLayout>
  </template>
  
  <script setup lang="ts">
  import { reactive, ref } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
 import { useIssueStore } from '../../stores/issue'
import BaseLayout from '../../layouts/BaseLayout.vue'
  
  const issueStore = useIssueStore()
  const route = useRoute()
  const router = useRouter()
  const projectId = route.params.projectId as string
  
  const issue = reactive({
    title: '',
    description: '',
    status: 'OPEN',
    assigneeId: '',
    labelIds: [] as string[]
  })
  
  const labelInput = ref('')
  const errorMessage = ref('')
  
  const handleSubmit = async () => {
    try {
      issue.labelIds = labelInput.value.split(',').map(id => id.trim()).filter(Boolean)
      await issueStore.createIssue(projectId, issue)
      router.push(`/projects/${projectId}/issues`)
    } catch (err: any) {
      errorMessage.value = err.response?.data?.message || 'Failed to create issue'
    }
  }
  </script>
  