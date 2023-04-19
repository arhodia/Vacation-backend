package gr.knowledge.internship.vacation.service;
import gr.knowledge.internship.vacation.domain.*;
import gr.knowledge.internship.vacation.domain.Employee;
import gr.knowledge.internship.vacation.repository.CompanyRepository;
import gr.knowledge.internship.vacation.domain.Company;
import gr.knowledge.internship.vacation.domain.RequestForVacation;
import gr.knowledge.internship.vacation.domain.VacationRequest;
import gr.knowledge.internship.vacation.enums.VacationStatus;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.EmployeeRepository;
import gr.knowledge.internship.vacation.service.mapper.EmployeeMapper;
import gr.knowledge.internship.vacation.service.dto.EmployeeDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
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
    @Autowired
    private CompanyRepository companyRepository;



    private Employee employee;
    private VacationRequest vacationRequest;

    @Autowired
    private static final String NotFoundExceptionMessage = "Not Found";


    public VacationRequestService(VacationRequestRepository vacationRequestRepository, VacationRequestMapper vacationRequestMapper,EmployeeRepository employeeRepository)
    {
        this. vacationRequestRepository = vacationRequestRepository;
        this.vacationRequestMapper = vacationRequestMapper;
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

    //Service method for query 2 Vacation Request
    public VacationRequestDTO requestForVacation(RequestForVacation requestForVacation)
    {
        Employee employee = employeeRepository.findById(requestForVacation.getEmployeeId()).orElseThrow();
        int result;
        long noOfDaysBetween = ChronoUnit.DAYS.between(requestForVacation.getStartDate(),requestForVacation.getEndDate());
        Integer details = Math.toIntExact(noOfDaysBetween - requestForVacation.getHoliday());
        result = details + 1;
        requestForVacation.setHoliday(result);
        if(requestForVacation.getHoliday() <= employee.getVacationDays())
        {
          //VacationRequest object is created
          // Set the vacationStatus field to PENDING
        VacationRequest vacationRequest = new VacationRequest();

        vacationRequest.setStatus(String.valueOf(VacationStatus.PENDING));
        vacationRequest.setStartDate(requestForVacation.getStartDate());
        vacationRequest.setEndDate(requestForVacation.getEndDate());
        vacationRequest.setDays(requestForVacation.getHoliday());
        vacationRequest.setVacrequestEmployee(employee);
        vacationRequestRepository.save( vacationRequest);
        vacationRequestMapper.toDto(vacationRequest);

        }
        if (vacationRequest != null)
        {
            return vacationRequestMapper.toDto(vacationRequest);
        } else
        {
            throw new NotFoundException("Vacation Request not acceptable");
        }

    }


    //Service method for query 4 Vacation  Requests by Company
    public List<VacationRequestDTO> vacationRequestByCompany(CompanyStatus companyStatus)
    {
        Company company = companyRepository.findById(companyStatus.getCompanyId()).get();
        List<VacationRequest> vacationRequest = vacationRequestRepository.getCompanyStatus(companyStatus.getCompanyId(),companyStatus.getStatus(),companyStatus.getStartDate(),companyStatus.getEndDate());
        List<VacationRequestDTO> vacationRequestDTOs = new ArrayList<>();
        for(VacationRequest details :vacationRequest){
            VacationRequestDTO vacationRequestDTO = vacationRequestMapper.toDto(details);
            vacationRequestDTOs.add(vacationRequestDTO);
        }
        return  vacationRequestDTOs;
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

}
