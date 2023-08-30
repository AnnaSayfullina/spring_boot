package ru.skypro.lessons.springboot.weblibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skypro.lessons.springboot.weblibrary.model.Employee;
import ru.skypro.lessons.springboot.weblibrary.model.Position;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO implements Serializable {
    private Integer id;
    private String name;
    private Integer salary;
    private PositionDTO positionDTO;

    public EmployeeDTO(String name, Integer salary, PositionDTO positionDTO) {
        this.name = name;
        this.salary = salary;
        this.positionDTO = positionDTO;
    }

    public static EmployeeDTO fromEmployee(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSalary(employee.getSalary());
        employeeDTO.setPositionDTO(PositionDTO.fromPosition(employee.getPosition()));
        return employeeDTO;
    }

    public Employee toEmployee() {
        Employee employee = new Employee();
        employee.setId(this.getId());
        employee.setName(this.getName());
        employee.setSalary(this.getSalary());
        employee.setPosition(this.getPositionDTO().toPosition());
        return employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDTO that = (EmployeeDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(salary, that.salary) && Objects.equals(positionDTO, that.positionDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, salary, positionDTO);
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", positionDTO=" + positionDTO +
                '}';
    }
}
