import api from '../api'

export function getComments(issueId: string) {
  return api.get(`/issues/${issueId}/comments`).then(res => res.data)
}

export function addComment(issueId: string, payload: { content: string }) {
  return api.post(`/issues/${issueId}/comments`, payload).then(res => res.data)
}
