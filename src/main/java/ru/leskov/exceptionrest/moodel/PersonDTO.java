package ru.leskov.exceptionrest.moodel;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    @NotBlank(message = "name must be not null")
    private String name;

    @NotBlank(message = "surname must be not null")
    private String sName;

    @Min(value = 0,message = "Больше 0")
    @Max(value = 100,message = "Меньше 100")
    private int age;

    @Email(message = "Email must be created")
    private String email;
}
