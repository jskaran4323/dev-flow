<template>
  <Card>
    <h4 class="text-lg font-semibold">👥 Join Requests</h4>

    <!-- Empty -->
    <p v-if="requests.length === 0" class="mt-2 text-sm text-muted-foreground">
      No join requests.
    </p>

    <!-- List -->
    <ul v-else class="mt-4 divide-y divide-border">
      <li
        v-for="req in requests"
        :key="req.userId"
        class="flex items-center justify-between py-3"
      >
        <div class="min-w-0">
          <p class="font-medium text-foreground truncate">
            {{ req.fullName || req.username }}
          </p>
          <p class="text-xs text-muted-foreground mt-0.5">
            Status: {{ req.status }} • Requested: {{ formatDate(req.createdAt) }}
          </p>
        </div>

        <Button
          variant="primary"
          size="sm"
          class="shrink-0"
          @click="approve(req.userId)"
          :disabled="loadingUserId === req.userId"
        >
          <span v-if="loadingUserId === req.userId" class="inline-flex items-center gap-2">
            <Spinner size="sm" />
            Approving…
          </span>
          <span v-else>✅ Approve</span>
        </Button>
      </li>
    </ul>

    <!-- Error -->
    <p v-if="errorMessage" class="mt-3 text-sm text-destructive">
      {{ errorMessage }}
    </p>
  </Card>
</template>

<script setup lang="ts">
import { onMounted, ref, watch } from 'vue'
import { fetchJoinRequests, approveJoinRequest } from '../services/devFlow/joinRequest'

// UI primitives
import Card from './ui/Card.vue'
import Button from './ui/Button.vue'
import Spinner from './ui/Spinner.vue'

interface Props {
  projectId: string
}
const props = defineProps<Props>()

const requests = ref<any[]>([])
const errorMessage = ref('')
const loadingUserId = ref<string | null>(null)

const fetchRequests = async () => {
  try {
    const res = await fetchJoinRequests(props.projectId)
    requests.value = res
  } catch (err: any) {
    errorMessage.value = err.response?.data?.message || '⚠️ Failed to load join requests'
  }
}

const approve = async (userId: string) => {
  loadingUserId.value = userId
  try {
    await approveJoinRequest(props.projectId, userId)
    requests.value = requests.value.filter((r: any) => r.userId !== userId)
  } catch (err: any) {
    errorMessage.value = err.response?.data?.message || '❌ Approval failed'
  } finally {
    loadingUserId.value = null
  }
}

const formatDate = (iso: string) => new Date(iso).toLocaleString()

onMounted(fetchRequests)
watch(() => props.projectId, fetchRequests)
</script>
