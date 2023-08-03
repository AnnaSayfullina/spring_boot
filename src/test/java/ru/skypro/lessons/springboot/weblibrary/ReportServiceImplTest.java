package ru.skypro.lessons.springboot.weblibrary;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import ru.skypro.lessons.springboot.weblibrary.dto.ReportDTO;
import ru.skypro.lessons.springboot.weblibrary.exceptions.ReportNotFoundException;
import ru.skypro.lessons.springboot.weblibrary.model.Report;
import ru.skypro.lessons.springboot.weblibrary.repository.ReportRepository;
import ru.skypro.lessons.springboot.weblibrary.service.ReportServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTest {

    @Mock
    private ReportRepository mockedReportRepository;

    @InjectMocks
    private ReportServiceImpl out;

    private static List<ReportDTO> getListReportForTest() {
        return List.of(
                new ReportDTO("director", 1, 120000, 120000, 120000),
                new ReportDTO("analyst", 2, 140000, 130000, 135000)
        );
    }

    private final static Report REPORT = new Report(1,"path");

//    Нужно ли было здесь разделить на 2 теста = создание отчета и добавление в базу?
    @DisplayName("Создание и добавление в БД отчета")
    @Test
    public void createReportWihPath_Ok() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        when(mockedReportRepository.createReport())
                .thenReturn(getListReportForTest());
        String file = objectMapper.writeValueAsString(mockedReportRepository.createReport());
        String fileName = "file.json";
        Path path = Paths.get(fileName);
        Files.write(path, file.getBytes());
        Report report = new Report();
        report.setPath(path.toString());

        mockedReportRepository.save(report);

        verify(mockedReportRepository, times(1)).save(report);
    }

//    Верно ли переделаны метод?
    @DisplayName("Получения отчета по id")
    @Test
    public void getReportByIdWihPath_Ok() {

        Report expected = REPORT;

        when(mockedReportRepository.findById(1))
                .thenReturn(Optional.of(expected));

        out.getReportByIdWihPath(1);
        verify(mockedReportRepository, times(1)).findById(1);
    }

    @DisplayName("Отчета по id не найден")
    @Test
    public void getReportByIdWihPath_Exception(){
        when(mockedReportRepository.findById(1)).thenThrow(ReportNotFoundException.class);
        assertThrows(ReportNotFoundException.class, () -> out.getReportByIdWihPath(1));

    }
}
