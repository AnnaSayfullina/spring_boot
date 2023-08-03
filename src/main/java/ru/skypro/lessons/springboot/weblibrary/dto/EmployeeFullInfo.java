package ru.skypro.lessons.springboot.weblibrary.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeFullInfo implements Serializable {
    private String name;
    private Integer salary;
    private String positionName;

    public EmployeeFullInfo(String name, Integer salary, String positionName) {
        this.name = name;
        this.salary = salary;
        this.positionName = positionName;
    }

    @Override
    public String toString() {
        return "EmployeeFullInfo{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", positionName='" + positionName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeFullInfo that = (EmployeeFullInfo) o;
        return Objects.equals(name, that.name) && Objects.equals(salary, that.salary) && Objects.equals(positionName, that.positionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, salary, positionName);
    }
}
