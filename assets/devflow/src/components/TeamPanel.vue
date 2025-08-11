<template>
  <Card>
    <h2 class="text-lg font-semibold">👥 Project Team</h2>

    <!-- Loading -->
    <p v-if="loading" class="mt-2 text-sm text-muted-foreground">Loading team…</p>

    <!-- Error -->
    <p v-else-if="error" class="mt-2 text-sm text-destructive">{{ error }}</p>

    <!-- Empty -->
    <p v-else-if="members.length === 0" class="mt-2 text-sm text-muted-foreground">
      No team members.
    </p>

    <!-- List -->
    <ul v-else class="mt-4 divide-y divide-border">
      <li
        v-for="member in members"
        :key="member.id || member.userId"
        class="py-3 flex items-center justify-between"
      >
        <div class="min-w-0">
          <p class="font-medium text-foreground truncate">
            {{ member.fullName || member.username }}
          </p>
          <p v-if="member.username" class="text-xs text-muted-foreground mt-0.5">
            @{{ member.username }}
          </p>
        </div>

        <Badge variant="secondary" rounded>
          {{ member.userType }}
        </Badge>
      </li>
    </ul>
  </Card>
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router'
import { storeToRefs } from 'pinia'
import { useTeamStore } from '../stores/teams'

// UI primitives
import Card from './ui/Card.vue'
import Badge from './ui/Badge.vue'

const route = useRoute()
const projectId = route.params.projectId as string

const store = useTeamStore()
store.fetchTeam(projectId)

const { members, loading, error } = storeToRefs(store)
</script>
