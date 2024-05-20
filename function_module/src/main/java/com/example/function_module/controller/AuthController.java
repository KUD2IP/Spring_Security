package com.example.function_module.controller;


import com.example.function_module.entity.User;
import com.example.function_module.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {


    @Autowired
    private UserServiceImpl userService;
    @GetMapping("/api/all")
    public @ResponseBody Iterable<User> getAllUser() {
        // This returns a JSON or XML with the users
        return userService.allUsers();
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/User")
    @PreAuthorize(value = "hasAuthority('ROLE_USER')")
    public String forUser(){
        return "account";
    }

}
