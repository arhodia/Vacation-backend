package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.Employee;
import gr.knowledge.internship.vacation.domain.MonthlyExpenses;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.EmployeeRepository;
import gr.knowledge.internship.vacation.service.dto.EmployeeDTO;
import gr.knowledge.internship.vacation.service.mapper.EmployeeMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Log4j2
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    private EmployeeMapper employeeMapper;

    private static final String NotFoundExceptionMessage = "Not Found";


    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper)
    {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Transactional
    public EmployeeDTO save(EmployeeDTO employeeDTO)
    {
        log.debug("Request to save Employee : {}",employeeDTO);
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Transactional(readOnly = true)
    public EmployeeDTO getById(Long id)
    {
        EmployeeDTO result;
        log.debug("Request to get Employee by id : {}",id);
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()){
            result = employeeMapper.toDto(employee.get());
        }else {
            throw new NotFoundException(NotFoundExceptionMessage);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getAllEmployee()
    {
        log.debug("Request to get all Employee.");
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employeesDTOs = new ArrayList<>();
        for(Employee employee : employees){
            EmployeeDTO employeeDTO = employeeMapper.toDto(employee);
            employeesDTOs.add(employeeDTO);
        }
        return employeesDTOs;
    }


    @Transactional(readOnly = true)
    public EmployeeDTO updateEmployee(Long id,EmployeeDTO employeeDTO)
    {
        log.debug("Request to save Employee : {}",employeeDTO);
        Employee employee = employeeMapper.toEntity(employeeDTO);
        if(employeeRepository.existsById(employee.getId()))
        {
            Employee saveProducts = employeeRepository.save(employee);

        }
        else
        {
            throw new NotFoundException("Employee not found ");
        }
        return employeeMapper.toDto(employee);
    }

    public void delete(Long id)
    {
        employeeRepository.deleteById(id);

    }

    //Query 6 Calculate monthly expenses for Company
    //http://localhost:8081/api/calculateExpensesForCompany/51
    public double calculateExpensesForCompany(Long employeeCompany) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeCompany);
        double finallExpenses = 0.0;
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            finallExpenses += employee.getSalary();
        }
        return finallExpenses;
    }


}
