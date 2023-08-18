package ru.skypro.lessons.springboot.weblibrary.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;
import ru.skypro.lessons.springboot.weblibrary.model.Position;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.weblibrary.repository.ReportRepository;
import ru.skypro.lessons.springboot.weblibrary.service.ReportService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private ReportService reportService;

    @BeforeEach
    public void cleanData(){
        employeeRepository.deleteAll();
        reportRepository.deleteAll();
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

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void createReportWihPathTest() throws Exception {

        addEmployeeListInRepository();
        mockMvc.perform(post("/report/path"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void getReportByIdWihPathTest() throws Exception {

        addEmployeeListInRepository();
        int id = reportService.createReportWihPath();

        MvcResult result =mockMvc.perform(get("/report/path/{id}", id))
                .andExpect(status().isOk())
                .andReturn();
        byte[] resourceContent = result.getResponse().getContentAsByteArray();
        assertThat(resourceContent).isNotEmpty();
    }

}
