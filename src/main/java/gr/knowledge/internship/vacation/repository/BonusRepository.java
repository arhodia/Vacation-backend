package gr.knowledge.internship.vacation.repository;
import gr.knowledge.internship.vacation.domain.Bonus;
import gr.knowledge.internship.vacation.domain.BonusSeason;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface BonusRepository extends JpaRepository<Bonus, Long>, JpaSpecificationExecutor<Bonus> {

    @Query("Select new gr.knowledge.internship.demo.model.BestCustomer(" +
            "vr.start_date, vr.end_date,b.amount,e.name,e.surname, e.id " +
            "from vacation_request vr " +
            "inner join employee e on e.id=vr.employee_id" +
            " inner join bonus b on b.employee_id = e.id " +
            "where vr.start_date >= '2022-12-21' and vr.end_date <= '2023-03-21')")
    List<BonusSeason> getTopCustomer(PageRequest pageable);



}