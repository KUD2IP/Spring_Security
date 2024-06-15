package com.example.function_module.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "t_address_info")
@Data
public class AddressInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "city")
    private String city;
    @Column(name = "street")
    private String street;
    @Column(name = "building_number")
    private String buildingNumber;
    public AddressInfo() {
    }

}
