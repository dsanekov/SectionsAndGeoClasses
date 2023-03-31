package ru.natlex.natlexTestApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.natlex.natlexTestApp.models.Person;
import ru.natlex.natlexTestApp.repositories.PeopleRepository;

import java.beans.Transient;

@Service
public class RegistrationService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }
    @Transactional
    public void doRegistration(Person person){
        peopleRepository.save(person);
    }
}
