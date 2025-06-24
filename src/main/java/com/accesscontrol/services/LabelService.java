package com.accesscontrol.services;

import java.util.*;

import com.accesscontrol.dto.LabelRequest;
import com.accesscontrol.models.Label;



public interface LabelService {
    public Label createLabel(UUID proejcrtId, LabelRequest request);
    public List<Label> getLabelByProjectId(UUID id);
}
