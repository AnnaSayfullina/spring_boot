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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

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

    @Override
    public int createReportWihPath() {

        ObjectMapper objectMapper = new ObjectMapper();
        String file = null;
        String fileName = "file.json";
        Path path = Paths.get(fileName);
        try {
            file = objectMapper.writeValueAsString(reportRepository.createReport());
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Report report = new Report();
        report.setReport(file);
        report.setPath(path.toString());
        return reportRepository.save(report).getIdReport();
    }

    @Override
    public ResponseEntity<Resource> getReportByIdWihPath(int id) {

        Path path = Paths.get(reportRepository.findById(id)
                        .orElseThrow(ReportNotFoundException::new)
                                .getPath());
        try {

            String file = Files.lines(path)
                    .collect(Collectors.joining());
            String fileName = "employeeReport";
            Resource resource = new ByteArrayResource(file.getBytes(StandardCharsets.UTF_8));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + id + ".json\"")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(resource);
        } catch (IOException ioException) {
            ioException.getCause();
            return null;
        }
    }
}
