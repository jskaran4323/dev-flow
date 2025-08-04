import request from "../request";

export function sendJoinRequest(projectId: string){  
    request.post(`/projects/${projectId}/join-request`)
}

export async function fetchJoinRequests(projectId: string) {
    return request.get(`/projects/${projectId}/join-requests`)
  }
  
  export async function approveJoinRequest(projectId: string, userId: string) {
    return request.post(`/projects/${projectId}/approve-request`, { userId })
  }