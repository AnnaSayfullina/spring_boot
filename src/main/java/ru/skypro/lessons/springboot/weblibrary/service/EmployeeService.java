package ru.skypro.lessons.springboot.weblibrary.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;

import java.io.IOException;
import java.util.List;
@Service
public interface EmployeeService {
//    int getSumSalary();
//    Employee getEmployeeMinSalary();
//    Employee getEmployeeMaxSalary();
//    List<Employee> getEmployeesHighSalary();
    EmployeeDTO addEmployee(EmployeeDTO employeeDTO);
    void editEmployee(String name, Integer salary, int id);
    EmployeeDTO getEmployeeById(int id);
    void deleteEmployeeById (int id);
//    List<Employee> employeesSalaryHigherThan(int salary);

    EmployeeDTO getEmployeeWithHighestSalary();

    List<EmployeeDTO> getAllEmployees();
    List<EmployeeDTO> getEmployeesByPosition(String position);
    EmployeeFullInfo getEmployeeFullInfoById(int id);
    List<Employee> getEmployeesWithPaging(int page);

    void uploadFileWithEmployees(MultipartFile multipartFile) throws IOException;

}
