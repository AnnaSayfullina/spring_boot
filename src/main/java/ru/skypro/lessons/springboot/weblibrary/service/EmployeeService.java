package ru.skypro.lessons.springboot.weblibrary.service;

import org.springframework.web.bind.annotation.PathVariable;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;

import java.util.List;

public interface EmployeeService {
    int getSumSalary();
    Employee getEmployeeMinSalary();
    Employee getEmployeeMaxSalary();
    List<Employee> getEmployeesHighSalary();
    void addEmployee(Employee employees);
    void editEmployee(String name, Integer salary, int id);
    Employee getEmployeeById(int id);
    void deleteEmployeeById (int id);
    List<Employee> employeesSalaryHigherThan(int salary);

}
