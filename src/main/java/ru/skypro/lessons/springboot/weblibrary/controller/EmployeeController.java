package ru.skypro.lessons.springboot.weblibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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

    /**
     * POST-запрос
     * localhost:8080/employees/
     * Он должен создавать множество новых сотрудников;
     */

    @PostMapping("/")
    public void addEmployee (@RequestBody Employee employee){
        employeeService.addEmployee(employee);
    }

    /**
     *PUT-запрос
     * localhost:8080/employees/{id}
     * Он должен редактировать сотрудника с указанным id;
     */
    @PutMapping("{id}")
    public void editEmployee(@RequestParam (value = "name", required = false) String name,
                             @RequestParam (value = "salary", required = false) Integer salary,
                             @PathVariable("id") int id ) {
        employeeService.editEmployee(name, salary, id);
    }

    /**
     GET-запрос
     localhost:8080/employees/{id}
     Он должен возвращать информацию о сотруднике с переданным id;
     */
    @GetMapping("{id}")
    public Employee getEmployeeById (@PathVariable int id){
        return employeeService.getEmployeeById(id);
    }

    /**
     * DELETE-запрос
     * localhost:8080/employees/{id}
     * Он должен удалять сотрудника с переданным id;
     */
    @DeleteMapping("{id}")
    public void deleteEmployeeById (@PathVariable int id){
        employeeService.deleteEmployeeById(id);
    }

    /**
     * GET-запрос
     * localhost:8080/employees/salaryHigherThan?salary=
     * Он должен возвращать всех сотрудников, зарплата которых выше переданного параметра salary.
     */
    @GetMapping("salaryHigherThan")
    public List<Employee> employeesSalaryHigherThan(@RequestParam("salary") int salary){
        return employeeService.employeesSalaryHigherThan(salary);
    }

}
