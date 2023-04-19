package gr.knowledge.internship.vacation.controller;
import gr.knowledge.internship.vacation.domain.RequestForVacation;
import gr.knowledge.internship.vacation.service.VacationRequestService;
import gr.knowledge.internship.vacation.domain.VacationRequestStatus;
import gr.knowledge.internship.vacation.service.dto.VacationRequestDTO;
import gr.knowledge.internship.vacation.domain.CompanyStatus;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.websocket.server.PathParam;
import java.util.List;


@Service
@Transactional
@Log4j2
@RequestMapping("/api")
public class VaccationRequestController
{

    private final VacationRequestService vacationRequestService;

    private CompanyStatus companyStatus;
    private VacationRequestStatus vacationRequestStatus;
    public VaccationRequestController(VacationRequestService  vacationRequestService)
    {
        this.vacationRequestService= vacationRequestService;
        this.companyStatus=companyStatus;
    }
    private static final String NotFoundExceptionMessage = "Not Found";


    //CREATE
    @CrossOrigin
    @PostMapping("/vacationRequest")
    public ResponseEntity<VacationRequestDTO> save(@RequestBody VacationRequestDTO vacationRequestDTO)
    {
        log.debug("Rest request to save Company : {}",vacationRequestDTO);
        VacationRequestDTO result = vacationRequestService.save(vacationRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


    //RETURN VACATION REQUEST BY ID
    @CrossOrigin
    @GetMapping("/vacationRequest/{id}")
    public ResponseEntity<VacationRequestDTO> getvacationRequest(@PathVariable Long id)
    {
        log.debug("Rest request to get vacationRequest by id : {}",id);
        VacationRequestDTO result = vacationRequestService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //RETURN VACATION REQUEST
    @CrossOrigin
    @GetMapping("/allvacationRequest")
    public ResponseEntity<List<VacationRequestDTO>> getAllVacationRequest()
    {
        log.debug("Rest request to get All VacationRequest  : {}");
        List<VacationRequestDTO> result = vacationRequestService.getAllVacationRequest();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //UPDATE
    @CrossOrigin
    @PutMapping("/updateVacationRequest/{id}")
    public ResponseEntity<VacationRequestDTO> updateVacationRequest(@PathVariable Long id, @RequestBody VacationRequestDTO vacationRequestDTO)
    {
        log.debug("Rest request to save Company : {}",vacationRequestDTO);
        VacationRequestDTO result = vacationRequestService.updateVacationRequest(id,vacationRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


    //DELETE
    @CrossOrigin
    @DeleteMapping("/deleteVacationRequestById/{id}")
    public String deleteVacationRequest(@PathVariable("id") Long id)
    {
        vacationRequestService.delete(id);
        return "Deleted Successfully";
    }

    //Query 2 Vacation Request
    //{"startDate" : "2022-04-01","employeeId" : 1,"endDate":"2022-04-05","holiday" : 2}//
    @PostMapping("/requestForVacation")
    public ResponseEntity<VacationRequestDTO> requestForVacation(@RequestBody RequestForVacation requestForVacation)
    {
        log.debug("Rest request to get vacation request: {}");
        VacationRequestDTO result = vacationRequestService.requestForVacation(requestForVacation);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //Query 4 Vacation Requests by Company
    /*{
    "companyId" : 1,
    "status": "PENDING",
    "startDate" : "2022-04-01" ,
    "endDate" : "2022-04-05"
    }*/
    @CrossOrigin
    @PostMapping ("/vacationRequestByCompany")
    public List<VacationRequestDTO> getvacationRequestByCompany(@RequestBody CompanyStatus companyStatus)
    {
        return vacationRequestService.vacationRequestByCompany(companyStatus);
    }

    //Query 5 Accept or Reject Vacation Request
    /*{
    "vacationId":1,
    "status" : "accepted"
    }*/
    @CrossOrigin
    @PutMapping("/updateVacationRequestStatus/{vacationId}")
    public ResponseEntity<VacationRequestDTO> updateVacationRequestStatus(@PathParam("vacationId") Long vacationId,@RequestBody VacationRequestStatus vacationRequestStatus)
    {
        log.debug("Rest request to save Company : {}");
        VacationRequestDTO result = vacationRequestService.updateVacationRequestStatus(vacationRequestStatus );
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
