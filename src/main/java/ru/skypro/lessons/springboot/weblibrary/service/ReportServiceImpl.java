package ru.skypro.lessons.springboot.weblibrary.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.weblibrary.exceptions.EmployeeNotFoundException;
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
public class ReportServiceImpl implements ReportService{

    private  final ReportRepository reportRepository;

    Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public int createReportWihPath() {

        logger.info("Вызван метод для создания отчета");

        ObjectMapper objectMapper = new ObjectMapper();
        String file = null;
        String fileName = "file.json";
        Path path = Paths.get(fileName);
        try {
            file = objectMapper.writeValueAsString(reportRepository.createReport());
            Files.write(path, file.getBytes());
        } catch (IOException e) {

            logger.error("Отчет не сформирован", e);
            throw new RuntimeException(e);
        }
        Report report = new Report();
        report.setPath(path.toString());
        int id = reportRepository.save(report).getIdReport();

        logger.debug("Сформирован и записан в базу данных отчет с id = {} ", id);
        return id;
    }

    @Override
    public Resource getReportByIdWihPath(int id) {

        logger.info("Вызван метод для получения отчета по id = {}", id);

        Path path = Paths.get(reportRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Отчет с id = {} не найден", id);
                    return new ReportNotFoundException();
                })
                .getPath());
        try {
            byte[] bytes = Files.readAllBytes(path);
            Resource resource = new ByteArrayResource(bytes);
            return resource;
        } catch (IOException ioException) {
            ioException.getCause();
            return null;
        }
    }
}
