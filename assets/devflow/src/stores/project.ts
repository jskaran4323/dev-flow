// src/stores/project.ts
import { defineStore } from 'pinia'
import {
  getProjects,
  getProjectById,
  addProject,
  updateProject
} from '../services/devFlow/project'

export interface Project {
  id: string
  name: string
  description: string
  status: 'ACTIVE' | 'IN_PROGRESS' | 'COMPLETED'
  createdAt: string
  updatedAt: string
}

export const useProjectStore = defineStore('project', {
  state: () => ({
    projects: [] as Project[],
    selectedProject: null as Project | null,
    loading: false,
    error: '' as string | null
  }),

  getters: {
    getProjectById: (state) => (id: string) => {
      return state.projects.find(p => p.id === id) || null
    }
  },

  actions: {
    async fetchProjects() {
      this.loading = true
      this.error = null
      try {
        const data = await getProjects()
       
        
        this.projects = data
      } catch (err: any) {
        this.error = err.response?.data?.message || 'Failed to load projects'
      } finally {
        this.loading = false
      }
    },

    async fetchProject(id: string) {
      this.loading = true
      this.error = null
      try {
        const project = await getProjectById(id)
        this.selectedProject = project

        const index = this.projects.findIndex(p => p.id === id)
        if (index === -1) {
          this.projects.push(project)
        } else {
          this.projects[index] = project
        }

        return project
      } catch (err: any) {
        this.error = err.response?.data?.message || 'Failed to load project'
      } finally {
        this.loading = false
      }
    },

    async createProject(data: {
      name: string
      description: string
      status: number
    }) {
      this.error = null
      try {
        const newProject = await addProject(data)
        console.log(newProject);
        
        this.projects.unshift(newProject)
        return newProject
      } catch (err: any) {
        this.error = err.response?.data?.message || 'Failed to create project'
        throw err
      }
    },

    async updateProject(id: string, data: {
      name: string
      description: string
      status: number
    }) {
      this.error = null
      try {
        const updatedProject = await updateProject(id, data)
        const index = this.projects.findIndex(p => p.id === id)
        if (index !== -1) {
          this.projects[index] = updatedProject
        }
        return updatedProject
      } catch (err: any) {
        this.error = err.response?.data?.message || 'Failed to update project'
        throw err
      }
    }
  }
})
