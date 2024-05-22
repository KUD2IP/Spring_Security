package com.example.function_module.repository;

import com.example.function_module.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByLogin(String Login);
//    User findByPassword()
}