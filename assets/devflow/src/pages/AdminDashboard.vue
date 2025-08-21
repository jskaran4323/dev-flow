<!-- src/pages/AdminDashboard.vue -->
<template>
  <BaseLayout>
    <!-- Header -->
    <section class="py-6">
      <h2 class="text-2xl font-semibold tracking-tight">🛠 Admin Dashboard</h2>
      <p class="text-sm text-muted-foreground mt-1">Manage all users in the workspace</p>
    </section>

    <!-- Loading -->
    <section v-if="loading" class="py-6 flex items-center gap-3">
      <Spinner />
      <span class="text-sm text-muted-foreground">Loading users…</span>
    </section>

    <!-- Error -->
    <section v-else-if="error" class="py-6">
      <Card>
        <p class="text-sm text-destructive">{{ error }}</p>
      </Card>
    </section>

    <!-- Users table -->
    <section v-else>
      <Card>
        <div class="overflow-x-auto">
          <table class="min-w-full text-sm">
            <thead>
              <tr class="text-left text-muted-foreground">
                <th class="py-2 pr-4">#</th>
                <th class="py-2 pr-4">Full Name</th>
                <th class="py-2 pr-4">Username</th>
                <th class="py-2 pr-4">Email</th>
                <th class="py-2 pr-4">User Type</th>
                <th class="py-2">Actions</th>
              </tr>
            </thead>
            <tbody class="divide-y divide-border">
              <tr v-for="(user, index) in users" :key="user.userId">
                <td class="py-2 pr-4">{{ index + 1 }}</td>
                <td class="py-2 pr-4">{{ user.fullName }}</td>
                <td class="py-2 pr-4 text-muted-foreground">{{ user.username }}</td>
                <td class="py-2 pr-4 text-muted-foreground">{{ user.email }}</td>
                <td class="py-2 pr-4">{{ user.userType }}</td>
                <td class="py-2">
                  <div class="flex items-center gap-2">
                    <Button size="sm" variant="secondary" @click="editUser(user)">✏️ Edit</Button>
                    <Button size="sm" variant="destructive" @click="deleteUser(user.userId)">🗑 Delete</Button>
                  </div>
                </td>
              </tr>
              <tr v-if="!users || users.length === 0">
                <td colspan="6" class="py-6 text-center text-muted-foreground">
                  No users found.
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </Card>
    </section>
  </BaseLayout>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { storeToRefs } from 'pinia'
import BaseLayout from '../layouts/BaseLayout.vue'
import { useAdminStore } from '../stores/admin'
import Card from '../components/ui/Card.vue'
import Button from '../components/ui/Button.vue'
import Spinner from '../components/ui/Spinner.vue'

const adminStore = useAdminStore()
const { users, loading, error } = storeToRefs(adminStore)

onMounted(async () => {
  await adminStore.fetchUsers()
})

function deleteUser(userId: string) {
  if (confirm('Are you sure you want to delete this user?')) {
    adminStore.deleteUser(userId)
  }
}

function editUser(user: any) {
  const newName = prompt('Enter new full name:', user.fullName)
 
  if (newName) {
    adminStore.updateUser(user.userId, { fullName: newName })
  }
}
</script>
