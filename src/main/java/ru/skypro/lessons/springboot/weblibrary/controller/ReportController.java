package ru.skypro.lessons.springboot.weblibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.weblibrary.service.ReportService;

@RestController
@RequestMapping("report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    @PostMapping("/")
    public int createReport(){
        return reportService.createReport();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> getReportById(@PathVariable int id) {
        return reportService.getReportById(id);
    }

}
