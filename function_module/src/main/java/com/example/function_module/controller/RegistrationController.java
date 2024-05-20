package com.example.function_module.controller;

import com.example.function_module.dto.UserRegisterDto;
import com.example.function_module.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private UserService userService;
    @ModelAttribute("user")
    public UserRegisterDto userRegistrationDto(){
        return new UserRegisterDto();
    }

    @PostMapping
    public String registrationPOST(
            @ModelAttribute("user") UserRegisterDto registrationDto) {
        userService.save(registrationDto);
        return "redirect:/login";
    }

    @GetMapping
    public String registrationGET() {
        return "registration";
    }

}
