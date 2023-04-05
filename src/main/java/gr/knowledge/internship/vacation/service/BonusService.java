package gr.knowledge.internship.vacation.service;
import gr.knowledge.internship.vacation.domain.Bonus;
import gr.knowledge.internship.vacation.domain.Company;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.BonusRepository;

import gr.knowledge.internship.vacation.service.dto.BonusDTO;
import gr.knowledge.internship.vacation.service.dto.CompanyDTO;
import gr.knowledge.internship.vacation.service.mapper.BonusMapper;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Log4j2
public class BonusService {
    private BonusRepository bonusRepository;

    private BonusMapper bonusMapper;

    private static final String NotFoundExceptionMessage = "Not Found";


    public BonusService(BonusRepository bonusRepository, BonusMapper bonusMapper)
    {
        this.bonusRepository = bonusRepository;
        this.bonusMapper = bonusMapper;
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

    public enum BonusRate{
        WINTER("winter", 1.3),
        AUTUMN("autumn", 0.4),
        SPRING("spring", 0.6),
        SUMMER("summer", 0.7);

        private final String season;
        private final double rate;

        private BonusRate(String season, double rate) {
            this.season = season;
            this.rate = rate;
        }

        public String getSeason() {
            return season;
        }

        public double getRate() {
            return rate;
        }
    }

    public Double getCalculationOfBonus(Double salary,String season)
    {



    }
}
