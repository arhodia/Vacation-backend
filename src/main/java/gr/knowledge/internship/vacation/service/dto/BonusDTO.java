package gr.knowledge.internship.vacation.service.dto;
import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class BonusDTO implements Serializable
{
    Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CompanyDTO bonusCompany;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmployeeDTO bonusEmployee;

    Long amount;
}
