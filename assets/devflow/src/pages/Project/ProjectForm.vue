<template>
    <BaseLayout>
    <div class="card shadow-sm p-4">
      <h4 class="mb-4">{{ isEditMode ? 'Edit Project' : 'New Project' }}</h4>
      <form @submit.prevent="handleSubmit">
        <div class="mb-3">
          <label class="form-label">Name</label>
          <input v-model="project.name" type="text" class="form-control" required />
        </div>
        <div class="mb-3">
          <label class="form-label">Description</label>
          <textarea v-model="project.description" class="form-control" rows="3"></textarea>
        </div>
        <div class="mb-3">
          <label class="form-label">Status</label>
          <select v-model="project.status" class="form-select">
  <option :value="ProjectStatusType.COMPLETED">Completed</option>
  <option :value="ProjectStatusType.ACTIVE">Active</option>
  <option :value="ProjectStatusType.IN_PROGRESS">In Progress</option>
</select>

        </div>
        <button type="submit" class="btn btn-success">{{ isEditMode ? 'Update' : 'Create' }}</button>
      </form>
    </div>
   </BaseLayout>
  </template>
  
  <script setup lang="ts">
  import { reactive } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
import BaseLayout from '../../layouts/BaseLayout.vue'
import { ProjectStatusType } from '../../enums/ProjectStatusType'
import {  useProjectStore } from '../../stores/project'

  import { ref } from 'vue'
  const route = useRoute()
  const router = useRouter()
  const isEditMode = route.params.id !== undefined
  const projectStore = useProjectStore();
  const project = reactive({
    name: '',
    description: '',
    status: ProjectStatusType.ACTIVE
  })
  const errorMessage = ref('')
  

  
  const handleSubmit = async() => {
    try{
      await projectStore.createProject(project);
     router.push('/projects')

    }catch(error: any){
      errorMessage.value = error.response?.data?.errorMessage || "login failed"
    }
  }
  </script>
  