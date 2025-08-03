package com.accesscontrol.services;

import java.util.*;
import com.accesscontrol.dto.request.AddTeamMemberRequest;
import com.accesscontrol.dto.request.JoinRequestDto;
import com.accesscontrol.dto.response.ProjectUserResponse;


public interface ProjectUserService {
    public void addTeamMember(UUID projectId, AddTeamMemberRequest request);
    public List<ProjectUserResponse> getTeam(UUID projectId);
    
    
    void requestToJoin(UUID projectId, UUID userId);
    List<JoinRequestDto> getJoinRequests(UUID projectId, UUID ownerId);
    void approveJoinRequest(UUID projectId, UUID userId, UUID ownerId);
}

