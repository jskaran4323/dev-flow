<template>
  <BaseLayout>
    <div class="container py-5 text-white">
      <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>🐛 Issues</h2>
        <button class="btn btn-success" @click="goToCreateIssue">+ New Issue</button>
      </div>

      <div v-if="issues.length === 0" class="alert alert-secondary text-muted">
        No issues found. Start by creating a new one.
      </div>

      <div class="row g-4">
        <div class="col-md-6" v-for="issue in issues" :key="issue.id">
          <div class="card bg-secondary text-white shadow-sm h-100">
            <div class="card-body">
              <h5 class="card-title fw-bold">{{ issue.title }}</h5>
              <p class="card-text small">{{ issue.description }}</p>
              <p class="mb-2">
                <span class="badge bg-light text-dark me-2">{{ issue.status }}</span>
                <small class="text-muted">Project: {{ issue.project.name }}</small>
              </p>

              <p class="mb-1"><strong>Assignee:</strong> {{ issue.assignee.username }} ({{ issue.assignee.email }})</p>

              <div class="mb-2">
                <strong>Labels:</strong>
                <span
                  v-for="label in issue.labels"
                  :key="label.id"
                  class="badge bg-info text-dark me-1"
                >
                  {{ label.name }} - {{ label.type }}
                </span>
              </div>

              <small class="text-muted">Created: {{ formatDate(issue.createdAt) }}</small><br />
              <small class="text-muted">Updated: {{ formatDate(issue.updatedAt) }}</small>
            </div>

            <div class="card-footer bg-transparent d-flex justify-content-between border-top border-light">
              <router-link :to="`/issues/${issue.id}/edit`" class="btn btn-sm btn-outline-light">Edit</router-link>
              <button class="btn btn-sm btn-outline-danger" @click="deleteIssue(issue.id)">Delete</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </BaseLayout>
</template>
<script setup lang="ts">
import { onMounted } from 'vue'
import { useIssueStore } from '../../stores/issue'
import { useRoute, useRouter } from 'vue-router'
import BaseLayout from '../../layouts/BaseLayout.vue'


const route = useRoute()
const issueStore = useIssueStore()
const issues = issueStore.issues
const projectId = route.params.projectId as string
const router = useRouter()
onMounted(() => {
  issueStore.fetchIssues(projectId)
})


function goToCreateIssue() {
  router.push(`/projects/${projectId}/issues/new`)
}  
const deleteIssue = (id: string) => {
  issueStore.deleteIssue(id)
}

const formatDate = (dateString: string) => {
  return new Date(dateString).toLocaleString()
}
</script>