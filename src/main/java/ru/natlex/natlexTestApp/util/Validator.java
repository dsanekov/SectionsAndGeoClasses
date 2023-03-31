package ru.natlex.natlexTestApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.natlex.natlexTestApp.models.Person;
import ru.natlex.natlexTestApp.services.PersonDetailsService;

@Component
public class Validator implements org.springframework.validation.Validator {
    private final PersonDetailsService personDetailsService;

    @Autowired
    public Validator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        try {
            personDetailsService.loadUserByUsername(person.getUsername());
        }
        catch (UsernameNotFoundException e){
            return;//user not found - this ok!
        }
        errors.rejectValue("username", "", "[This username already exist!]");
    }
}
