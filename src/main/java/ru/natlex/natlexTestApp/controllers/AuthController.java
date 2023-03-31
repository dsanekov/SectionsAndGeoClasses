package ru.natlex.natlexTestApp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.natlex.natlexTestApp.models.Person;
import ru.natlex.natlexTestApp.services.RegistrationService;
import ru.natlex.natlexTestApp.util.Validator;

@Controller
@RequestMapping("/registration")
public class AuthController {
    private final Validator personValidator;
    private final RegistrationService registrationService;

    @Autowired
    public AuthController(Validator personValidator, RegistrationService registrationService) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
    }

    @GetMapping()
    public String registration(@ModelAttribute("person")Person person){
        return "registration";
    }
    @PostMapping()
    public String doRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person,bindingResult);
        if(bindingResult.hasErrors()){
            return "/registration";
        }
        registrationService.doRegistration(person);
        return "redirect:/login";
    }
}
