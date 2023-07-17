package ru.skypro.lessons.springboot.weblibrary.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReportNotFoundException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = " Отчет не найден" ;

    public ReportNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
