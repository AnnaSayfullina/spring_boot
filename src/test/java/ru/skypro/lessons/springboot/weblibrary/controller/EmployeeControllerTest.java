package ru.skypro.lessons.springboot.weblibrary.controller;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.PositionDTO;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;
import ru.skypro.lessons.springboot.weblibrary.model.Position;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class EmployeeControllerTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13")
            .withUsername("postgres")
            .withPassword("Anna_098!");

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    public void cleanData(){
        employeeRepository.deleteAll();
    }

    @Test
    void testPostgresql() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            assertThat(conn).isNotNull();
        }
    }

    void addEmployeeListInRepository() {
        Position director = new Position("director");
        Position analyst = new Position("analyst");

        List<Employee> employeeList = List.of(
                new Employee( "Anna", 120000, director),
                new Employee("Mary", 130000, analyst),
                new Employee("Mikel", 140000, analyst)
        );
        employeeRepository.saveAll(employeeList);
    }
    Employee addEmployeeInRepository(){
        Employee employee = new Employee("Ivan", 100000, new Position("java"));
        return employeeRepository.save(employee);
    }

    List<Employee> getEmployees() {
        return Stream.generate(() ->
                        new Employee("Kirill", 12000, new Position("java")))
                .limit(13)
                .toList();
    }



    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void getAllEmployeesTest() throws Exception {
        mockMvc.perform(get("/employees/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        addEmployeeListInRepository();

        mockMvc.perform(get("/employees/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }

//    Почему тест падает, если убираю строку
//    jsonEmployee.put("id", 0);
//    Обязательно нужно на вход подать какой-то id. Но должно работать без этого. Не понимаю в чем ошибка?

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void addEmployeeTest() throws Exception {
        JSONObject position = new JSONObject();
        position.put("namePosition", "position_new");

        JSONObject jsonEmployee = new JSONObject();
        jsonEmployee.put("id", 0);
        jsonEmployee.put("name", "test_name");
        jsonEmployee.put("salary", 10000);

        jsonEmployee.put("positionDTO", position);

        mockMvc.perform(post("/employees/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonEmployee.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("test_name"));


        mockMvc.perform(get("/employees/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("test_name"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void editEmployeeTest() throws Exception {

//        int id = addEmployeeInRepository().getId();
//        JSONObject position = new JSONObject();
//        position.put("namePosition", "position_new");
//
//        JSONObject jsonEmployee = new JSONObject();
//        jsonEmployee.put("id", 0);
//        jsonEmployee.put("name", "Oleg");
//        jsonEmployee.put("salary", 10000);
//
//        jsonEmployee.put("positionDTO", position);

        addEmployeeListInRepository();

        int idUpdate = employeeRepository.findIdByName("Anna");
        mockMvc.perform(put("/employees/{id}", idUpdate)
                  .param("name", "Oleg")
                  .param("salary", "300000"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/{id}", idUpdate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Oleg"));

        int anotherId = 150;

        mockMvc.perform(put("/employees/{id}", anotherId)
                        .param("name", "Oleg")
                        .param("salary", "300000"))
                .andExpect(status().isNotFound());

    }
    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void getEmployeeByIdTest() throws Exception {

        int id = addEmployeeInRepository().getId();

        mockMvc.perform(get("/employees/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ivan"));

        int anotherId = 153;

        mockMvc.perform(get("/employees/{id}", anotherId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void deleteEmployeeByIdTest() throws Exception {

        addEmployeeListInRepository();

        int idUpdate = employeeRepository.findIdByName("Anna");

        mockMvc.perform(delete("/employees/{id}", idUpdate))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));

        int anotherId = 154;

        mockMvc.perform(delete("/employees/{id}", anotherId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void getEmployeeWithHighestSalaryTest() throws Exception {

        addEmployeeListInRepository();

        int id = employeeRepository.findIdByName("Mikel");

        mockMvc.perform(get("/employees/withHighestSalary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Mikel"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void getEmployeesByPositionTest() throws Exception {

        addEmployeeListInRepository();

        mockMvc.perform(get("/employees/position")
                        .param("position", "analyst"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));

    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void getEmployeeFullInfoByIdTest () throws Exception {

        addEmployeeListInRepository();

        int id = employeeRepository.findIdByName("Mikel");

        mockMvc.perform(get("/employees/{id}/fullInfo", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mikel"))
                .andExpect(jsonPath("$.positionName").value("analyst"));

    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void getEmployeesWithPagingTest_whenThereIsPage() throws Exception {


        List<Employee> employeeList = getEmployees();
        employeeRepository.saveAll(employeeList);

        mockMvc.perform(get("/employees/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(13));

        mockMvc.perform(get("/employees/page")
                        .param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));

    }
    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void getEmployeesWithPagingTest_NoPage() throws Exception {

        List<Employee> employeeList = getEmployees();
        employeeRepository.saveAll(employeeList);

        mockMvc.perform(get("/employees/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(13));

        mockMvc.perform(get("/employees/page"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(10));

    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void uploadFileWithEmployeesTest() throws Exception {

        mockMvc.perform(get("/employees/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        List<EmployeeDTO> employeeDTOList = Stream.generate(() ->
                        new EmployeeDTO(0,"Kirill", 12000, new PositionDTO(0,"java")))
                .limit(5)
                .toList();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(employeeDTOList);
        MockMultipartFile file = new MockMultipartFile("file", json.getBytes());

        mockMvc.perform(multipart("/employees/upload")
                        .file(file))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(5));

    }
}
