package gr.knowledge.internship.vacation.service;
import gr.knowledge.internship.vacation.domain.*;
import gr.knowledge.internship.vacation.domain.Employee;
import gr.knowledge.internship.vacation.domain.VacationRequest;
import gr.knowledge.internship.vacation.enums.VacationStatus;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.EmployeeRepository;
import gr.knowledge.internship.vacation.service.mapper.EmployeeMapper;
import gr.knowledge.internship.vacation.service.dto.EmployeeDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import gr.knowledge.internship.vacation.repository.VacationRequestRepository;
import gr.knowledge.internship.vacation.service.dto.VacationRequestDTO;
import gr.knowledge.internship.vacation.service.mapper.VacationRequestMapper;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Component
@Service
@Transactional
@Log4j2
public class VacationRequestService
{
    @Autowired
    private final VacationRequestRepository vacationRequestRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private VacationRequestMapper vacationRequestMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    private Employee employee;
    private VacationRequest vacationRequest;

    @Autowired
    private static final String NotFoundExceptionMessage = "Not Found";

    private RequestVacation requestVacation;
    public VacationRequestService(VacationRequestRepository vacationRequestRepository, VacationRequestMapper vacationRequestMapper,EmployeeRepository employeeRepository)
    {
        this. vacationRequestRepository = vacationRequestRepository;
        this.vacationRequestMapper = vacationRequestMapper;
        this.requestVacation = requestVacation;
        this.employeeRepository = employeeRepository;

    }

    @Transactional
    public VacationRequestDTO save(VacationRequestDTO vacationRequestDTO){
        log.debug("Request to save Company : {}",vacationRequestDTO);
        VacationRequest vacationRequest = vacationRequestMapper.toEntity(vacationRequestDTO);
        vacationRequest =  vacationRequestRepository.save(vacationRequest);
        return vacationRequestMapper.toDto(vacationRequest);
    }

    @Transactional(readOnly = true)
    public VacationRequestDTO  getById(Long id)
    {
        VacationRequestDTO result;
        log.debug("Request to get VacationRequest by id : {}",id);
        Optional<VacationRequest> vacationRequest = vacationRequestRepository.findById(id);
        if(vacationRequest.isPresent()){
            result = vacationRequestMapper.toDto(vacationRequest.get());
        }else {
            throw new NotFoundException(NotFoundExceptionMessage);

        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<VacationRequestDTO> getAllVacationRequest()
    {
        log.debug("Request to get all AllVacationRequest.");
        List<VacationRequest> vacationRequest = vacationRequestRepository.findAll();
        List<VacationRequestDTO> vacationRequestDTOs = new ArrayList<>();
        for(VacationRequest details :vacationRequest){
            VacationRequestDTO vacationRequestDTO = vacationRequestMapper.toDto(details);
            vacationRequestDTOs.add(vacationRequestDTO);
        }
        return  vacationRequestDTOs;
    }

    @Transactional(readOnly = false)
    public VacationRequestDTO updateVacationRequest(Long id,VacationRequestDTO vacationRequestDTO)
    {
        log.debug("Request to save vacationRequest : {}",vacationRequestDTO);
        VacationRequest vacationRequest = vacationRequestMapper.toEntity(vacationRequestDTO);
        if(vacationRequestRepository.existsById(id))
        {
            VacationRequest savevacationRequest = vacationRequestRepository.save(vacationRequest);

        }
        else
        {
            throw new NotFoundException("Company not found ");
        }
        return vacationRequestMapper.toDto(vacationRequest);
    }


    public void delete(Long id)
    {
        vacationRequestRepository.deleteById(id);
    }

    //method for query 2 Vacation Request
    public VacationRequestDTO requestForVacation(VacationRequestDTO vacationRequestDTO)
    {
        RequestVacation requestVacation = new RequestVacation(
                vacationRequestDTO.getStartDate(),
                vacationRequestDTO.getId(),
                vacationRequestDTO.getEndDate(),
                vacationRequestDTO.getDays());
      Employee employee = employeeRepository.findById(requestVacation.getEmployeeId()).get();

          Integer finalVacationDates = calculateDates(vacationRequest.getStartDate(),vacationRequest.getEndDate());
          requestVacation.setHoliday(finalVacationDates);
          numberOfDays(employee.getVacationDays(),requestVacation.getHoliday());
          //VacationRequest object is created
          // Set the vacationStatus field to PENDING
           VacationRequest vacationRequest = new VacationRequest();

          vacationRequest.setStatus(String.valueOf(VacationStatus.PENDING));
          vacationRequest.setStartDate(requestVacation.getStartDate());
          vacationRequest.setEndDate(requestVacation.getEndDate());
          vacationRequest.setDays(requestVacation.getHoliday());
          vacationRequest.setVacrequestEmployee(employee);

          vacationRequest=vacationRequestRepository.save(vacationRequest);
          return vacationRequestMapper.toDto(vacationRequest);
      }


    //method for query 2 Vacation Request
    public Integer calculateDates(LocalDate startDate,LocalDate endDate)
    {
        int result;
        long noOfDaysBetween = ChronoUnit.DAYS.between( startDate,endDate);
        Integer details = Math.toIntExact(noOfDaysBetween - requestVacation.getHoliday());
        return result = details + 1;
    }

    //method for query 2 Vacation Request
    public Boolean numberOfDays(Integer vacationDays,Integer holiday)
    {
        //employee.getVacationDays()
        boolean result = true;
        if(requestVacation.getHoliday() >= vacationDays)
        {
            result=false;
        }
        return result;
    }
    //method for query 4
    public List<VacationRequestDTO> vacationRequestByCompany(Long companyId,String status,LocalDate startDate,LocalDate endDate)
    {
        return vacationRequestRepository.getCompanyStatus(companyId,status,startDate,endDate);

    }

    //Service method for query 5 Accept or Reject Vacation Request
    public VacationRequestDTO updateVacationRequestStatus(VacationRequestStatus vacationRequestStatus)
    {
        if(vacationRequest.getId().equals(vacationRequestStatus.getVacationId()))
        {
            if(vacationRequestStatus.getStatus().equalsIgnoreCase("accepted"))
                 {
                     VacationRequest vacationRequest = vacationRequestRepository.findById(vacationRequestStatus.getVacationId()).get();
                     //Employee employee = vacationRequest.getVacrequestEmployee();
                     EmployeeDTO employeeDTO = employeeMapper.toDto(employee);
                     Employee employee = vacationRequest.getVacrequestEmployee();
                     //employee.setVacationDays(employee.getVacationDays() - vacationRequest.getDays());
                     employee.setVacationDays(employee.getVacationDays() - vacationRequest.getDays());
                     employeeService.save(employeeDTO);
                    //Integer finalresult = vacationRequest.getVacrequestEmployee().getVacationDays()-vacationRequest.getDays();
                    //vacationRequest.getVacrequestEmployee().setVacationDays(finalresult);
                    vacationRequest.setStatus(vacationRequestStatus.getStatus());
                 }
            if(vacationRequestStatus.getStatus().equalsIgnoreCase("rejected") )
                 {
                    vacationRequest.setStatus("rejected");
                 }
        } else   {
            throw new NotFoundException("Vacation id not found ");
        }
        return vacationRequestMapper.toDto(vacationRequest);
    }
/*
    public VacationRequestDTO updateVacationRequestStatus(VacationRequestStatus vacationRequestStatus) {
        VacationRequest vacationRequest = vacationRequestRepository.findById(vacationRequestStatus.getVacationId()).get();
        Employee employee = vacationRequest.getVacrequestEmployee();
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);
        if(!vacationRequestStatus.getStatus().equals("accepted") && !vacationRequestStatus.getStatus().equals("rejected"))
            throw new RuntimeException("Request data are invalid.");
        if (vacationRequestStatus.getStatus() == "accepted") {
            employee.setVacationDays(employee.getVacationDays() - vacationRequest.getDays());
            employeeService.save(employeeDTO);
            System.out.println( employee.getVacationDays());
        }
        vacationRequest.setStatus(vacationRequestStatus.getStatus());
        VacationRequestDTO vacationRequestDTO=vacationRequestMapper.toDto(vacationRequest);
        vacationRequestDTO=save(vacationRequestDTO);
        return vacationRequestDTO;
    }*/


}
