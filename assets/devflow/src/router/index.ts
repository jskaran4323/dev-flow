import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/auth'
import Login from '../pages/Login.vue'
import Register from '../pages/Register.vue'
import DashBoard from '../pages/DashBoard.vue'
import Home from '../pages/Home.vue'
import Project from '../pages/Project/Project.vue'
import ProjectForm from '../pages/Project/ProjectForm.vue'
import SelectProject from '../pages/Project/SelectProject.vue'
import Issues from '../pages/Issues/Issues.vue'
import IssueForm from '../pages/Issues/IssueForm.vue'
import ProjectDetails from '../pages/Project/ProjectDetails.vue'
import IssueDetals from '../pages/Issues/IssueDetails.vue'
import ProjectKanban from '../pages/ProjectKanban.vue'
import PublicProjects from '../pages/Project/PublicProjects.vue'
import UserProfile from '../pages/user/UserProfile.vue'
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
    name: 'ProjectIssues',
    path: '/projects/:projectId/issues',
    component: Issues
  },

  {
    name: 'CreateIssue',
    path: '/projects/:projectId/issues/new',
    component: IssueForm
  },

  
  {
    name: 'EditIssue',
    path: '/projects/:projectId/issues/:id/edit',
    component: IssueForm
  }
,
  {
    path: '/select-project',
    component: SelectProject
  },
  {
    path: '/projects/:projectId/issues/new',
    component: SelectProject
  }, 
  {
    path: '/projects/:projectId',
    name: 'ProjectDetail',
    component: ProjectDetails
  },
  {
    path: '/issues/:id',
    name: 'IssueDetails',
    component: IssueDetals // adjust path if needed
  },
  {
    path: '/projects/:projectId/kanban',
    name: 'ProjectKanban',
    component: ProjectKanban
  },
  {
    path: '/public-projects',
    name: 'PublicProjects',
    component: PublicProjects 
  },
  {
    path: '/profile',
    name: 'UserProfile',
    component: UserProfile
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
