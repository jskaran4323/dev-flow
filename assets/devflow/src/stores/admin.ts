import { defineStore } from 'pinia'
import { getAllUsers, deleteUserById, updateUserById } from '../services/admin'


export interface UserDto {
  userId: string
  fullName: string
  username: string
  email: string
  userType: string
  createdAt: string | null
}
export const useAdminStore = defineStore('admin', {
  state: () => ({
    users: [] as UserDto[],
    loading: false,
    error: null as string | null,
  }),

  actions: {
    async fetchUsers() {
      this.loading = true
      this.error = null
      try {
        this.users = await getAllUsers()
      } catch (err: any) {
        this.error = err.message || 'Failed to fetch users'
      } finally {
        this.loading = false
      }
    },

    async deleteUser(userId: string) {
      try {
        await deleteUserById(userId)
        this.users = this.users.filter(u => u.userId !== userId)
      } catch (err: any) {
        this.error = err.message || 'Failed to delete user'
      }
    },

    async updateUser(userId: string, updatedData: any) {
      try {
        const updated = await updateUserById(userId, updatedData)
        const idx = this.users.findIndex(u => u.userId === userId)
        if (idx !== -1) this.users[idx] = updated
      } catch (err: any) {
        this.error = err.message || 'Failed to update user'
      }
    }
  }
})
