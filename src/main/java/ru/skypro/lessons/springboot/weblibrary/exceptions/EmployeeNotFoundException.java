package ru.skypro.lessons.springboot.weblibrary.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmployeeNotFoundException extends RuntimeException {
        private static final String DEFAULT_MESSAGE = " Сотрудник не найден" ;

    public EmployeeNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
