package gr.knowledge.internship.vacation.controller;

import gr.knowledge.internship.vacation.service.CompanyService;
import gr.knowledge.internship.vacation.service.dto.CompanyDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api")
public class CompanyController {
    private CompanyService companyService;

    public CompanyController(CompanyService companyService)
    {
        this.companyService = companyService;
    }

    @CrossOrigin
    @PostMapping("/company")
    public ResponseEntity<CompanyDTO> save(@RequestBody CompanyDTO companyDTO)
    {
        log.debug("Rest request to save Company : {}",companyDTO);
        CompanyDTO result = companyService.save(companyDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping("/company/{id}")
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable Long id)
    {
        log.debug("Rest request to get Company by id : {}",id);
        CompanyDTO result = companyService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @CrossOrigin
    @GetMapping("/allcompany")
    public ResponseEntity<List<CompanyDTO>> getAllEmployee()
    {
        log.debug("Rest request to get All Employee  : {}");
        List<CompanyDTO> result = companyService.getAllCompany();
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    @CrossOrigin
    @PutMapping("/updatecompany/{id}")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable Long id, @RequestBody CompanyDTO companyDTO)
    {
        log.debug("Rest request to save Company : {}",companyDTO);
        CompanyDTO result = companyService.updateCompany(id,companyDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    @CrossOrigin
    @DeleteMapping("/deleteCompanyById/{id}")
    public String deleteCompany(@PathVariable("id") Long id)
    {
        companyService.delete(id);
        return "Deleted Successfully";

    }

}
