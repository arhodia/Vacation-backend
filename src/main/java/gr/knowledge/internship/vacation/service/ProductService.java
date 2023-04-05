package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.Product;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.ProductRepository;
import gr.knowledge.internship.vacation.service.dto.ProductDTO;
import gr.knowledge.internship.vacation.service.mapper.ProductMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@Log4j2
public class ProductService {


    private ProductRepository productRepository;

    private ProductMapper productMapper;

    private static final String NotFoundExceptionMessage = "Not Found";


    public ProductService(ProductRepository productRepository, ProductMapper productMapper)
    {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }
    @Transactional
    public ProductDTO save(ProductDTO productDTO){
        log.debug("Request to save Company : {}",productDTO);
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Transactional(readOnly = true)
    public ProductDTO getById(Long id)
    {
        ProductDTO result;
        log.debug("Request to get Product by id : {}",id);
        Optional<Product>  product = productRepository.findById(id);
        if( product.isPresent()){
            result = productMapper.toDto( product.get());
        }else {
            throw new NotFoundException(NotFoundExceptionMessage);

        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProduct()
    {
        log.debug("Request to get all Product.");
        List<Product> product = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();
        for(Product details :product){
            ProductDTO productDTO =productMapper.toDto(details);
            productDTOs.add(productDTO);
        }
        return  productDTOs;
    }

    @Transactional(readOnly = false)
    public ProductDTO updateProduct(Long id,ProductDTO productDTO)
    {
        log.debug("Request to save Employee : {}",productDTO);
        Product product = productMapper.toEntity( productDTO);
        if(productRepository.existsById(id))
        {
            Product saveProducts = productRepository.save(product);

        }
        else
        {
            throw new NotFoundException("Product not found ");
        }
        return productMapper.toDto(product);
    }


    public void delete(Long id)
    {
        productRepository.deleteById(id);
    }



}
