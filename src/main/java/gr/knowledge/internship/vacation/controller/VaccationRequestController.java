package gr.knowledge.internship.vacation.controller;
import gr.knowledge.internship.vacation.service.VacationRequestService;
import gr.knowledge.internship.vacation.service.dto.VacationRequestDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
@Transactional
@Log4j2
@RequestMapping("/api")
public class VaccationRequestController {


    private final VacationRequestService vacationRequestService;
    public VaccationRequestController(VacationRequestService  vacationRequestService)
    {
        this.vacationRequestService= vacationRequestService;
    }
    private static final String NotFoundExceptionMessage = "Not Found";

    @PostMapping("/vacationRequest")
    public ResponseEntity<VacationRequestDTO> save(@RequestBody VacationRequestDTO vacationRequestDTO)
    {
        log.debug("Rest request to save Company : {}",vacationRequestDTO);
        VacationRequestDTO result = vacationRequestService.save(vacationRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/vacationRequest/{id}")
    public ResponseEntity<VacationRequestDTO> getvacationRequest(@PathVariable Long id)
    {
        log.debug("Rest request to get vacationRequest by id : {}",id);
        VacationRequestDTO result = vacationRequestService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/allvacationRequest")
    public ResponseEntity<List<VacationRequestDTO>> getAllVacationRequest()
    {
        log.debug("Rest request to get All VacationRequest  : {}");
        List<VacationRequestDTO> result = vacationRequestService.getAllVacationRequest();
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/updateVacationRequest/{id}")
    public ResponseEntity<VacationRequestDTO> updateVacationRequest(@PathVariable Long id, @RequestBody VacationRequestDTO vacationRequestDTO)
    {
        log.debug("Rest request to save Company : {}",vacationRequestDTO);
        VacationRequestDTO result = vacationRequestService.updateVacationRequest(id,vacationRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteVacationRequestById/{id}")
    public String deleteVacationRequest(@PathVariable("id") Long id)
    {
        vacationRequestService.delete(id);
        return "Deleted Successfully";

    }

}
