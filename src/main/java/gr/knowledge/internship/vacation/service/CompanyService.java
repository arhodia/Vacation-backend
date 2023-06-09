package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.Company;
import gr.knowledge.internship.vacation.domain.MonthlyExpenses;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.CompanyRepository;
import gr.knowledge.internship.vacation.service.dto.CompanyDTO;
import gr.knowledge.internship.vacation.service.mapper.CompanyMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;




@Service
@Transactional
@Log4j2
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private static final String NotFoundExceptionMessage = "Not Found";

    public CompanyService(CompanyRepository companyRepository,CompanyMapper companyMapper)
    {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }


    @Transactional
    public CompanyDTO save(CompanyDTO companyDTO)
    {
        log.debug("Request to save Company : {}",companyDTO);
        Company company = companyMapper.toEntity(companyDTO);
        company = companyRepository.save(company);
        return companyMapper.toDto(company);
    }

    @Transactional(readOnly = true)
    public CompanyDTO getById(Long id)
    {
        CompanyDTO result;
        log.debug("Request to get Company by id : {}",id);
        Optional<Company> company = companyRepository.findById(id);
        if(company.isPresent())
        {
            result = companyMapper.toDto(company.get());
        }else
        {
            throw new NotFoundException(NotFoundExceptionMessage);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<CompanyDTO> getAllCompany()
    {
        log.debug("Request to get all Employee.");
        List<Company> company = companyRepository.findAll();
        List<CompanyDTO> companyDTOs = new ArrayList<>();
        for(Company details :company){
            CompanyDTO companyDTO = companyMapper.toDto(details);
            companyDTOs.add(companyDTO);
        }
        return  companyDTOs;
    }

    @Transactional(readOnly = false)
    public CompanyDTO updateCompany(Long id,CompanyDTO companyDTO)
    {
        log.debug("Request to save Employee : {}",companyDTO);
        Company company = companyMapper.toEntity(companyDTO);
        if(companyRepository.existsById(id))
        {
            Company saveCompany = companyRepository.save(company);

        }
        else
        {
            throw new NotFoundException("Company not found ");
        }
        return companyMapper.toDto(company);
    }


    public void delete(Long id)
    {
        companyRepository.deleteById(id);

    }


}
