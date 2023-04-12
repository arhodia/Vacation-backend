package gr.knowledge.internship.vacation.repository;

import gr.knowledge.internship.vacation.domain.Employee;
import gr.knowledge.internship.vacation.domain.MonthlyExpenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee>
{

    //Query 6 Calculate monthly expenses for Company
    @Query("SELECT e.id,e.name, e.surName from  Employee e  inner join Company c on e.id=c.id where c.id =:companyId ")
    List<Employee> getEmployeeForEachCompany (@Param("companyId") Long companyId);

}

