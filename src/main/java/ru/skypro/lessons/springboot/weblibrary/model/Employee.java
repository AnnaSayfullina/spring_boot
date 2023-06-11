package ru.skypro.lessons.springboot.weblibrary.model;

import lombok.*;

import java.util.Objects;

@Data
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Employee {
    private String name;
    private int salary;
    private int id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
