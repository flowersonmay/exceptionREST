package ru.leskov.exceptionrest.controller;

public class PersonException extends Exception {
    public PersonException(String msg) {
        super(msg);
    }
    public PersonException(){
    }
}
