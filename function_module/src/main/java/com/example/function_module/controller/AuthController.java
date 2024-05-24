package com.example.function_module.controller;


import com.example.function_module.entity.User;
import com.example.function_module.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String home(Model model){
        if(userService.isUserLoggedIn()){
            model.addAttribute("logged", "Sign Out");
            return "index";
        }
        model.addAttribute("notLogged", "notLogged");
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        if(userService.isUserLoggedIn()){
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/account")
    public String forUser(Model model){
        model.addAttribute("logged", "Sign Out");
        return "account";
    }

}
