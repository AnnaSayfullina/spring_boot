package ru.skypro.lessons.springboot.weblibrary.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.weblibrary.dto.ReportDTO;
import ru.skypro.lessons.springboot.weblibrary.model.Report;

import java.util.List;

@Repository
public interface ReportRepository extends CrudRepository<Report, Integer> {

    @Query ("SELECT new ru.skypro.lessons.springboot.weblibrary.dto.ReportDTO(p.namePosition, count(e.id), max(e.salary), min(e.salary), avg(e.salary)) FROM Employee e join fetch Position p WHERE e.position=p GROUP BY p.namePosition")
    List<ReportDTO> createReport();
}
