package ru.skypro.lessons.springboot.weblibrary.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;
import ru.skypro.lessons.springboot.weblibrary.model.Position;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;
    @BeforeEach
    public void cleanData(){
        employeeRepository.deleteAll();
    }

    void addEmployeeListInRepository() {
        Position director = new Position(1,"director");
        Position analyst = new Position(2,"analyst");

        List<Employee> employeeList = List.of(
                new Employee( 1,"Anna", 120000, director),
                new Employee(2,"Mary", 130000, analyst),
                new Employee(3,"Mikel", 140000, analyst)
        );
        employeeRepository.saveAll(employeeList);
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
//    Обязательно нужно на вход падать какой-то id. Но должно работать без этого. Не понимаю в чем ошибка?

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
//Метод не работает
    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void editEmployeeTest() throws Exception {

        addEmployeeListInRepository();

//        JSONObject position = new JSONObject();
//        position.put("namePosition", "position_new");
//
//        JSONObject jsonEmployee = new JSONObject();
//        jsonEmployee.put("id", 1);
//        jsonEmployee.put("name", "Oleg");
//        jsonEmployee.put("salary", 10000);
//
//        jsonEmployee.put("positionDTO", position);

        mockMvc.perform(put("/employees/{id}",1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonEmployee.toString()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").isNotEmpty())
//                .andExpect(jsonPath("$.id").isNumber())
//                .andExpect(jsonPath("$.name").value("test_name"));
                        .param("id", String.valueOf(1))
                        .param("name", "Oleg")
                        .param("salary", String.valueOf(300000)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employees/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Oleg"));


    }


}
