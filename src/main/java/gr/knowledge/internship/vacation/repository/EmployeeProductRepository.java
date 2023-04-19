package gr.knowledge.internship.vacation.repository;
import gr.knowledge.internship.vacation.domain.AllProductsForACompany;
import gr.knowledge.internship.vacation.domain.EmployeeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmployeeProductRepository extends JpaRepository<EmployeeProduct, Long>, JpaSpecificationExecutor<EmployeeProduct>{
    //Query 8 Get All products for a Company
    @Query("SELECT new gr.knowledge.internship.vacation.domain.AllProductsForACompany(e.id,e.name,e.surName,p.id,p.name,p.barcode,p.description )from EmployeeProduct ep inner join Employee e on ep.employeeEmployee.id = e.id inner join Product p on p.id = ep.employeeProduct.id where e.employeeCompany.id =:companyId ")
    List<AllProductsForACompany> GetAllProductsForCompany (@Param("companyId") Long companyId);

}
