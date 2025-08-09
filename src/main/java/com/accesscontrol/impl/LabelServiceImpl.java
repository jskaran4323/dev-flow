package com.accesscontrol.impl;

import java.util.*;

import org.springframework.stereotype.Service;

import com.accesscontrol.dto.request.LabelRequest;
import com.accesscontrol.models.Label;
import com.accesscontrol.models.Project;

import com.accesscontrol.repositories.LabelRepository;
import com.accesscontrol.repositories.ProjectRepository;
import com.accesscontrol.services.LabelService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LabelServiceImpl implements LabelService{
     public final ProjectRepository projectRepository;
     public final LabelRepository labelRepository; 

    @Override
    public Label createLabel(UUID projectId, LabelRequest request) {
       Project project = projectRepository.findById(projectId)
       .orElseThrow(() -> new RuntimeException("Project not found"));

       Label label = new Label();
       
       label.setLabelTypeEnum(request.getType());
       label.setProject(project);
       
        return labelRepository.save(label);
    }
    

    @Override
    public List<Label> getLabelByProjectId(UUID id) {
        Project project = projectRepository.findById(id).orElseThrow(()-> new RuntimeException("project not found"));
        return labelRepository.findByProject(project);
    }

}
