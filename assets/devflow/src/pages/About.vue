<template>
  <BaseLayout>
    <!-- Hero -->
    <section class="py-8 text-center">
      <h1 class="text-3xl font-bold tracking-tight">About DevFlow</h1>
      <p class="mt-2 text-muted-foreground">
        DevFlow is a minimal, developer‑friendly issue tracker with projects, labels, comments, and a Kanban board.
      </p>
    </section>

    <!-- Content -->
    <section class="grid grid-cols-1 md:grid-cols-3 gap-6">
      <Card class="md:col-span-2">
        <h2 class="text-lg font-semibold">What is DevFlow?</h2>
        <p class="mt-2 text-sm text-muted-foreground">
          DevFlow helps teams track issues, collaborate, and ship faster. It pairs a Spring Boot backend with a Vue.js frontend.
          The UI is purposefully minimal, built with Tailwind and a small set of primitives (Button, Card, Badge, etc.).
        </p>

        <ul class="mt-4 list-disc pl-5 space-y-2 text-sm">
          <li>Create and manage issues with labels and assignees</li>
          <li>Discuss with inline comments on every issue</li>
          <li>Organize work on a simple Kanban board</li>
          <li>Public projects + join requests for collaboration</li>
        </ul>
      </Card>

      <Card>
        <h3 class="text-lg font-semibold">Tech Stack</h3>
        <ul class="mt-3 text-sm space-y-1">
          <li>⚙️ Backend: Spring Boot</li>
          <li>🖥 Frontend: Vue 3 + Vite + Pinia</li>
          <li>🎨 UI: Tailwind CSS + custom primitives</li>
        </ul>
      </Card>
    </section>
  </BaseLayout>
</template>

<script setup lang="ts">
import BaseLayout from '../layouts/BaseLayout.vue'
import Card from '../components/ui/Card.vue'
</script>
