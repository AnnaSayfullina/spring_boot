package ru.skypro.lessons.springboot.weblibrary.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.weblibrary.exceptions.ReportNotFoundException;
import ru.skypro.lessons.springboot.weblibrary.model.Report;
import ru.skypro.lessons.springboot.weblibrary.repository.ReportRepository;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{

    private  final ReportRepository reportRepository;

    @Override
    public int createReport(){

        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(reportRepository.createReport());
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Отчет не сформирован", e);
        }

        Report report = new Report();
        report.setReport(json);
        return reportRepository.save(report).getIdReport();

    }


        @Override
    public ResponseEntity<Resource> getReportById(int id) {
        String fileName = "employeeReport";
        Resource resource =
                new ByteArrayResource(reportRepository.findById(id)
                        .orElseThrow(ReportNotFoundException::new)
                        .getReport()
                        .getBytes(StandardCharsets.UTF_8));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + id + ".json\"")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(resource);

    }
}
