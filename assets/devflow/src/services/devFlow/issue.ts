import request from "../request"

export const getIssueById = (id: string) => request.get(`/issues/${id}`)
export const getProjectIssues = (projectId: string) => request.get(`/issues/project/${projectId}`)


export const addIssue = (projectId: string, data:{
  title: string
  description: string
  status: string
  assigneeId: string
  labels: number[]
}) => request.post(`/issues/${projectId}`, data).then(res => res.data)

export const updateIssue = (id: string, data:{
    title: string,
    description: string,
    status: string
    labels: number[]
  }) => request.put(`/issues/${id}`,data).then(res => res.data)
  
export const deleteIssue = (id: string) => request.delete(`/issues/${id}`) 
  
  
