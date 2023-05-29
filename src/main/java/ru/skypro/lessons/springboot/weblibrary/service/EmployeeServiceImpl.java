package ru.skypro.lessons.springboot.weblibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository employeeRepository;

    @Override
    public int getSumSalary() {
        return employeeRepository.getSumSalary();
    }

    @Override
    public Employee getEmployeeMinSalary() {
        return employeeRepository.getEmployeeMinSalary();
    }

    @Override
    public Employee getEmployeeMaxSalary() {
        return employeeRepository.getEmployeeMaxSalary();
    }

    @Override
    public List<Employee> getEmployeesHighSalary() {
        return employeeRepository.getEmployeesHighSalary();
    }
}
