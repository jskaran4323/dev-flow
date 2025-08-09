<template>
  <div class="card bg-dark text-white p-4 shadow-sm">
    <h4 class="mb-3">👥 Join Requests</h4>

    <!-- No requests -->
    <div v-if="requests.length === 0" class="text-muted">No join requests.</div>

    <!-- Request list -->
    <ul class="list-group">
      <li
        v-for="req in requests"
        :key="req.userId"
        class="list-group-item d-flex justify-content-between align-items-center bg-secondary text-white"
      >
        <div>
          <strong>{{ req.fullName || req.username }}</strong><br />
          <small>Status: {{ req.status }} | Requested: {{ formatDate(req.createdAt) }}</small>
        </div>
        <button
          class="btn btn-success btn-sm"
          @click="approve(req.userId)"
          :disabled="loadingUserId === req.userId"
        >
          ✅ Approve
        </button>
      </li>
    </ul>

    <!-- Error -->
    <p v-if="errorMessage" class="text-danger mt-3">{{ errorMessage }}</p>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, watch } from 'vue'
import { fetchJoinRequests, approveJoinRequest } from '../services/devFlow/joinRequest'

interface Props {
  projectId: string
}
const props = defineProps<Props>()

const requests = ref<any[]>([])
const errorMessage = ref('')
const loadingUserId = ref<string | null>(null)

// Fetch join requests
const fetchRequests = async () => {
  try {
    const res = await fetchJoinRequests(props.projectId)
    requests.value = res
  } catch (err: any) {
    errorMessage.value = err.response?.data?.message || '⚠️ Failed to load join requests'
  }
}

// Approve a request
const approve = async (userId: string) => {
  loadingUserId.value = userId
  try {
    await approveJoinRequest(props.projectId, userId)
    // Remove from UI
    requests.value = requests.value.filter(r => r.userId !== userId)
  } catch (err: any) {
    errorMessage.value = err.response?.data?.message || '❌ Approval failed'
  } finally {
    loadingUserId.value = null
  }
}

// Format ISO date to readable
const formatDate = (iso: string) => new Date(iso).toLocaleString()

onMounted(fetchRequests)
watch(() => props.projectId, fetchRequests)
</script>
