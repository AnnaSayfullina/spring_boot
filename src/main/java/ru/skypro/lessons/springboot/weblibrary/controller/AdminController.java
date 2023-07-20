package ru.skypro.lessons.springboot.weblibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;
import ru.skypro.lessons.springboot.weblibrary.service.EmployeeService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin/employees")
@RequiredArgsConstructor
    public class AdminController {
        private final EmployeeService employeeService;

        @GetMapping("/all")
        public List<EmployeeDTO> getAllEmployees(){
            return employeeService.getAllEmployees();
        }

        /**
         * POST-запрос localhost:8080/employees/
         * Он должен создавать множество новых сотрудников;
         */

        @PostMapping("/")
        public void addEmployee (@RequestBody EmployeeDTO employeeDTO){
            employeeService.addEmployee(employeeDTO);
        }

        /**
         *PUT-запрос localhost:8080/employees/{id}
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
        public EmployeeDTO getEmployeeById (@PathVariable int id){
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
         * GET-запрос localhost:8080/employees/withHighestSalary
         * Он должен возвращать информацию о сотрудниках с самой высокой зарплатой в фирме;
         */
        @GetMapping("/withHighestSalary")
        public EmployeeDTO getEmployeeWithHighestSalary(){
            return employeeService.getEmployeeWithHighestSalary();
        }

        /**
         * GET-запрос localhost:8080/employees?position=
         * Он должен принимать на вход опциональный параметр position
         *  и возвращать информацию о всех сотрудниках фирмы, указанной в параметре должности.
         *  Если параметр не указан, то возвращать необходимо всех сотрудников.
         */
        @GetMapping("/position")
        public List<EmployeeDTO> getEmployeesByPosition(@RequestParam(value = "position", required = false) String position) {
            return employeeService.getEmployeesByPosition(position);
        }

        /**
         * GET-запрос localhost:8080/employees/{id}/fullInfo
         * Он должен возвращать полную информацию о сотруднике (имя, зарплата, название должности)
         * с переданным в пути запроса идентификатором.
         */
        @GetMapping("/{id}/fullInfo")
        public EmployeeFullInfo getEmployeeFullInfoById(@PathVariable int id){
            return employeeService.getEmployeeFullInfoById(id);
        }

        /**
         * GET-запрос   localhost:8080/employees/page?page=
         Он должен возвращать информацию о сотрудниках, основываясь на номере страницы.
         Если страница не указана, то возвращается первая страница. Номера страниц начинаются с 0.
         Лимит на количество сотрудников на странице — 10 человек.
         */
        @GetMapping("/page")
        public List<Employee> getEmployeesWithPaging(@RequestParam("page") int page){
            return employeeService.getEmployeesWithPaging(page);
        }

        /**
         * POST-запрос localhost:8080/employees/upload.
         * Он должен принимать на вход файл JSON, содержащий список сотрудников в JSON-формате.
         * Все сотрудники из загружаемого файла должны быть сохранены в базе данных.
         */
        @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public void uploadFileWithEmployees(@RequestParam("file") MultipartFile multipartFile) throws IOException {
            employeeService.uploadFileWithEmployees(multipartFile);
        }

    }
