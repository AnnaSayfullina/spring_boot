package ru.skypro.lessons.springboot.weblibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;
import ru.skypro.lessons.springboot.weblibrary.model.Report;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ReportDTO implements Serializable {


    private String namePosition;
    private long countOfEmployee;
    private int maxSalary;
    private int minSalary;
    private double averageSalary;

//    public static ReportDTO fromReport(Report report) {
//
//        ReportDTO reportDTO = new ReportDTO();
//        reportDTO.setIdReport(report.getIdReport());
//        reportDTO.setReport(report.getReport());
//
//        return reportDTO;
//    }
//
//    public Report toReport() {
//
//        Report report = new Report();
//        report.setIdReport(this.getIdReport());
//        report.setReport(this.getReport());
//
//        return report;
//    }

}


