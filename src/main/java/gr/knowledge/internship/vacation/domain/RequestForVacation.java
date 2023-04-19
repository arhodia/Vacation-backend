package gr.knowledge.internship.vacation.domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestForVacation {
    @Id
    LocalDate startDate;
    Long employeeId;
    LocalDate endDate;
    Integer holiday;

}
