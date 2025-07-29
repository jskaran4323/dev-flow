package com.accesscontrol.services;

import java.util.*;
import com.accesscontrol.dto.request.AddTeamMemberRequest;
import com.accesscontrol.dto.response.ProjectUserResponse;


public interface ProjectUserService {
    public void addTeamMember(UUID projectId, AddTeamMemberRequest request);
    public List<ProjectUserResponse> getTeam(UUID projectId);
   
}

