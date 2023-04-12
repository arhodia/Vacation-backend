package gr.knowledge.internship.vacation.repository;
import gr.knowledge.internship.vacation.domain.Company;
import gr.knowledge.internship.vacation.domain.MonthlyExpenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company>
{
/*Query 6 Calculate monthly expenses for Company
    @Query("SELECT new gr.knowledge.internship.vacation.domain.MonthlyExpenses( c.id,e.salary )from Company c inner join  Employee e on c.id=e.id where c.id =:companyId ")
    List<MonthlyExpenses> calculateMonthlyExpenses(@Param("companyId") Long companyId);*/



}
