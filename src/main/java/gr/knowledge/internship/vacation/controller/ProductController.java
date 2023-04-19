package gr.knowledge.internship.vacation.controller;
import gr.knowledge.internship.vacation.service.ProductService;

import gr.knowledge.internship.vacation.service.dto.ProductDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/api")
public class ProductController {

    private ProductService productService;

    public ProductController( ProductService productService)
    {
        this.productService =productService;
    }
    @CrossOrigin
    @PostMapping("/product")
    public ResponseEntity<ProductDTO> save(@RequestBody ProductDTO productDTO)
    {
        log.debug("Rest request to save Company : {}",productDTO);
        ProductDTO result = productService.save(productDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id)
    {
        log.debug("Rest request to get Company by id : {}",id);
        ProductDTO result = productService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @CrossOrigin
    @GetMapping("/allproduct")
    public ResponseEntity<List<ProductDTO>> getAllEmployee()
    {
        log.debug("Rest request to get AllProduct  : {}");
        List<ProductDTO> result = productService.getAllProduct();
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    @CrossOrigin
    @PutMapping("/updateproduct/{id}")
    public ResponseEntity<ProductDTO> updateProducts(@PathVariable Long id, @RequestBody ProductDTO productDTO)
    {
        log.debug("Rest request to save Product : {}",productDTO);
        ProductDTO result = productService.updateProduct(id,productDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    @CrossOrigin
    @DeleteMapping("/deleteProductById/{id}")
    public String deleteCompany(@PathVariable("id") Long id)
    {
        productService.delete(id);
        return "Deleted Successfully";

    }



}
