package com.accesscontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accesscontrol.dto.ChatRequest;
import com.accesscontrol.dto.ChatResponse;
import com.accesscontrol.services.ChatService;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    @Autowired
   private ChatService chatService;

   @PostMapping
   public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request){
   String reply = chatService.getReplyFromAI(request.getMessage());
   return ResponseEntity.ok(new ChatResponse(reply));
}
}
