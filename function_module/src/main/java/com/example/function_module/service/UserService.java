package com.example.function_module.service;

import com.example.function_module.dto.UserRegisterDto;
import com.example.function_module.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void save(UserRegisterDto registerDto);

}
