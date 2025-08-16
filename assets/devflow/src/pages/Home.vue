<template>
  <BaseLayout content-class="flex flex-col min-h-screen">
    <!-- HERO (centered, no card) -->
    <section class="flex-1 flex items-center justify-center text-center">
      <div>
        <h1 class="text-5xl font-semibold tracking-tight">🚀 DevFlow</h1>

        <!-- typing line -->
        <p class="mt-3 text-lg text-muted-foreground inline-flex items-center">
          <span>{{ typedText }}</span>
          <span v-show="showCursor" class="ml-0.5 caret"></span>
        </p>

        <div class="mt-6 flex flex-col sm:flex-row items-center justify-center gap-3">
          <Button
            v-if="!authStore.isAuthenticated"
            as="router-link"
            :to="'/register'"
            variant="primary"
            size="lg"
          >
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

    <!-- BOTTOM: features + footer kept compact so no page scroll -->
    <section class="space-y-6 pb-12 mb-12">
      <div>
        <h2 class="text-center text-xl font-semibold tracking-tight">Why DevFlow?</h2>
        <div class="mt-4 grid grid-cols-1 md:grid-cols-3 gap-4">
          <Card class="p-4">
            <h3 class="text-base font-semibold">🧠 Smart Issues</h3>
            <p class="mt-1 text-sm text-muted-foreground">
              Label and prioritize tasks using AI suggestions so your team always knows what’s next.
            </p>
          </Card>

          <Card class="p-4">
            <h3 class="text-base font-semibold">👥 Team Collaboration</h3>
            <p class="mt-1 text-sm text-muted-foreground">
              Assign issues, leave comments, and track progress across your entire workflow.
            </p>
          </Card>

          <Card class="p-4">
            <h3 class="text-base font-semibold">📊 Kanban Board</h3>
            <p class="mt-1 text-sm text-muted-foreground">
              Visualize work status with an interactive board—stay organized and efficient.
            </p>
          </Card>
        </div>
      </div>

      <footer>
        <p class="text-center text-xs text-muted-foreground">
          © 2025 DevFlow. Built for devs by devs.
        </p>
      </footer>
    </section>
  </BaseLayout>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import BaseLayout from '../layouts/BaseLayout.vue'
import { useAuthStore } from '../stores/auth'
import Button from '../components/ui/Button.vue'
import Card from '../components/ui/Card.vue'

const authStore = useAuthStore()

// Typing effect for the subtitle
const fullText = 'Track issues. Collaborate with your team. Ship faster.'
const typedText = ref('')
const showCursor = ref(true)

let typeTimer: number | undefined
let blinkTimer: number | undefined

onMounted(() => {
  let i = 0
  typeTimer = window.setInterval(() => {
    if (i < fullText.length) {
      typedText.value += fullText.charAt(i)
      i++
    } else {
      window.clearInterval(typeTimer)
    }
  }, 45)

  // blinking cursor
  blinkTimer = window.setInterval(() => {
    showCursor.value = !showCursor.value
  }, 500)
})

onUnmounted(() => {
  if (typeTimer) window.clearInterval(typeTimer)
  if (blinkTimer) window.clearInterval(blinkTimer)
})
</script>

<style scoped>
.caret {
  width: 1px;
  height: 1.25em;
  background: currentColor;
  display: inline-block;
  transform: translateY(2px);
}
</style>
