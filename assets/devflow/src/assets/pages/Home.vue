<template>
    <div class="min-h-screen bg-surface text-text px-6 py-10">
      <div class="max-w-5xl mx-auto">
        <h1 class="text-3xl font-bold text-primary mb-4">👋 Welcome to DevFlow</h1>
        <p class="text-lg text-gray-400 mb-8">
          Track issues, collaborate with your team, and get work done — all in one place.
        </p>
  
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div class="bg-card p-6 rounded-2xl shadow hover:shadow-lg transition">
            <h2 class="text-xl font-semibold mb-2">🗂️ Your Projects</h2>
            <p class="text-sm text-gray-400">View and manage all your assigned projects.</p>
          </div>
  
          <div class="bg-card p-6 rounded-2xl shadow hover:shadow-lg transition">
            <h2 class="text-xl font-semibold mb-2">🐛 Open Issues</h2>
            <p class="text-sm text-gray-400">Keep track of bugs and features in progress.</p>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  // later: fetch user data, projects, issues
  </script>
  