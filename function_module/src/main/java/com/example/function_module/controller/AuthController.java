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
    @GetMapping("/all")
    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    public @ResponseBody Iterable<User> getAllUser() {
        return userService.allUsers();
    }

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/account")
    public String forUser(){
        return "account";
    }

}
