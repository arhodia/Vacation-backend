package gr.knowledge.internship.vacation.service.dto;

import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;


import java.time.LocalDate;

@Data
public class CompanyStatusDTO {
    @Id
    @NotNull
    Long companyId;
    @NotNull
    String status;
    @NotNull
    LocalDate startDate;
    @NotNull
    LocalDate endDate;
}
