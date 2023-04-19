package gr.knowledge.internship.vacation.controller;
import gr.knowledge.internship.vacation.service.BonusService;
import gr.knowledge.internship.vacation.service.dto.BonusDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api")
public class BonusController {
    private BonusService bonusService;

    public BonusController(BonusService bonusService)
    {
        this.bonusService = bonusService;
    }

    //CRUD: POST
    @CrossOrigin
    @PostMapping("/bonus")
    public ResponseEntity<BonusDTO> save(@RequestBody BonusDTO bonusDTO )
    {
        log.debug("Rest request to save Company : {}",bonusDTO);
        BonusDTO result = bonusService.save(bonusDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    //CRUD:GET BY ID
    @CrossOrigin
    @GetMapping("/bonus/{id}")
    public ResponseEntity<BonusDTO> getBonus(@PathVariable Long id)
    {
        log.debug("Rest request to get Company by id : {}",id);
        BonusDTO result = bonusService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    //CRUD : GET ALL
    @CrossOrigin
    @GetMapping("/allbonus")
    public ResponseEntity<List<BonusDTO>> getAllEmployee()
    {
        log.debug("Rest request to get All Employee  : {}");
        List<BonusDTO> result = bonusService.getAllBonus();
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    //CRUD : UPDATE
    @CrossOrigin
    @PutMapping("/updatebonus/{id}")
    public ResponseEntity<BonusDTO> updateBonus(@PathVariable Long id, @RequestBody BonusDTO bonusDTO)
    {
        log.debug("Rest request to save Company : {}",bonusDTO);
        BonusDTO result =  bonusService.updateBonus(id,bonusDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    //CRUD : DELETE

    @CrossOrigin
    @DeleteMapping("/deletebonusById/{id}")
    public String deletebonus(@PathVariable("id") Long id)
    {
        bonusService.delete(id);
        return "Deleted Successfully";

    }
    //QUERY 3 :Get calculation of bonus
    @CrossOrigin
    @GetMapping("/getCalculationOfBonus")
    public ResponseEntity<Double> getCalculationOfBonus(@RequestParam Double salary,@RequestParam String season)
    {
        log.debug("Rest request to get All CalculationOfBonus  : {}");
        Double result = bonusService.getCalculationOfBonus(salary,season);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


    //QUERY 7 Create Bonuses for a Company
    //POSTMAN : getBonusesForCompany?companyId=1&season=SPRING
    @CrossOrigin
    @GetMapping("/getBonusesForCompany")
    public ResponseEntity<List<BonusDTO>> getBonusesForCompany(@RequestParam Long companyId,@RequestParam String season)
    {
        log.debug("Rest request to get All Employee  : {}");
        List<BonusDTO> result = bonusService.getBonusesForCompany(companyId,season);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
       // getBonusesForCompany(Long companyId,String season)
    }
}
