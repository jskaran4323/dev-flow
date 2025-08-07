<template>
  <BaseLayout>
    <div class="container py-5 text-white">
      <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>🐛 Issues</h2>
        <button class="btn btn-success" @click="goToCreateIssue">+ New Issue</button>
      </div>

      <!-- Empty state -->
      <div v-if="issues.length === 0" class="alert alert-secondary text-muted">
        No issues found. Start by creating a new one.
      </div>

      <!-- Issues List -->
      <div class="row g-4">
        <div class="col-md-6" v-for="issue in issues" :key="issue.id">
          <router-link :to="`/issues/${issue.id}`" class="text-decoration-none">
          <div class="card bg-secondary text-white shadow-sm h-100">
            <div class="card-body">
              <h5 class="card-title fw-bold">{{ issue.title }}</h5>
              <p class="card-text small">{{ issue.description }}</p>

          

              <p class="mb-1">
                <strong>Assignee:</strong>
                {{ issue.assignee.fullName }} ({{ issue.assignee.username }})
              </p>

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
        </router-link>
        </div>
      </div>
    </div>
  </BaseLayout>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useIssueStore } from '../../stores/issue'
import BaseLayout from '../../layouts/BaseLayout.vue'

// Stores & Router
const route = useRoute()
const router = useRouter()
const projectId = route.params.projectId as string
const issueStore = useIssueStore()

// Reactive computed issues
const issues = computed(() => issueStore.issues)

// Load on mount
onMounted(async () => {
  await issueStore.fetchIssues(projectId)

  
})

// Go to Create Issue
function goToCreateIssue() {
  router.push(`/projects/${projectId}/issues/new`)
}

// Delete issue
const deleteIssue = async (id: string) => {
  await issueStore.deleteIssue(id)
}

// Date formatter
const formatDate = (dateString: string) => {
  return new Date(dateString).toLocaleString()
}
</script>
