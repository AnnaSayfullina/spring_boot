package ru.skypro.lessons.springboot.weblibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.weblibrary.service.ReportService;

@RestController
@RequestMapping("report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/path")
    public int createReportWihPath(){
        return reportService.createReportWihPath();
    }

    @GetMapping(value = "/path/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> getReportByIdWihPath(@PathVariable int id) {
        Resource resource = reportService.getReportByIdWihPath(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employeeReport\"" + id + ".json\"")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(resource);
    }

}
