package ru.skypro.lessons.springboot.weblibrary.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.classgraph.Resource;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.weblibrary.model.Report;
import ru.skypro.lessons.springboot.weblibrary.repository.ReportRepository;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{

    private  final ReportRepository reportRepository;

//
//    @Override
//    public ReportDTO getReportById(int id) {
//        ReportDTO reportDTO = ReportDTO.fromReport(reportRepository.findById(id)
//                .orElseThrow(ReportNotFoundException::new));
//
//        return  reportDTO;
//    }


    @Override
    public int createReport(){

        ObjectMapper objectMapper = new ObjectMapper();
        byte[] json = null;
        try {
            json = objectMapper.writeValueAsString(reportRepository.createReport()).getBytes();
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Отчет не сформирован", e);
        }

        Report report = new Report();
        report.setReport(json);
        return reportRepository.save(report).getIdReport();

    }

//    @Override
//    public Report getReportById(int id) {
//        Report report = reportRepository.findById(id)
//                .orElseThrow(ReportNotFoundException::new)
//        ;
//        return report;
//    }

        @Override
    public ResponseEntity<Resource> getReportById(int id) {
        String fileName = "employeeReport.json";
// почему здесь подчеркивает красным?
        Resource resource = new ByteArrayResource(reportRepository.findById(id).get().getReport());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + id + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(resource);

    }
}
