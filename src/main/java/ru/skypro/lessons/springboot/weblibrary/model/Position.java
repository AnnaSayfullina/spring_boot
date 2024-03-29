package ru.skypro.lessons.springboot.weblibrary.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "position")
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_position")
    private int idPosition;

    @Column(name = "name_position")
    private String namePosition;


    public Position(String namePosition) {
        this.namePosition = namePosition;
    }

    public Position(int idPosition, String namePosition) {
        this.idPosition = idPosition;
        this.namePosition = namePosition;
    }

}
