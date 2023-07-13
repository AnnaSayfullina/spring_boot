package ru.skypro.lessons.springboot.weblibrary.service;

import io.github.classgraph.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.weblibrary.dto.ReportDTO;
import ru.skypro.lessons.springboot.weblibrary.model.Report;

@Service
public interface ReportService {

//    ReportDTO getReportById(int id);
    int createReport();
    ResponseEntity<Resource> getReportById(int id);

}
