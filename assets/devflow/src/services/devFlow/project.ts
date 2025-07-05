import request from "../request"

export const getProjects = () => request.get('/projects')

export const getProjectById = (id: string) =>
  request.get(`/projects/${id}`).then(res => res.data)

export const addProject = (data: {
  name: string
  description: string
  status: number
}) => request.post('/project/register', data).then(res => res.data)

export const updateProject = (id: string, data: {
  name: string
  description: string
  status: number
}) => request.put(`/projects/${id}`, data).then(res => res.data)

export const deleteProject = (id: string) => request.delete(`/project/delete/${id}`)
