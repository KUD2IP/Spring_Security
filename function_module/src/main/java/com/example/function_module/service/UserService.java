package com.example.function_module.service;

import com.example.function_module.dto.UserRegisterDto;
import com.example.function_module.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    void save(UserRegisterDto registerDto);
    public boolean isUserLoggedIn();
    public User findByUsername(String username);

    public List<User> allUsers();


}
