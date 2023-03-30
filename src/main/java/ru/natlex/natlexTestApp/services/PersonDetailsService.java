package ru.natlex.natlexTestApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.natlex.natlexTestApp.Security.PersonDetails;
import ru.natlex.natlexTestApp.models.Person;
import ru.natlex.natlexTestApp.repositories.PeopleRepository;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final PeopleRepository peopleRepository;
    @Autowired
    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUsername(username);
        if(person.isEmpty()){
            throw new UsernameNotFoundException("User not found!");
        }
        return new PersonDetails(person.get());
    }
}
