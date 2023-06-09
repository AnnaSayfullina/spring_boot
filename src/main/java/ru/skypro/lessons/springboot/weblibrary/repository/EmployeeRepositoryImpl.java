package ru.skypro.lessons.springboot.weblibrary.repository;

import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.weblibrary.exceptions.EmployeeNotFoundException;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository{

    private final static List<Employee> EMPLOYEE_LIST = new ArrayList<>(Arrays.asList(
            new Employee("Anna", 150000, 1),
            new Employee("Oleg", 120000, 2),
            new Employee("Maksim", 130000, 3),
            new Employee("Timur", 180000,4),
            new Employee("Marina", 110000, 5)
    ));


    @Override
    public List<Employee> getAllEmployees() {
        return EMPLOYEE_LIST;
    }

    @Override
    public void addEmployee(Employee employee) {
        getAllEmployees().add(employee);
    }

    @Override
    public void editEmployee(String name, Integer salary, int id) {

        getAllEmployees().stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .map(employee -> {
                    if (!name.isEmpty()){
                        employee.setName(name);
                    }
                    if (salary!=null) {
                        employee.setSalary(salary);
                    }
                    return employee;
                }).orElseThrow(EmployeeNotFoundException::new);
    }

    @Override
    public Employee getEmployeeById(int id) {
        Employee employee = getAllEmployees().stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
        return employee;
    }

    @Override
    public void deleteEmployeeById(int id) {
        Employee employee = getAllEmployees().stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
        getAllEmployees().remove(employee);
    }

}
