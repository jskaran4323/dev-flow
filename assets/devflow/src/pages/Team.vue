<template>
  <BaseLayout>
    <section class="py-10 max-w-4xl mx-auto">
      <h1 class="text-3xl font-bold mb-6 text-center">👥 Meet the Team</h1>

      <p class="text-center text-muted-foreground mb-10">
        DevFlow was built by a small but mighty team passionate about helping developers track issues,
        collaborate, and ship faster.
      </p>

      <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
        <!-- Member 1 -->
        <div class="p-6 rounded-lg border border-border bg-card/50 shadow hover:shadow-lg transition">
          <h2 class="text-xl font-semibold mb-2">Jaskaran Singh</h2>
          <p class="text-sm text-muted-foreground mb-4">Software Engineer</p>
          <p class="text-sm">
            Focuses on backend architecture, APIs, and system design.
            Loves building scalable applications and learning new technologies.
          </p>
        </div>

        <!-- Member 2 -->
        <div class="p-6 rounded-lg border border-border bg-card/50 shadow hover:shadow-lg transition">
          <h2 class="text-xl font-semibold mb-2">Yash Gajera</h2>
          <p class="text-sm text-muted-foreground mb-4">Software Engineer</p>
          <p class="text-sm">
            Specializes in crafting smooth user experiences and clean UI designs.
            Handles the frontend integration with DevFlow’s APIs.
          </p>
        </div>
      </div>
    </section>
  </BaseLayout>
</template>

<script setup lang="ts">
import BaseLayout from '../layouts/BaseLayout.vue'
</script>
