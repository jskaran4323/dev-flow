<template>
  <BaseLayout>
    <div class="container py-5 text-white">
      <!-- Header -->
      <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Issues</h2>
        <button class="btn btn-success" @click="goToCreateIssue">+ New Issue</button>
      </div>

      <!-- Empty State -->
      <div v-if="issues.length === 0" class="alert alert-secondary text-muted text-center">
        No issues found in this project. Start by creating a new one.
      </div>

      <!-- Issues Grid -->
      <div class="row g-4" v-else>
        <div class="col-md-6" v-for="issue in issues" :key="issue.id">
          <div class="card bg-secondary text-white shadow-sm h-100">
            <div class="card-body">
              <!-- Title + Description -->
              <h5 class="card-title fw-bold">
                <router-link :to="`/issues/${issue.id}`" class="text-white text-decoration-none">
                  {{ issue.title }}
                </router-link>
              </h5>
              <p class="card-text small text-light">
                {{ issue.description.length > 100 ? issue.description.slice(0, 100) + '...' : issue.description }}
              </p>

              <!-- Assignee -->
              <p class="mb-2">
                <strong>👤 Assignee:</strong>
                {{ issue.assignee.fullName }} <span class="text-muted">({{ issue.assignee.username }})</span>
              </p>

              <!-- Labels -->
              <div class="mb-2">
                <strong>🏷️ Labels:</strong>
                <span
                  v-for="label in issue.labels"
                  :key="label.id"
                  class="badge bg-info text-dark me-1"
                >
                 {{ label.type }}
                </span>
              </div>

              <!-- Timestamps -->
              <div class="text-muted small">
                <div>🕒 Created: {{ formatDate(issue.createdAt) }}</div>
                <div>🔄 Updated: {{ formatDate(issue.updatedAt) }}</div>
              </div>
            </div>

            <!-- Footer Actions -->
            <div class="card-footer bg-transparent d-flex justify-content-between border-top border-light">
              <router-link :to="`/issues/${issue.id}/edit`" class="btn btn-sm btn-outline-light">
                ✏️ Edit
              </router-link>
              <button class="btn btn-sm btn-outline-danger" @click="deleteIssue(issue.id)">
                🗑️ Delete
              </button>
            </div>
          </div>
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
