package gr.knowledge.internship.vacation.repository;
import gr.knowledge.internship.vacation.domain.VacationRequest;
import gr.knowledge.internship.vacation.domain.CompanyStatus;
import gr.knowledge.internship.vacation.service.dto.VacationRequestDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long>, JpaSpecificationExecutor<VacationRequest>
{
    @Query("Select new gr.knowledge.internship.vacation.domain.CompanyStatus( c.id,vr.status,vr.startDate,vr.endDate) from VacationRequest vr join Employee e on vr.id = e.id join Company c on e.id=c.id where c.id = :companyId and vr.status = :status AND vr.startDate >= :startDate AND vr.endDate <= :endDate")
    List<VacationRequestDTO> getCompanyStatus(@Param("companyId") Long companyId,@Param("status") String status,@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    //on vr.id = e.id and on e.id=c.id
}
