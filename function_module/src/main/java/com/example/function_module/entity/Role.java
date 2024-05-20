package com.example.function_module.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "t_role")
@Data
public class Role{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Role() {
    }

    public Role(Long id) {
        this.id = id;
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}