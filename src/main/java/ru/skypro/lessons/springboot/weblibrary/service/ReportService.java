package ru.skypro.lessons.springboot.weblibrary.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ReportService {

    int createReportWihPath();
    Resource getReportByIdWihPath(int id);

}
