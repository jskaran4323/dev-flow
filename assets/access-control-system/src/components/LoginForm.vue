<template>
  
    <v-container >
        <NavBar/>
        <v-row>
            <v-col>
            <h2>Login</h2>
            </v-col>
        </v-row>

        <v-form @submit.prevent="loginUser">
         <v-row>
            <v-col>
                <v-text-field
                v-model="identifier"
                label="Enter your username or email"
                :rules="[rules.required]"
                outlined
                required
                />
            </v-col>
         </v-row>
         <v-row>
            <v-col>
                <v-text-field
                v-model="password"
                type="password"
                label="Enter your password"
                :rules="[rules.required]"
                outlined
                required
                />
            </v-col>
         </v-row>
         <v-row>
            <v-col>
                <v-btn color="primary" type="submit">
                    Login
                </v-btn>
                
            </v-col>
         </v-row>
         <v-alert type="error" v-if="error" dense outlined class="mt-3">
        {{ error }}
      </v-alert>
        
        </v-form>
      
    </v-container>
</template>

<script>
  import { setToken } from '@/utils/auth';

import NavBar from './NavBar.vue';
export default{
  
    name: 'LoginForm',
    components:{
    NavBar
    },
    data(){
        return{
            identifier:'',
            password:'',
            error: '',
            rules:{
                required: v => !!v || 'Required',
            }
        };
    },
   methods:{
    async loginUser(){
     this.error = '';
     try{
        const res = await fetch('/auth/login',{
            method:'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
                identifier: this.identifier,
                password: this.password
            })
        });
        if (!res.ok){
            this.error="login failed"
            return;
        }
        const data = await res.json();
        setToken(data.token);
        this.$router.push({name: 'DashBoard'})
     }catch(e){
        this.error="something went bad"
        console.log(e);
        
     }

    }
   }

} 
</script>