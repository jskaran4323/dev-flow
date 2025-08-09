package com.accesscontrol.models;

import java.util.*;

import com.accesscontrol.enums.LabelType;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
   
    @Column(name = "type")
    private int type;

    @ManyToOne
    @JsonBackReference("project-label")
    private Project project;
   
    public LabelType getLabelTypeEnum(){
        return LabelType.fromValue(this.type);
    }
    public void setLabelTypeEnum(LabelType type){
        this.type = type.getValue();
    }

}
