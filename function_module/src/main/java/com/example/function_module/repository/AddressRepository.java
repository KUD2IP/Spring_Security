package com.example.function_module.repository;

import com.example.function_module.entity.AddressInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressInfo, Long> {

}
