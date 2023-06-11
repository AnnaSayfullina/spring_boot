package ru.skypro.lessons.springboot.weblibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository employeeRepository;

    @Override
    public int getSumSalary() {
        List<Employee> employeeList = new ArrayList<>(employeeRepository.getAllEmployees());
        int sum = employeeList.stream()
                .mapToInt(e -> e.getSalary())
                .sum();
        return sum;
    }

    @Override
    public Employee getEmployeeMinSalary() {
        List<Employee> employeeList = new ArrayList<>(employeeRepository.getAllEmployees());
        Employee employee = employeeList.stream()
                .sorted(Comparator.comparing(Employee::getSalary))
                .findFirst()
                .get();
        return employee;
    }

    @Override
    public Employee getEmployeeMaxSalary() {
        List<Employee> employeeList = new ArrayList<>(employeeRepository.getAllEmployees());
        Employee employee = employeeList.stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .findFirst()
                .get();
        return employee;
    }

    @Override
    public List<Employee> getEmployeesHighSalary() {
        List<Employee> employeeList = new ArrayList<>(employeeRepository.getAllEmployees());

        double averageSalary = employeeList.stream()
                .mapToInt(Employee::getSalary)
                .average().orElse(0);

        List<Employee> employeesHighSalary = employeeList.stream()
                .filter(e-> e.getSalary()>averageSalary)
                .collect(Collectors.toList());

        return employeesHighSalary;
    }

    @Override
    public void addEmployee(Employee employee) {
        employeeRepository.addEmployee(employee);
    }

    @Override
    public void editEmployee(String name, Integer salary, int id) {
        employeeRepository.editEmployee(name, salary, id);
    }

    @Override
    public Employee getEmployeeById(int id) {
        return employeeRepository.getEmployeeById(id);
    }

    @Override
    public void deleteEmployeeById(int id) {
        employeeRepository.deleteEmployeeById(id);
    }

    @Override
    public List<Employee> employeesSalaryHigherThan(int salary) {
        List<Employee> employeeList = new ArrayList<>(employeeRepository.getAllEmployees());
        List<Employee> employeesHigherSalaryThan = employeeList.stream()
                .filter(e-> e.getSalary()>salary)
                .collect(Collectors.toList());
        return employeesHigherSalaryThan;
    }

}
