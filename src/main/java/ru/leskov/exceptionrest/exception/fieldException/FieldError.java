package ru.leskov.exceptionrest.exception.fieldException;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.leskov.exceptionrest.controller.ErrorMessage;
import ru.leskov.exceptionrest.controller.PersonException;

//
@RestControllerAdvice
@Slf4j
public class FieldError {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onConstraintValidationException(ConstraintViolationException exception) {
        var voilations = exception.getConstraintViolations()
                .stream()
                .map(violation ->
                        new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage())
                ).toList();
        exception.getConstraintViolations()
                .forEach(
                        v -> log.error(v.getPropertyPath().toString())
                );
        return new ValidationErrorResponse(voilations);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        var violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(
                        error.getField(),
                        error.getDefaultMessage()))
                .toList();
        log.error(e.getBindingResult().toString());
        log.error(e.getBindingResult().getFieldErrors().toString());
        e.getBindingResult().getFieldErrors().forEach(error -> log.error(error.getField() + " " + error.getDefaultMessage()));
        return new ValidationErrorResponse(violations);
    }


    @ExceptionHandler(PersonException.class)
    public ResponseEntity<ErrorMessage> notFoundException(PersonException exception){
        log.error(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> mismatchException(MethodArgumentTypeMismatchException exception) {
        log.error(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }

}
