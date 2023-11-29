package ru.leskov.exceptionrest.service;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.leskov.exceptionrest.controller.PersonException;
import ru.leskov.exceptionrest.moodel.Person;
import ru.leskov.exceptionrest.moodel.PersonDTO;
import ru.leskov.exceptionrest.repository.PersonRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public List<Person> getAll() {
        return personRepository.findAll();
    }

    public Person findById(@NotNull Long id) throws PersonException {
        return personRepository.findById(id).orElseThrow(() -> new PersonException(String.format("Пользователь с id %d не найден",id)));
    }

    public void save(PersonDTO personDTO) {
        var person = Person.builder()
                .age(personDTO.getAge())
                .name(personDTO.getName())
                .sName(personDTO.getSName()).build();
        personRepository.save(person);
    }
    public void update(PersonDTO personDTO, Long id) throws PersonException {
        var personToUpdate = findById(id);
        if (!personToUpdate.getEmail().equals(personDTO.getEmail()) &&
                personRepository.findPersonByEmail(personDTO.getEmail()).getId()!=id){
            throw new PersonException(String.format("Почтовый адрес %s занят  не найден",personDTO.getEmail()));
        }
        var person = Person.builder()
                .age(personDTO.getAge())
                .name(personDTO.getName())
                .sName(personDTO.getSName()).build();
        personToUpdate.setAge(person.getAge());
        personToUpdate.setName(person.getName());
        personToUpdate.setSName(person.getSName());
        personToUpdate.setEmail(person.getEmail());
        personRepository.save(personToUpdate);
    }

}
