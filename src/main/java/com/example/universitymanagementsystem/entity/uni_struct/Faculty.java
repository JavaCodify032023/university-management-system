package com.example.universitymanagementsystem.entity.uni_struct;

import com.example.universitymanagementsystem.entity.BaseEntity;
import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Faculty")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Faculty extends BaseEntity {

    private String name;

    public Faculty(Long id, String name) {
        super(id);
        this.name = name;
    }
}
