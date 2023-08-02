package ru.skypro.lessons.springboot.weblibrary;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.exceptions.EmployeeNotFoundException;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;
import ru.skypro.lessons.springboot.weblibrary.model.Position;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.weblibrary.service.EmployeeServiceImpl;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository mockedRepository;

    @InjectMocks
    private EmployeeServiceImpl out;

    private final static Position DIRECTOR = new Position(1,"director");
    private final static Position ANALYST = new Position(2,"analyst");

    private static List<Employee> getListEmployeesForTest() {
        return List.of(
                new Employee(1, "Anna", 120000, DIRECTOR),
                new Employee(2, "Mary", 130000, ANALYST),
                new Employee(3, "Mikel", 140000, ANALYST)
        );
    }

    private static List<Position> getPositionForTest(){
        return List.of(
                new Position(1,"director", Set.of(
                        new Employee(1, "Anna", 120000, DIRECTOR))),
                new Position(2,"analyst", Set.of(
                        new Employee(2, "Mary", 130000, ANALYST),
                        new Employee(3, "Mikel", 140000, ANALYST)))
        );
    }

    @DisplayName("Добавление сотрудника в БД")
    @Test
    public void addEmployee_OK(){
        Employee expectedEmployee = new Employee(4,"Oleg", 100000, ANALYST);
        EmployeeDTO employeeDTO = out.addEmployee(EmployeeDTO.fromEmployee(expectedEmployee));
        Employee actualEmployee = employeeDTO.toEmployee();
        assertEquals(expectedEmployee, actualEmployee);
        verify(mockedRepository, times(1)).save(employeeDTO.toEmployee());
    }

    @DisplayName("Изменение сотрудника в БД")
    @Test
    public void editEmployee_OK(){
        Employee employee = new Employee(1,"Oleg", 100000, DIRECTOR);
        Employee expected = new Employee(1,"Anna", 200000, DIRECTOR);

        when(mockedRepository.findById(1))
                .thenReturn(Optional.of(employee));

        Employee actual = out.getEmployeeById(1).toEmployee();
        out.editEmployee(expected.getName(), expected.getSalary(), expected.getId());

        assertEquals(expected, actual);
    }


    @DisplayName("Поиск сотрудника в БД по id")
    @Test
    public void getEmployeeById_OK(){
        Employee expected = new Employee(1,"Oleg", 100000, DIRECTOR);

        when(mockedRepository.findById(1))
                .thenReturn(Optional.of(expected));

        Employee actual = out.getEmployeeById(1).toEmployee();
        assertEquals(expected, actual);

    }

    @DisplayName("Сотрудник в БД по id не найден")
    @Test
    public void getEmployeeById_Exception(){
        when(mockedRepository.findById(2)).thenThrow(EmployeeNotFoundException.class);
        assertThrows(EmployeeNotFoundException.class, () -> out.getEmployeeById(2));
    }

    @DisplayName("Удаление сотрудника в БД по id")
    @Test
    public void deleteEmployeeById_OK(){
        Employee employee = new Employee(1,"Oleg", 100000, DIRECTOR);

        when(mockedRepository.findById(1)).thenReturn(Optional.of(employee));
        out.deleteEmployeeById(1);
        verify(mockedRepository, times(1)).deleteById(1);
    }

    @DisplayName("Удаление сотрудника в БД по id невозможно")
    @Test
    public void deleteEmployeeById_Exception() {
        assertThrows(EmployeeNotFoundException.class, () -> out.deleteEmployeeById(2));
        verify(mockedRepository, times(0)).deleteById(2);

    }

    @DisplayName("Получение списка всех сотрудников")
    @Test
    public void getAllEmployees_OK(){

        when(mockedRepository.findAllEmployees()).thenReturn(getListEmployeesForTest());

        List<Employee> list = out.getAllEmployees().stream().
                map(EmployeeDTO::toEmployee)
                        .toList();
        assertIterableEquals(getListEmployeesForTest(), list);
    }

// В классе EmployeeServiceImpl данный метод возвращает EmployeeDTO.
// Нужно ли было как-то в тесте задействовать маппинг в DTO и обратно?

    @DisplayName("Получение сотрудника с самой высокой зарплатой")
    @Test
    public void getEmployeeWithHighestSalary_OK(){
        when(mockedRepository.getEmployeeWithHighestSalary())
                .thenReturn(getListEmployeesForTest().get(2));

        Employee expected = getListEmployeesForTest().stream()
                .max(Comparator.comparingInt(Employee::getSalary))
                .get();

        Employee actual = out.getEmployeeWithHighestSalary().toEmployee();

        assertEquals(expected, actual);
        verify(mockedRepository, times(1)).getEmployeeWithHighestSalary();

    }
// В классе EmployeeServiceImpl данный метод возвращает EmployeeDTO.
// Здесь решила использовать маппинг. Нужно ли было?
    @DisplayName("Получение списка сотрудников по должности, если должность null, то нужно вывести весь список сотрудников")
    @Test
    public  void getEmployeesByPosition_NULL_position(){
        Position position = new Position(null);
        when(mockedRepository.findAllEmployees())
                .thenReturn(getListEmployeesForTest());

        List<EmployeeDTO> expected = getListEmployeesForTest().stream()
                        .map(EmployeeDTO::fromEmployee)
                                .collect(Collectors.toList());

        List<EmployeeDTO> actual = out.getEmployeesByPosition(position.getNamePosition());

        assertIterableEquals(expected, actual);
    }

    @DisplayName("Получение списка сотрудников по должности")
    @Test
    public  void getEmployeesByPosition_OK(){

        List<EmployeeDTO> expected = getListEmployeesForTest().stream()
                        .filter(employee -> employee.getPosition() == DIRECTOR)
                        .map(EmployeeDTO::fromEmployee)
                                .collect(Collectors.toList());

        when(mockedRepository.getEmployeesByPosition(DIRECTOR.getNamePosition()))
                .thenReturn(List.of(
                        new Employee(1, "Anna", 120000, DIRECTOR)));

        List<EmployeeDTO> actual = out.getEmployeesByPosition(DIRECTOR.getNamePosition());
        assertIterableEquals(expected, actual);
    }

    @DisplayName("Получение полной информации о сотруднике по id")
    @Test
    public void getEmployeeFullInfoById_OK(){
        EmployeeFullInfo expected =  new EmployeeFullInfo("Anna", 120000, "director");
        when(mockedRepository.getEmployeeFullInfoById(1)).thenReturn(expected);

        assertEquals(expected, out.getEmployeeFullInfoById(1));
        verify(mockedRepository, times(1)).getEmployeeFullInfoById(1);
    }

    @DisplayName("Получение сотрудников постранично")
    @Test
    public void getEmployeesWithPaging_OK(){

        Pageable employeeOfConcretePage = PageRequest.of(0, 10);
        Page<Employee> employeePage = new PageImpl<>(getListEmployeesForTest());

        when(mockedRepository.findAll(employeeOfConcretePage))
                .thenReturn(employeePage);

        assertIterableEquals(getListEmployeesForTest(), out.getEmployeesWithPaging(0));
    }

    @DisplayName("Получение из файла списка сотрудников")
    @Test
    public void uploadFileWithEmployees_ReadEmployeesFromFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(getListEmployeesForTest());
        MockMultipartFile file = new MockMultipartFile("actualFile", "testEmployee.json", MediaType.MULTIPART_FORM_DATA_VALUE, json.getBytes());
        List<Employee> actual= objectMapper.readValue(file.getInputStream(), new TypeReference<>() {});
        assertEquals(getListEmployeesForTest(), actual);
    }

    @DisplayName("Добавление в базу данных из файла списка сотрудников")
    @Test
    public void uploadFileWithEmployees_AddEmployeesFromFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(getListEmployeesForTest());
        MockMultipartFile file = new MockMultipartFile("actualFile", "testEmployee.json", MediaType.MULTIPART_FORM_DATA_VALUE, json.getBytes());
        List<Employee> actual= objectMapper.readValue(file.getInputStream(), new TypeReference<>() {});

        out.uploadFileWithEmployees(file);
        verify(mockedRepository, times(1)).saveAll(actual);

    }

}