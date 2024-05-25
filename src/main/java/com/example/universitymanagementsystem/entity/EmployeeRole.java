package com.example.universitymanagementsystem.entity;

import com.example.universitymanagementsystem.entity.enums.EmployeeRoleEnum;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity(name = "employee_role")
@Data
public class EmployeeRole extends BaseEntity{
    private EmployeeRoleEnum name;
}
