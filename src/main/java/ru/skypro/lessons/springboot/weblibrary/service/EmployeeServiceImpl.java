package ru.skypro.lessons.springboot.weblibrary.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.exceptions.EmployeeNotFoundException;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository employeeRepository;

    Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void addEmployee(EmployeeDTO employeeDTO) {
        logger.info("Вызван метод для создания и добавления сотрудника в базу данных {}", employeeDTO );
        Employee employee = employeeDTO.toEmployee();
        employeeRepository.save(employee);
        logger.debug("Сотрудник {} создан", employee);

    }

    @Override
    public void editEmployee(String name, Integer salary, int id) {
        logger.info("Вызван метод для изменения сотрудника id = {}", id);
        EmployeeDTO employeeDTO = EmployeeDTO.fromEmployee(employeeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Сотрудник с id = {} не найден", id);
                    return new EmployeeNotFoundException();
                }));
        employeeDTO.setName(name);
        employeeDTO.setSalary(salary);
        Employee employee = employeeDTO.toEmployee();
        employeeRepository.save(employee);
        logger.debug("Сотрудник с id={} изменен, обновленные данные сотрудника: {}", id, employee);
    }

    @Override
    public EmployeeDTO getEmployeeById(int id) {
        logger.info("Вызван метод получения сотрудника по id = {}", id);
        EmployeeDTO employeeDTO = EmployeeDTO.fromEmployee(employeeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Сотрудник с id = {} не найден", id);
                    return new EmployeeNotFoundException();
                }));
        logger.debug("Сотрудник {} найден", employeeDTO);
        return  employeeDTO;
    }

    @Override
    public void deleteEmployeeById(int id) {
        logger.info("Вызван метод для удаления сотрудника по id = {}", id);
        EmployeeDTO employeeDTO = EmployeeDTO.fromEmployee(employeeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Сотрудник с id = {} не найден", id);
                    return new EmployeeNotFoundException();
                }));
        Employee employee = employeeDTO.toEmployee();
        employeeRepository.deleteById(employee.getId());
        logger.debug("Сотрудник {} удален", employee);

    }

    @Override
    public List<EmployeeDTO> getAllEmployees(){
        logger.info("Вызван метод для получения списка всех сотрудника");
        logger.debug("Список сотрудников сформирован");
        return employeeRepository.findAllEmployees().stream()
                .map(EmployeeDTO::fromEmployee)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO getEmployeeWithHighestSalary() {
        logger.info("Вызван метод для получения сотрудника с самой высокой зарплатой");

        Employee employee = employeeRepository.getEmployeeWithHighestSalary();
        EmployeeDTO employeeDTO = EmployeeDTO.fromEmployee(employee);
        logger.debug("Сотрудник с самой высокой зарплатой: {}", employeeDTO);

        return employeeDTO;
    }

    @Override
    public List<EmployeeDTO> getEmployeesByPosition(String position) {
        logger.info("Вызван метод для получения списка сотрудников по должности '{}'", position);

        List<Employee> employees = new ArrayList<>();
        List<EmployeeDTO> employeesDTO = new ArrayList<>();
        if(position == null){
            employees = employeeRepository.findAllEmployees();
            employeesDTO = employees.stream()
                    .map(EmployeeDTO::fromEmployee)
                    .collect(Collectors.toList());
        }else {
            employees = employeeRepository.getEmployeesByPosition(position);
            employeesDTO = employees.stream()
                    .map(EmployeeDTO::fromEmployee)
                    .collect(Collectors.toList());
        }

        logger.debug("Список сотрудников должности '{}': {}", position, employeesDTO);

        return employeesDTO;
    }

    @Override
    public EmployeeFullInfo getEmployeeFullInfoById(int id) {
        logger.info("Вызван метод для получения полной информации о сотруднике по id = {}", id);

        EmployeeFullInfo employeeFullInfo = employeeRepository.getEmployeeFullInfoById(id);
        logger.debug("Полной информация о сотруднике по id = {}: {}", id, employeeFullInfo);

        return employeeFullInfo;
    }

    @Override
    public List<Employee> getEmployeesWithPaging(int page) {
        logger.info("Вызван метод получения сотрудников постранично");

        Pageable employeeOfConcretePage = PageRequest.of(page, 10);
        Page<Employee> pageOfEmployee = employeeRepository.findAll(employeeOfConcretePage);

        return pageOfEmployee.stream()
                .toList();
    }

    @Override
    public void uploadFileWithEmployees(MultipartFile multipartFile) {
        logger.info("Вызван метод для загрузки из файла списка сотрудников и сохранении его в базу данных");

        File file = new File("newFile.json");

        List<EmployeeDTO> employeesDTO =
                null;
        try {
        Files.write(file.toPath(), multipartFile.getBytes());

        ObjectMapper objectMapper = new ObjectMapper();

         employeesDTO = objectMapper.readValue(multipartFile.getInputStream(), new TypeReference<>() {});
        } catch (IOException e) {
            logger.error("Список сотрудников из файла в базу данных не добавлен", e);
            throw new RuntimeException(e);
        }
        employeeRepository.saveAll(employeesDTO.stream().map(EmployeeDTO::toEmployee).toList());

        logger.debug("Список сотрудников добавлен в базу");

    }
}
