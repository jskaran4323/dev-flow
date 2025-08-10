<template>
    <BaseLayout>
      <div class="container py-5 text-white">
        <h2 class="mb-4">📊 Project Issues Board</h2>
        <div class="row">
          <div
            v-for="status in statuses"
            :key="status.key"
            class="col-md-4"
          >
            <div class="card bg-dark border-light h-100">
              <div class="card-header bg-secondary text-white">
                {{ status.label }}
                <span class="badge bg-info ms-2">
                  {{ filteredIssues(status.key).length }}
                </span>
              </div>
              <div class="card-body overflow-auto" style="max-height: 60vh">
                <div
                  v-for="issue in filteredIssues(status.key)"
                  :key="issue.id"
                  class="card mb-3 p-3 bg-light text-dark shadow-sm"
                  style="cursor: pointer;"
                  @click="navigateToIssue(issue.id)"
                >
                  <h5 class="mb-2 fw-bold">{{ issue.title }}</h5>
                  <p class="mb-2 text-muted small">{{ issue.description }}</p>
  
                  <!-- Labels -->
                  <div class="mb-2" v-if="issue.labels && issue.labels.length > 0">
                    <span
                      v-for="label in issue.labels"
                      :key="label.id || label"
                      class="badge me-1"
                      :class="getLabelColor(getLabelType(label))"
                    >
                      {{ getLabelName(getLabelType(label)) }}
                    </span>
                  </div>
  
                  <div class="d-flex justify-content-between align-items-center mt-2">
                    <div class="text-muted small">
                      <i class="fas fa-user me-1"></i>
                      {{ issue.assignee?.fullName || 'Unassigned' }}
                    </div>
                    <div class="text-muted small">
                      {{ formatDate(issue.createdAt) }}
                    </div>
                  </div>
                </div>
  
                <!-- Empty state -->
                <div v-if="filteredIssues(status.key).length === 0" class="text-center text-muted py-4">
                  <i class="fas fa-inbox fa-2x mb-2"></i>
                  <p>No issues in {{ status.label.toLowerCase() }}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </BaseLayout>
  </template>
  
  <script setup lang="ts">
  import { onMounted, computed } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import { useIssueStore } from '../stores/issue'
  import BaseLayout from '../layouts/BaseLayout.vue'
  
  const route = useRoute()
  const router = useRouter()
  const projectId = route.params.projectId as string
  const issueStore = useIssueStore()
  
  const issues = computed(() => issueStore.issues)
  
  onMounted(() => {
    issueStore.fetchIssues(projectId)
  })
  
  const statuses = [
    { key: 'OPEN', label: '🟢 Open' },
    { key: 'IN_PROGRESS', label: '🟡 In Progress' },
    { key: 'CLOSED', label: '🔴 Closed' }
  ]
  
  const filteredIssues = (status: string) => {
    return issues.value.filter((i) => {
      if (typeof i.status === 'number') {
        return getStatusString(i.status) === status
      }
      return i.status === status
    })
  }
  
  const getStatusString = (statusCode: number) => {
    const statusMap: Record<number, string> = {
      0: 'OPEN',
      1: 'IN_PROGRESS',
      2: 'CLOSED'
    }
    return statusMap[statusCode] || 'UNKNOWN'
  }
  
  const labelTypeMap: Record<number, string> = {
    0: 'BUG',
    1: 'FEATURE',
    2: 'ENHANCEMENT',
    3: 'DOCUMENTATION',
    4: 'URGENT',
    5: 'DESIGN',
    6: 'TEST',
    7: 'PERFORMANCE',
    8: 'SECURITY',
    9: 'BACKEND',
    10: 'FRONTEND'
  }
  
  const getLabelType = (label: any) => {
    return typeof label === 'object' && label.type !== undefined ? label.type : label
  }
  
  const getLabelName = (labelType: number) => {
    return labelTypeMap[labelType] || `Unknown (${labelType})`
  }
  
  const getLabelColor = (labelType: number) => {
    const colorMap: Record<number, string> = {
      0: 'bg-danger',
      1: 'bg-success',
      2: 'bg-info',
      3: 'bg-secondary',
      4: 'bg-warning',
      5: 'bg-primary',
      6: 'bg-dark',
      7: 'bg-warning',
      8: 'bg-danger',
      9: 'bg-success',
      10: 'bg-info'
    }
    return colorMap[labelType] || 'bg-secondary'
  }
  
  const formatDate = (dateString: string) => {
    if (!dateString) return ''
    return new Date(dateString).toLocaleDateString()
  }
  
  const navigateToIssue = (issueId: string, projectId: string) => {
  router.push({ 
    name: 'IssueDetails', 
    params: { id: issueId },
    query: { projectId } 
  })
}
  </script>
  