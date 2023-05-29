package ru.skypro.lessons.springboot.weblibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;
import ru.skypro.lessons.springboot.weblibrary.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("salary/sum")
    public int getSumSalary(){
        return employeeService.getSumSalary();
    }

    @GetMapping("salary/min")
    public Employee getEmployeeMinSalary(){
        return employeeService.getEmployeeMinSalary();
    }

    @GetMapping("salary/max")
    public Employee getEmployeeMaxSalary(){
        return  employeeService.getEmployeeMaxSalary();
    }

    @GetMapping("high-salary")
    public List<Employee> getEmployeesHighSalary(){
        return employeeService.getEmployeesHighSalary();
    }



}
