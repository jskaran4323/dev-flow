package com.accesscontrol.services;
import com.accesscontrol.models.InventoryItem;
import com.accesscontrol.enums.InventoryType;
import java.util.*;

public interface InventoryItemService {    
    List<InventoryItem> getAll();
    InventoryItem getById(UUID id);
     InventoryItem create(InventoryItem item);
    InventoryItem update(Long id, InventoryItem item);
    void delete(Long id);
    List<InventoryItem> getByType(InventoryType type);

}
