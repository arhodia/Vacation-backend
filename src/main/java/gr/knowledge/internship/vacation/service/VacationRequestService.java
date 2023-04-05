package gr.knowledge.internship.vacation.service;
import gr.knowledge.internship.vacation.domain.VacationRequest;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import gr.knowledge.internship.vacation.repository.VacationRequestRepository;
import gr.knowledge.internship.vacation.service.dto.VacationRequestDTO;
import gr.knowledge.internship.vacation.service.mapper.VaccationRequestMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
@Log4j2
public class VacationRequestService
{

    private VacationRequestRepository vacationRequestRepository;

    private VaccationRequestMapper vaccationRequestMapper;

    private static final String NotFoundExceptionMessage = "Not Found";


    public VacationRequestService(VacationRequestRepository vacationRequestRepository, VaccationRequestMapper vaccationRequestMapper)
    {
        this. vacationRequestRepository = vacationRequestRepository;
        this.vaccationRequestMapper = vaccationRequestMapper;
    }

    @Transactional
    public VacationRequestDTO save(VacationRequestDTO vacationRequestDTO){
        log.debug("Request to save Company : {}",vacationRequestDTO);
        VacationRequest vacationRequest = vaccationRequestMapper.toEntity(vacationRequestDTO);
        vacationRequest =  vacationRequestRepository.save(vacationRequest);
        return vaccationRequestMapper.toDto(vacationRequest);
    }

    @Transactional(readOnly = true)
    public VacationRequestDTO  getById(Long id)
    {
        VacationRequestDTO result;
        log.debug("Request to get VacationRequest by id : {}",id);
        Optional<VacationRequest> vacationRequest = vacationRequestRepository.findById(id);
        if(vacationRequest.isPresent()){
            result = vaccationRequestMapper.toDto(vacationRequest.get());
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
            VacationRequestDTO vacationRequestDTO = vaccationRequestMapper.toDto(details);
            vacationRequestDTOs.add(vacationRequestDTO);
        }
        return  vacationRequestDTOs;
    }

    @Transactional(readOnly = false)
    public VacationRequestDTO updateVacationRequest(Long id,VacationRequestDTO vacationRequestDTO)
    {
        log.debug("Request to save vacationRequest : {}",vacationRequestDTO);
        VacationRequest vacationRequest = vaccationRequestMapper.toEntity(vacationRequestDTO);
        if(vacationRequestRepository.existsById(id))
        {
            VacationRequest savevacationRequest = vacationRequestRepository.save(vacationRequest);

        }
        else
        {
            throw new NotFoundException("Company not found ");
        }
        return vaccationRequestMapper.toDto(vacationRequest);
    }


    public void delete(Long id)
    {
        vacationRequestRepository.deleteById(id);

    }
}
