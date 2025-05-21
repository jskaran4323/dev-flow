<template>
 <v-container>
  <NavBar/>
  <v-row>
    <v-col>
      <h2 style="text-align: center;">How can i help you today ?</h2>
      <v-card height="400px" class="overflow-y-auto mb-3">
          <v-list>
            <v-list-item v-for="(msg, i) in messages" :key="i">
              <v-list-item-content>
                <v-list-item-title :class="msg.from === 'user' ? 'text-right' : 'text-left'">
                  <strong>{{ msg.from === 'user' ? 'You' : 'Bot' }}:</strong> {{ msg.text }}
                </v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </v-card>
  
        <v-text-field
          v-model="newMessage"
          label="Type a message"
          @keyup.enter="sendMessage"
          outlined
        />
        <v-btn color="primary" @click="sendMessage">Send</v-btn>
    </v-col>
  </v-row>
 </v-container>
  
</template>

<script>
import NavBar from './NavBar.vue';
export default {
  name: 'UserDashboard',
  data(){
    return {
      newMessage: '',
      messages: []
    }
  },
  components: {
   NavBar
  },
  methods: {
  async sendMessage(){
    const userMessage = this.newMessage.trim();
    const token = localStorage.getItem('token');
    if(!userMessage){
      return
    }
    this.messages.push({from: 'user', text: userMessage})
    this.newMessage='';

    try{
      const res = await fetch('/api/chat',{
        method: 'POST',
        headers:{"Content-Type": "application/json",
        'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({message:userMessage})
      })
      const data = await res.json();
      this.messages.push({form: 'bot', text: data.reply || 'No response'})
    }catch(e){
      console.log(e);
      
    }

  }
  }
}
</script>
