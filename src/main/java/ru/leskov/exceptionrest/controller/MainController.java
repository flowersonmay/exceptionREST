package ru.leskov.exceptionrest.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.leskov.exceptionrest.moodel.Person;
import ru.leskov.exceptionrest.moodel.PersonDTO;
import ru.leskov.exceptionrest.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Validated
public class MainController {

    private final PersonService personService;

    @GetMapping()
    public List<Person> getPerson() {
        return personService.getAll();
    }

    @GetMapping("/{id}")
    public PersonDTO getPersonById(@PathVariable @Min(1)Long id) throws PersonException {
        var person = personService.findById(id);
        return PersonDTO.builder()
                .age(person.getAge())
                .name(person.getName())
                .sName(person.getSName())
                .email(person.getEmail())
                .build();
    }

    @PostMapping
    public String addNewPerson(@RequestBody @Valid PersonDTO personDTO){
        personService.save(personDTO);
        return  "success";
    }
    @PutMapping("/{id}")
    public String updatePerson(@PathVariable @Min(1) Long id,
                               @RequestBody @Valid PersonDTO personDTO) throws PersonException {
            personService.update(personDTO,id);
        return "success";
    }


}
