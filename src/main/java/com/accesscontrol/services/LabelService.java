package com.accesscontrol.services;

import java.util.*;

import com.accesscontrol.dto.request.LabelRequest;
import com.accesscontrol.models.Label;



public interface LabelService {
    public Label createLabel(UUID projecttId, LabelRequest request);
    public List<Label> getLabelByProjectId(UUID id);
}
