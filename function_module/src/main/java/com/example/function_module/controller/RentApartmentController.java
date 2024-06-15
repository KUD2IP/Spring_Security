package com.example.function_module.controller;


import com.example.function_module.dto.ApartmentFullInfoDto;
import com.example.function_module.dto.UserRegisterDto;
import com.example.function_module.service.RentApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static com.example.function_module.controller.ControllerConst.APARTMENT_REGISTRATION;

@RestController
public class RentApartmentController {

    @Autowired
    private RentApartmentService rentApartmentService;

    @ModelAttribute("apart")
    public ApartmentFullInfoDto apartmentFullInfoDto(){
        return new ApartmentFullInfoDto();
    }


    @PostMapping(APARTMENT_REGISTRATION)
    public ModelAndView registrationNewApart(@ModelAttribute("apart") ApartmentFullInfoDto apartmentFullInfoDto){
        rentApartmentService.saveNewApart(apartmentFullInfoDto);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration_apart");
        return modelAndView;
    }

    @GetMapping(APARTMENT_REGISTRATION)
    public ModelAndView regAp() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration_apart");
        return modelAndView;
    }
}
