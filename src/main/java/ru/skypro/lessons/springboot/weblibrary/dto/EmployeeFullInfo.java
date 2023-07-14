package ru.skypro.lessons.springboot.weblibrary.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class EmployeeFullInfo implements Serializable {
    private String name;
    private Integer salary;
    private String positionName;

    public EmployeeFullInfo(String name, Integer salary, String positionName) {
        this.name = name;
        this.salary = salary;
        this.positionName = positionName;
    }
}
