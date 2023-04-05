package gr.knowledge.internship.vacation.service.dto;
import gr.knowledge.internship.vacation.domain.Company;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
@Data
public class EmployeeProductDTO  {
    Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmployeeDTO employeeEmployee;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ProductDTO employeeProduct;
}
