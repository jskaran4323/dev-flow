<template>
  <BaseLayout>
    <!-- Hero Section -->
    <section class="py-5 text-center text-white bg-dark">
      <div class="container">
        <h1 class="display-4 fw-bold mb-3">🚀 DevFlow</h1>
        <p class="lead">Track issues. Collaborate with your team. Ship faster.</p>

        <!-- Only show buttons if user is NOT logged in -->
      
          <router-link to="/register" class="btn btn-success btn-lg me-3">Get Started</router-link>
          <div class="mt-4" v-if="!authStore.isAuthenticated">
          <router-link to="/login" class="btn btn-outline-light btn-lg">Login</router-link>
        </div>
      </div>
    </section>

    <!-- Features Section -->
    <section class="py-5 bg-secondary text-white">
      <div class="container">
        <h2 class="mb-4 text-center">Why DevFlow?</h2>
        <div class="row text-start">
          <div class="col-md-4">
            <div class="card bg-dark text-white shadow-sm mb-4">
              <div class="card-body">
                <h5 class="card-title">🧠 Smart Issues</h5>
                <p class="card-text">Label and prioritize tasks using AI suggestions so your team always knows what’s next.</p>
              </div>
            </div>
          </div>
          <div class="col-md-4">
            <div class="card bg-dark text-white shadow-sm mb-4">
              <div class="card-body">
                <h5 class="card-title">👥 Team Collaboration</h5>
                <p class="card-text">Assign issues, leave comments, and track progress across your entire workflow.</p>
              </div>
            </div>
          </div>
          <div class="col-md-4">
            <div class="card bg-dark text-white shadow-sm mb-4">
              <div class="card-body">
                <h5 class="card-title">📊 Kanban Board</h5>
                <p class="card-text">Visualize work status with an interactive board—stay organized and efficient.</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Footer -->
    <footer class="bg-dark text-white py-4 border-top border-secondary mt-5">
      <div class="container text-center">
        <p class="mb-0">&copy; 2025 DevFlow. Built for devs by devs.</p>
      </div>
    </footer>
  </BaseLayout>
</template>

<script setup lang="ts">
import BaseLayout from '../layouts/BaseLayout.vue'
import { useAuthStore } from '../stores/auth'

const authStore = useAuthStore()
</script>
