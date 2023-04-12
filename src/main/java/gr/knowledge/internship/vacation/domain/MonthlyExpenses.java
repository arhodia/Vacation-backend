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
public class MonthlyExpenses {
    @Id
    @NotNull
    Long companyID;

    Double Salary;

}
