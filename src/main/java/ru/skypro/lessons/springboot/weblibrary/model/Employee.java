package ru.skypro.lessons.springboot.weblibrary.model;

import lombok.*;

@Data
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Employee {
    private String name;
    private int salary;

}
