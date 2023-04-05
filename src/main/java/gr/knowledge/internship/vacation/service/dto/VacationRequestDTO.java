package gr.knowledge.internship.vacation.service.dto;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
public class VacationRequestDTO {

    Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VacationRequestDTO  vacrequestEmployee;

    LocalDate startDate;
    LocalDate endDate;
    @Size(max = 20)
    String status;
    Integer days;
}
