package ru.skypro.lessons.springboot.weblibrary.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;
import ru.skypro.lessons.springboot.weblibrary.model.Position;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PositionDTO implements Serializable {

    private int idPosition;
    private String namePosition;

    public PositionDTO(String namePosition) {
        this.namePosition = namePosition;
    }

    public static PositionDTO fromPosition(Position position) {
        PositionDTO positionDTO = new PositionDTO();
        positionDTO.setIdPosition(position.getIdPosition());
        positionDTO.setNamePosition(position.getNamePosition());
        return positionDTO;
    }

    public Position toPosition() {
        Position position = new Position();
        position.setIdPosition(this.getIdPosition());
        position.setNamePosition(this.getNamePosition());
        return position;
    }

    @Override
    public String toString() {
        return "PositionDTO{" +
                "idPosition=" + idPosition +
                ", namePosition='" + namePosition + '\'' +
                '}';
    }
}
