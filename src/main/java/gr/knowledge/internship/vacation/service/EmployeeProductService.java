package gr.knowledge.internship.vacation.service;
import gr.knowledge.internship.vacation.domain.AllProductsForACompany;
import gr.knowledge.internship.vacation.domain.EmployeeProduct;
import gr.knowledge.internship.vacation.domain.Product;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.EmployeeProductRepository;
import gr.knowledge.internship.vacation.repository.ProductRepository;
import gr.knowledge.internship.vacation.service.dto.EmployeeProductDTO;
import gr.knowledge.internship.vacation.service.dto.ProductDTO;
import gr.knowledge.internship.vacation.service.mapper.EmployeeProductMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
@Log4j2
public class EmployeeProductService {
    @Autowired
    private EmployeeProductRepository employeeProductRepository;
    @Autowired
    private EmployeeProductMapper employeeProductMapper;
    @Autowired
    private ProductRepository productRepository;

    private static final String NotFoundExceptionMessage = "Not Found";


    public EmployeeProductService(EmployeeProductRepository employeeProductRepository, EmployeeProductMapper employeeProductMapper)
    {
        this.employeeProductRepository = employeeProductRepository;
        this.employeeProductMapper = employeeProductMapper;
    }


    @Transactional
    public EmployeeProductDTO save(EmployeeProductDTO employeeProductDTO){
        log.debug("Request to save Company : {}",employeeProductDTO);
        EmployeeProduct employeeProduct  = employeeProductMapper.toEntity(employeeProductDTO);
        employeeProduct = employeeProductRepository .save(employeeProduct);
        return employeeProductMapper.toDto(employeeProduct);
    }
    @Transactional(readOnly = true)
    public EmployeeProductDTO getById(Long id)
    {
        EmployeeProductDTO result;
        log.debug("Request to get EmployeeProduct by id : {}",id);
        Optional<EmployeeProduct> employeeProduct = employeeProductRepository.findById(id);
        if(employeeProduct.isPresent()){
            result = employeeProductMapper.toDto(employeeProduct.get());
        }else {
            throw new NotFoundException(NotFoundExceptionMessage);

        }
        return result;
    }
    @Transactional(readOnly = true)
    public List<EmployeeProductDTO> getAllEmployeeProduct()
    {
        log.debug("Request to get all Employee.");
        List<EmployeeProduct> employeeProduct = employeeProductRepository.findAll();
        List<EmployeeProductDTO> employeeProductDTOs = new ArrayList<>();
        for(EmployeeProduct details :employeeProduct){
            EmployeeProductDTO  employeeProductDTO = employeeProductMapper.toDto(details);
            employeeProductDTOs.add(employeeProductDTO);
        }
        return  employeeProductDTOs;
    }
    @Transactional(readOnly = false)
    public EmployeeProductDTO updateEmployeeProduct(Long id,EmployeeProductDTO employeeProductDTO)
    {
        log.debug("Request to save Employee : {}",employeeProductDTO);
        EmployeeProduct employeeProduct = employeeProductMapper.toEntity(employeeProductDTO);
        if(employeeProductRepository.existsById(id))
        {
            EmployeeProduct saveEmployeeProduct = employeeProductRepository.save(employeeProduct);

        }
        else
        {
            throw new NotFoundException("Company not found ");
        }
        return employeeProductMapper.toDto(employeeProduct);
    }


    public void delete(Long id)
    {
        employeeProductRepository.deleteById(id);

    }


    //QUERY 8 Get All products for a Company
    public  Map<String, List<Product>> getAllProductsForACompany(Long companyId)
    {
        List<AllProductsForACompany> allProductsForCompany = employeeProductRepository.GetAllProductsForCompany(companyId);

        Map<String, List<Product>> productMap = new HashMap<>();//declare map

       // LinkedHashMap<String, String> sortedMap = new LinkedHashMap<>();//declare linkedhashmap

        ArrayList<String> list = new ArrayList<>();//declare arraylist

        //List<EmployeeProduct> employeeproducts = employeeProductRepository.findAll();

        for (AllProductsForACompany detail : allProductsForCompany)
        {
            String employeeName  = detail.getName() + " " + detail.getSurName();

            Product product = productRepository.findById(detail.getProductId()).orElse(null);
            if (product != null)
            {
                if (!productMap.containsKey(employeeName))
                {
                    productMap.put(employeeName, new ArrayList<>());
                }
                productMap.get(employeeName).add(product);
            }
        }

        return productMap;
    }


}
