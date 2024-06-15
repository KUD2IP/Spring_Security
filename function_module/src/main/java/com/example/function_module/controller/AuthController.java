package com.example.function_module.controller;


import com.example.function_module.dto.LoginDto;
import com.example.function_module.entity.User;
import com.example.function_module.service.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.example.function_module.controller.ControllerConst.USER_ACCOUNT;
import static com.example.function_module.controller.ControllerConst.USER_LOGIN;

@Controller
public class AuthController {


    @Autowired
    private UserService userService;


    @GetMapping("/all")
    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    public @ResponseBody Iterable<User> getAllUser() {
        return userService.allUsers();
    }

    @GetMapping("/")
    public String home(Model model){
        if(userService.isUserLoggedIn()){
            model.addAttribute("logged", "Sign Out");
            return "index";
        }
        model.addAttribute("notLogged", "notLogged");
        return "index";
    }

    @GetMapping(USER_LOGIN)
    public String login(){
        if(userService.isUserLoggedIn()){
            return "redirect:/";
        }
        return "login";
    }


    @GetMapping(USER_ACCOUNT)
    public String forUser(){
        return "account";
    }

}
