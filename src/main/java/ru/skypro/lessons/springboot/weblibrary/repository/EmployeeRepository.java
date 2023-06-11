package ru.skypro.lessons.springboot.weblibrary.repository;

import ru.skypro.lessons.springboot.weblibrary.exceptions.EmployeeNotFoundException;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;

import java.util.List;

public interface EmployeeRepository {
    List<Employee> getAllEmployees();
    void addEmployee(Employee employee);
    void editEmployee(String name, Integer salary, int id) throws EmployeeNotFoundException;
    Employee getEmployeeById(int id) throws EmployeeNotFoundException;
    void deleteEmployeeById (int id);
}
