package ru.leskov.exceptionrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.leskov.exceptionrest.moodel.Person;
import ru.leskov.exceptionrest.moodel.PersonDTO;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {
    Person getPersonById(Long id);
    List<Person> findAll();
    Person findPersonByEmail(String email);
}
