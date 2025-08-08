<!-- src/pages/AdminDashboard.vue -->
<template>
    <BaseLayout>
      <div class="container py-5 text-white">
        <h2 class="mb-4">🛠 Admin Dashboard</h2>
  
        <div v-if="loading">Loading users...</div>
        <div v-if="error" class="text-danger">{{ error }}</div>
  
        <table v-if="users && users.length" class="table table-dark table-bordered">
          <thead>
            <tr>
              <th>#</th>
              <th>Full Name</th>
              <th>Username</th>
              <th>Email</th>
              <th>User Type</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(user, index) in users" :key="user.userId">
              <td>{{ index + 1 }}</td>
              <td>{{ user.fullName }}</td>
              <td>{{ user.username }}</td>
              <td>{{ user.email }}</td>
              <td>{{ user.userType }}</td>
              <td>
    <button class="btn btn-sm btn-warning me-2" @click="editUser(user)">✏️ Edit</button>
    <button class="btn btn-sm btn-danger" @click="deleteUser(user.userId)">🗑 Delete</button>
  </td>
            </tr>
          </tbody>
        </table>
      </div>
    </BaseLayout>
  </template>
  
  <script setup lang="ts">
  import { onMounted } from 'vue'
  import { useAdminStore } from '../stores/admin'
  import BaseLayout from '../layouts/BaseLayout.vue'
  
  const adminStore = useAdminStore()
  
  
  const { users, loading, error } = adminStore
  
  onMounted(async() => {
   await adminStore.fetchUsers()
  })
  function deleteUser(userId: string) {
  if (confirm("Are you sure you want to delete this user?")) {
    adminStore.deleteUser(userId)
  }
}

function editUser(user: any) {
  // You can open a modal or redirect to an edit form
  const newName = prompt("Enter new full name:", user.fullName)
  if (newName) {
    adminStore.updateUser(user.userId, { fullName: newName })
  }
}
  </script>
  