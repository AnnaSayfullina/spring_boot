package ru.skypro.lessons.springboot.weblibrary.controller;

import io.github.classgraph.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.weblibrary.model.Report;
import ru.skypro.lessons.springboot.weblibrary.service.EmployeeService;
import ru.skypro.lessons.springboot.weblibrary.service.ReportService;

@RestController
@RequestMapping("report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final EmployeeService employeeService;

    @PostMapping("/")
    public int createReport(){
        return reportService.createReport();
    }
    @GetMapping("/{id}")
    public Report getReportById(@PathVariable int id) {
        return reportService.getReportById(id);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Resource> getReportById(@PathVariable int id) {
//        return reportService.getReportById(id);
//    }

}
