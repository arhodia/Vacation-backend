package gr.knowledge.internship.vacation.service;
import gr.knowledge.internship.vacation.domain.Bonus;
import gr.knowledge.internship.vacation.domain.Company;
import gr.knowledge.internship.vacation.domain.Employee;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.BonusRepository;
import gr.knowledge.internship.vacation.repository.EmployeeRepository;
import gr.knowledge.internship.vacation.repository.CompanyRepository;
import gr.knowledge.internship.vacation.service.dto.BonusDTO;
import gr.knowledge.internship.vacation.service.mapper.BonusMapper;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Log4j2
public class BonusService {
    @Autowired
    private BonusRepository bonusRepository;
    @Autowired
    private BonusMapper bonusMapper;

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    private static final String NotFoundExceptionMessage = "Not Found";


    public BonusService(BonusRepository bonusRepository, BonusMapper bonusMapper,CompanyRepository companyRepository)
    {
        this.bonusRepository = bonusRepository;
        this.bonusMapper = bonusMapper;
        this.companyRepository =companyRepository;
    }


    @Transactional
    public BonusDTO save(BonusDTO bonusDTO){
        log.debug("Request to save Company : {}",bonusDTO);
        Bonus bonus = bonusMapper.toEntity(bonusDTO);
        bonus= bonusRepository.save(bonus);
        return bonusMapper.toDto(bonus);
    }

    @Transactional(readOnly = true)
    public BonusDTO getById(Long id)
    {
        BonusDTO result;
        log.debug("Request to get Bonus by id : {}",id);
        Optional<Bonus> bonus =bonusRepository.findById(id);
        if(bonus.isPresent()){
            result = bonusMapper.toDto(bonus.get());
        }else {
            throw new NotFoundException(NotFoundExceptionMessage);

        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<BonusDTO> getAllBonus()
    {
        log.debug("Request to get all Employee.");
        List<Bonus> bonus = bonusRepository.findAll();
        List<BonusDTO> BonusDTOs = new ArrayList<>();
        for(Bonus details :bonus){
            BonusDTO bonusDTO = bonusMapper.toDto(details);
            BonusDTOs.add(bonusDTO);
        }
        return   BonusDTOs;
    }

    @Transactional(readOnly = false)
    public BonusDTO updateBonus(Long id,BonusDTO bonusDTO)
    {
        log.debug("Request to save Employee : {}",bonusDTO);
        Bonus bonus = bonusMapper.toEntity(bonusDTO);
        if(bonusRepository.existsById(id))
        {
            Bonus saveBonus = bonusRepository.save(bonus);

        }
        else
        {
            throw new NotFoundException("Company not found ");
        }
        return bonusMapper.toDto(bonus);
    }


    public void delete(Long id)
    {
        bonusRepository.deleteById(id);

    }

    //task3
    public enum SeasonRate{
        WINTER("Winter", 1.2),
        AUTUMN("Autumn", 1.1),
        SPRING("Spring", 1.0),
        SUMMER("Summer", 1.3);

        private String season;
        private Double rate;
        private LocalDate startDate;
        private LocalDate endDate;

        SeasonRate(String season, Double rate) {
            this.season = season;
            this.rate = rate;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public String getSeason() {
            return season;
        }

        public Double getRate() {
            return rate;
        }

        public boolean containsDate(LocalDate date) {
            return date.isAfter(startDate) && date.isBefore(endDate);
        }
    }
/*
    public Double getCalculationOfBonus(Double salary,String season)
    {
        SeasonRate mySeason= SeasonRate.valueOf(season.toUpperCase());
      Double result = null;
      if(bonus.getAmount()==salary)
      {
          result=bonus.getBonusEmployee().getSalary() * mySeason.getRate();
      } else if ( bonus.getAmount()==salary)
      {
          result=salary * mySeason.getRate();
      }else if ( bonus.getAmount()==salary)
      {
          result=bonus.getAmount() * mySeason.getRate();
      }else if (bonus.getAmount()==salary)
      {
          result=bonus.getAmount() * mySeason.getRate();
      }
      return result;
    }*/

    public double getCalculationOfBonus(Double salary,String season)
    {
        SeasonRate mySeason= SeasonRate.valueOf(season.toUpperCase());
        Double rate = mySeason.getRate();
         return salary*rate;
    }

    public List<BonusDTO> getBonusesForCompany(Long companyId,String season)
    {
        Company company = companyRepository.findById(companyId).orElseThrow();
        List<Employee> employeesList = employeeRepository.getEachEmployeeForACompany(companyId);
       // List<Double> newemployeesList = new ArrayList<>();
        List<Bonus> bonusesDTO = new ArrayList<>();
        for(Employee details : employeesList)
        {
            Bonus bonus = new Bonus();
            bonus.setAmount(getCalculationOfBonus(details.getSalary(),season));
            bonus.setBonusCompany(company);
            bonus.setBonusEmployee(details);
            bonusesDTO.add(bonus);
        }
        List<Bonus> allBonuses =  bonusRepository.saveAll(bonusesDTO);
        return bonusMapper.toDtoList(allBonuses);
    }
}
