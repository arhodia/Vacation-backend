package gr.knowledge.internship.vacation.service.dto;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class VacationRequestDTO implements Serializable{

    Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmployeeDTO  vacrequestEmployee;

    LocalDate startDate;
    LocalDate endDate;
    @Size(max = 20)
    String status;
    Integer days;
}
