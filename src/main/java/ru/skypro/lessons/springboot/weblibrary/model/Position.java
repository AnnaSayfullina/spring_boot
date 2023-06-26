package ru.skypro.lessons.springboot.weblibrary.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "position")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_position")
    private int idPosition;

    @Column(name = "name_position")
    private String namePosition;

    @OneToMany(mappedBy = "position", fetch = FetchType.EAGER)
    private Set<Employee> employees;



}
