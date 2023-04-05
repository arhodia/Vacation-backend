package gr.knowledge.internship.vacation.controller;
import gr.knowledge.internship.vacation.service.EmployeeProductService;
import gr.knowledge.internship.vacation.service.dto.EmployeeProductDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api")
public class EmployeeProductController {
    private EmployeeProductService employeeProductService;

    public EmployeeProductController(EmployeeProductService employeeProductService)
    {
        this.employeeProductService = employeeProductService;
    }

    @PostMapping("/employeeProduct")
    public ResponseEntity<EmployeeProductDTO> save(@RequestBody EmployeeProductDTO employeeProductDTO)
    {
        log.debug("Rest request to save EmployeeProduct : {}",employeeProductDTO);
        EmployeeProductDTO result = employeeProductService.save(employeeProductDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/employeeProduct/{id}")
    public ResponseEntity<EmployeeProductDTO> getEmployeeProduct(@PathVariable Long id)
    {
        log.debug("Rest request to get Company by id : {}",id);
        EmployeeProductDTO result = employeeProductService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/allEmployeeProduct")
    public ResponseEntity<List<EmployeeProductDTO>> getAllEmployeeProduct()
    {
        log.debug("Rest request to get All Employee  : {}");
        List<EmployeeProductDTO> result =  employeeProductService.getAllEmployeeProduct();
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/updateEmployeeProduct/{id}")
    public ResponseEntity<EmployeeProductDTO> updateEmployeeProduct(@PathVariable Long id, @RequestBody EmployeeProductDTO employeeProductDTO)
    {
        log.debug("Rest request to save Company : {}",employeeProductDTO);
        EmployeeProductDTO result =employeeProductService.updateEmployeeProduct(id,employeeProductDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteEmployeeProductById/{id}")
    public String deleteCompany(@PathVariable("id") Long id)
    {
        employeeProductService.delete(id);
        return "Deleted Successfully";

    }


}
