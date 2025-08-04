<template>
  <div class="card bg-dark text-white p-4 shadow-sm">
    <h4 class="mb-3">👥 Join Requests</h4>

    <div v-if="requests.length === 0">No join requests.</div>

    <ul class="list-group">
      <li
        v-for="req in requests"
        :key="req.userId"
        class="list-group-item d-flex justify-content-between align-items-center bg-secondary text-white"
      >
        <div>
          <strong>{{ req.fullName || req.username }}</strong>
          <br />
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

    <p v-if="errorMessage" class="text-danger mt-3">{{ errorMessage }}</p>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import { fetchJoinRequests, approveJoinRequest } from '../services/devFlow/joinRequest'

const route = useRoute()
const projectId = route.params.projectId as string

const requests = ref<any[]>([])
const errorMessage = ref('')
const loadingUserId = ref<string | null>(null)

const fetchRequests = async () => {
  try {
    requests.value = await fetchJoinRequests(projectId)
  } catch (err: any) {
    errorMessage.value = err.response?.data?.message || 'Failed to load join requests'
  }
}

const approve = async (userId: string) => {
  loadingUserId.value = userId
  try {
    await approveJoinRequest(projectId, userId)
    requests.value = requests.value.filter(r => r.userId !== userId)
  } catch (err: any) {
    errorMessage.value = err.response?.data?.message || 'Approval failed'
  } finally {
    loadingUserId.value = null
  }
}

const formatDate = (iso: string) => new Date(iso).toLocaleString()

onMounted(fetchRequests)
</script>
