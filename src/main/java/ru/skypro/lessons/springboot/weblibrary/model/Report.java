package ru.skypro.lessons.springboot.weblibrary.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "report")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Report {
    @Id
    @Column(name = "id_report")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReport;

    @Lob
    @Column(name = "report", columnDefinition = "text")
    private String report;

    @Column(name = "path")
    private String path;

}
