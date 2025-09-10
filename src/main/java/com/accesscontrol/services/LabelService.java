package com.accesscontrol.services;

import com.accesscontrol.dto.request.LabelRequest;
import com.accesscontrol.models.Label;
import java.util.*;

public interface LabelService {
  public Label createLabel(UUID projecttId, LabelRequest request);

  public List<Label> getLabelByProjectId(UUID id);
}
