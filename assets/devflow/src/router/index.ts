import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/auth'
import Login from '../pages/Login.vue'
import Register from '../pages/Register.vue'
import DashBoard from '../pages/DashBoard.vue'
import Home from '../pages/Home.vue'
import Project from '../pages/Project/Project.vue'
import ProjectForm from '../pages/Project/ProjectForm.vue'
import SelectProject from '../pages/Project/SelectProject.vue'
import Issues from '../pages/issues/issues.vue'
import IssueForm from '../pages/issues/IssueForm.vue'
// ... your other routes

const routes = [
  { path: '/', component: Home },
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  { path: '/dashboard', component: DashBoard },
  {
    path: '/projects',
    component: Project,
  },
  {
    path: '/projects/new',
    component: ProjectForm,
  },
  {
    path: '/projects/:id/edit',
    component: ProjectForm
  },
  {
    name: "createIssue",
    path: '/project/:projectId/create-issue',
    component: Issues,
  },
  {
    path: '/issues/new',
    component: IssueForm,
  },
  {
    path: '/issues/:id/edit',
    component: Issues
  },

  {
    path: '/select-project',
    component: SelectProject
  },
  {
    path: '/projects/:projectId/issues/new',
    component: SelectProject
  } 




]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.beforeEach((to, from, next) => {
  const auth = useAuthStore()


  if (!auth.token && typeof window !== 'undefined') {
    const storedToken = localStorage.getItem('token')
    if (storedToken) {
      auth.initializeAuth()
    }
  }

  
  if (auth.isAuthenticated && (to.path === '/login' || to.path === '/register')) {
    return next('/dashboard')
  }

  next()
})

export default router
