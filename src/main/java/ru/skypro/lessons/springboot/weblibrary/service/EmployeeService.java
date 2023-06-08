package ru.skypro.lessons.springboot.weblibrary.service;

import ru.skypro.lessons.springboot.weblibrary.model.Employee;

import java.util.List;

public interface EmployeeService {
    int getSumSalary();
    Employee getEmployeeMinSalary();
    Employee getEmployeeMaxSalary();
    List<Employee> getEmployeesHighSalary();
}
