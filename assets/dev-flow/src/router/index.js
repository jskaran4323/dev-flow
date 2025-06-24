import Vue from 'vue';
import Router from 'vue-router';

import UserDashBoard from '@/components/user/UserDashBoard.vue';
import UserForm from '@/components/user/UserForm.vue';
import LoginForm from '@/components/user/LoginForm.vue';
import HomePage from '@/components/home/HomePage.vue';


Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [ 
    { path: '/', name: 'Home', component: HomePage },
    { path: '/register', name: 'Register', component: UserForm },
    {path: '/dashboard', name:'DashBoard', component: UserDashBoard},
    {path: '/login', name:'Login', component: LoginForm}
  ]
});
