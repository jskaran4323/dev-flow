import request from '../request.ts'

export function getProjectLabels(projectId: string) {
  return request.get(`/${projectId}/labels`).then(res => res.data)
}
export const createLabel = (projectId: string, label: {type: string }) =>{
    return request.post(`/${projectId}/labels`, label).then(res => res.data)
  }
  