package gr.knowledge.internship.vacation.domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacationRequestStatus {
    @Id
    @NotNull
    Long vacationId;

    String status;

}
