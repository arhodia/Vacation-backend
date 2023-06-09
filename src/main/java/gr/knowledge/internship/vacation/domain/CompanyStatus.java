package gr.knowledge.internship.vacation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyStatus {

        Long companyId;
        @Size(max=20)
        String status;
        LocalDate startDate;
        LocalDate endDate;

    }
