package ru.skypro.lessons.springboot.weblibrary.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeFullInfo {
    private String name;
    private Integer salary;
    private String positionName;

    public EmployeeFullInfo(String name, Integer salary, String positionName) {
        this.name = name;
        this.salary = salary;
        this.positionName = positionName;
    }
}
