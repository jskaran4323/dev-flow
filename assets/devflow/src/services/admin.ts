 import request from "./request"
export function getAllUsers() {
  return request.get('/admin/users')
}

export function deleteUserById(userId: string) {
  return request.delete(`/admin/users/${userId}`)
}

export function updateUserById(userId: string, data: any) {
  return request.put(`/admin/users/${userId}`, data)
}
