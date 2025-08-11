<template>
  <BaseLayout>
    <!-- Hero -->
    <section class="py-10">
      <div class="rounded-2xl border border-border bg-card text-card-foreground shadow-soft px-6 py-10 text-center">
        <h1 class="text-4xl font-semibold tracking-tight">🚀 DevFlow</h1>
        <p class="mt-3 text-lg text-muted-foreground">
          Track issues. Collaborate with your team. Ship faster.
        </p>

        <div class="mt-6 flex flex-col sm:flex-row items-center justify-center gap-3">
          <Button as="router-link" :to="'/register'" variant="primary" size="lg">
            Get Started
          </Button>

          <Button
            v-if="!authStore.isAuthenticated"
            as="router-link"
            :to="'/login'"
            variant="secondary"
            size="lg"
          >
            Login
          </Button>

          <Button
            v-else
            as="router-link"
            :to="'/dashboard'"
            variant="secondary"
            size="lg"
          >
            Go to Dashboard
          </Button>
        </div>
      </div>
    </section>

    <!-- Features -->
    <section class="py-10">
      <h2 class="text-center text-2xl font-semibold tracking-tight">Why DevFlow?</h2>
      <div class="mt-6 grid grid-cols-1 md:grid-cols-3 gap-6">
        <Card>
          <h3 class="text-lg font-semibold">🧠 Smart Issues</h3>
          <p class="mt-2 text-sm text-muted-foreground">
            Label and prioritize tasks using AI suggestions so your team always knows what’s next.
          </p>
        </Card>

        <Card>
          <h3 class="text-lg font-semibold">👥 Team Collaboration</h3>
          <p class="mt-2 text-sm text-muted-foreground">
            Assign issues, leave comments, and track progress across your entire workflow.
          </p>
        </Card>

        <Card>
          <h3 class="text-lg font-semibold">📊 Kanban Board</h3>
          <p class="mt-2 text-sm text-muted-foreground">
            Visualize work status with an interactive board—stay organized and efficient.
          </p>
        </Card>
      </div>
    </section>

    <!-- Footer -->
    <section class="py-8">
      <p class="text-center text-sm text-muted-foreground">
        © 2025 DevFlow. Built for devs by devs.
      </p>
    </section>
  </BaseLayout>
</template>

<script setup lang="ts">
import BaseLayout from '../layouts/BaseLayout.vue'
import { useAuthStore } from '../stores/auth'
import Button from '../components/ui/Button.vue'
import Card from '../components/ui/Card.vue'

const authStore = useAuthStore()
</script>
