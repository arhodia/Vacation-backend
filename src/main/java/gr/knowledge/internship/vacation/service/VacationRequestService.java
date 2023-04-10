package gr.knowledge.internship.vacation.service;
import gr.knowledge.internship.vacation.domain.CompanyStatus;
import gr.knowledge.internship.vacation.domain.VacationRequest;
import gr.knowledge.internship.vacation.domain.RequestVacation;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.EmployeeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import gr.knowledge.internship.vacation.repository.VacationRequestRepository;
import gr.knowledge.internship.vacation.service.dto.VacationRequestDTO;
import gr.knowledge.internship.vacation.service.mapper.VacationRequestMapper;
import gr.knowledge.internship.vacation.enums.VacationStatus;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
@Log4j2
public class VacationRequestService
{

    private VacationRequestRepository vacationRequestRepository;
    private VacationStatus vacationStatus;
    private CompanyStatus companyStatus;
    private VacationRequestMapper vacationRequestMapper;
    private EmployeeRepository employeeRepository;
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
//method for query 2
  /*  public VacationRequestDTO requestForVacation(RequestVacation requestVacation)
    {
      Employee employee = employeeRepository.findById(requestVacation.getEmployeeId()).get();

      {

      }

    }*/
    //method for query 2
    public Integer calculateDates(LocalDate startDate,LocalDate endDate)
    {
        int result;
        long noOfDaysBetween = ChronoUnit.DAYS.between( startDate,endDate);
        Integer details = Math.toIntExact(noOfDaysBetween - requestVacation.getHoliday());
        return result = details + 1;
    }

    //method for query 2
    public Boolean numberOfDays(Integer vacationDays,RequestVacation requestVacation)
    {
        boolean result = true;
        if(requestVacation.getHoliday() >= vacationDays)
        {
            result=false;
        }
        return result;
    }

    public List<VacationRequestDTO> vacationRequestByCompany(Long companyId,String status,LocalDate startDate,LocalDate endDate)
    {
      return vacationRequestRepository.getCompanyStatus(companyId,status,startDate,endDate);

    }

}
