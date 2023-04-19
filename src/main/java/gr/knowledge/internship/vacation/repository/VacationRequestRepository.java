package gr.knowledge.internship.vacation.repository;
import gr.knowledge.internship.vacation.domain.VacationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long>, JpaSpecificationExecutor<VacationRequest>
{
    //query 4

    @Query("Select new gr.knowledge.internship.vacation.domain.CompanyStatus(c.id,vr.status, vr.startDate,vr.endDate ) from VacationRequest vr inner join Employee e on vr.vacrequestEmployee.id = e.id inner join Company c on e.employeeCompany.id=c.id where c.id = :companyId and vr.status = :status AND vr.startDate >= :startDate AND vr.endDate <= :endDate")
    List<VacationRequest> getCompanyStatus(@Param("companyId") Long companyId,@Param("status") String status,@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
