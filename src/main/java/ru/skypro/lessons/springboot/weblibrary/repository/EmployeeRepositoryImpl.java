package ru.skypro.lessons.springboot.weblibrary.repository;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeRepositoryImpl implements EmployeeRepository{

    private final static List<Employee> EMPLOYEE_LIST = List.of(
            new Employee("Anna", 150000),
            new Employee("Oleg", 120000),
            new Employee("Maksim", 130000),
            new Employee("Timur", 180000),
            new Employee("Marina", 110000)
    );


    @Override
    public List<Employee> getAllEmployees() {
        return EMPLOYEE_LIST;
    }
}
