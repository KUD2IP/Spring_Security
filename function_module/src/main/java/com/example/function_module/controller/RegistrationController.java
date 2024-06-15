package com.example.function_module.controller;

import com.example.function_module.dto.UserRegisterDto;
import com.example.function_module.entity.User;
import com.example.function_module.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.example.function_module.controller.ControllerConst.USER_REGISTRATION;


@Controller
@RequestMapping(USER_REGISTRATION)
public class RegistrationController {
    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserRegisterDto userRegistrationDto(){
        return new UserRegisterDto();
    }

    @PostMapping
    public String registrationPOST(
            @ModelAttribute("user") UserRegisterDto registrationDto,
                                    Model model) {
        User user = userService.findByUsername(registrationDto.getUsername());

        if (!(user == null) &&
                registrationDto.getUsername()
                .equals(user.getUsername())
        ){
            model.addAttribute("replay",
                    "There is already a user with this login");
            return "registration";
        }
        if(registrationDto.getUsername().length() < 5 ||
                registrationDto.getNickname().length() < 5 ||
                registrationDto.getPassword().length() < 5
        ) {
            model.addAttribute("small",
                    "the login password and nickname must be longer than 5 characters");
            return "registration";
        }

        userService.save(registrationDto);
        return "redirect:/login";
    }



    @GetMapping
    public String registrationGET() {
        if(userService.isUserLoggedIn()){
            return "redirect:/";
        }
        return "registration";
    }

}
