package com.accesscontrol.services;

import java.util.List;
import java.util.UUID;

import com.accesscontrol.dto.request.AddTeamMemberRequest;
import com.accesscontrol.dto.request.JoinRequestDto;
import com.accesscontrol.dto.response.ProjectUserResponse;

public interface ProjectUserService {

    void addTeamMember(UUID projectId, AddTeamMemberRequest request);
    List<ProjectUserResponse> getTeam(UUID projectId);

    void requestToJoin(UUID projectId, UUID userId);
    List<JoinRequestDto> getJoinRequests(UUID projectId, UUID ownerId);
    void approveJoinRequest(UUID projectId, UUID userId, UUID ownerId);

    boolean isProjectOwner(UUID projectId, UUID userId);
    boolean isOwnerOrMember(UUID projectId, UUID userId);
}
