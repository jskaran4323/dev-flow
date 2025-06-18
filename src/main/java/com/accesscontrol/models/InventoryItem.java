package com.accesscontrol.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

import java.util.UUID;

import com.accesscontrol.enums.InventoryType;


@Entity
@Table(name = "Inventory_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(unique = true)
    private String sku;
    private int quantity;
    private BigDecimal price;
    private int type;

    public InventoryType getTypeEnum(){
        return InventoryType.fromValue(this.type);
    }
    public void setTypeEnum(InventoryType type){
        this.type = type.getValue();
    }
}
