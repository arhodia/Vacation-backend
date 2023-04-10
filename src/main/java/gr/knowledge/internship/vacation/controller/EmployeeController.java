package gr.knowledge.internship.vacation.controller;

import gr.knowledge.internship.vacation.service.EmployeeService;
import gr.knowledge.internship.vacation.service.dto.EmployeeDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService)
    {

        this.employeeService = employeeService;
    }
    @CrossOrigin
    @PostMapping("/employee")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO)
    {
        log.debug("Rest request to save Employee : {}",employeeDTO);
        EmployeeDTO result = employeeService.save(employeeDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    @CrossOrigin
    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long id)
    {
        log.debug("Rest request to get Employee by id : {}",id);
        EmployeeDTO result = employeeService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/allEmployee")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployee()
    {
        log.debug("Rest request to get All Employee  : {}");
        List<EmployeeDTO> result = employeeService.getAllEmployee();
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    @CrossOrigin
    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<EmployeeDTO> updateProducts(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO)
    {
        log.debug("Rest request to save Employee : {}",employeeDTO);
         EmployeeDTO result =employeeService.updateEmployee(id,employeeDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    @CrossOrigin
    @DeleteMapping("/deleteEmployeeById/{id}")
    public String deleteProducts(@PathVariable("id") Long id)
    {
        employeeService.delete(id);
        return "Deleted Successfully";

    }

}