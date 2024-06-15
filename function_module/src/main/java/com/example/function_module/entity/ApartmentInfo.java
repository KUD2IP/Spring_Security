package com.example.function_module.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "t_apartment_info")
@Data
public class ApartmentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "rooms_count")
    private String roomsCount;
    @Column(name = "peice")
    private String price;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "t_apartment_address",
            joinColumns = @JoinColumn(name = "apartment_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "address_id", referencedColumnName = "id")
    )
    private AddressInfo addressInfo;

    public ApartmentInfo() {

    }
}
