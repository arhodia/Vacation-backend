package gr.knowledge.internship.vacation.domain;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Data
public class AllProductsForACompany {
    Long id;

    @NotNull
    @Size(max = 255)
    String name;

    @NotNull
    @Size(max = 255)
    String surName;

    Long productId;

    @NotNull
    @Size(max = 255)
    String productName;

    @NotNull
    @Size(max = 1000)
    String productDescription;

    @NotNull
    String productBarcode;

    public AllProductsForACompany( Long id,String name, String surName, Long productId, String productName, String productBarcode, String productDescription) {
        this.id = id;
        this.name = name;
        this.surName = surName;
        this.productId = productId;
        this.productName = productName;
        this.productBarcode = productBarcode;
        this.productDescription = productDescription;
    }
}
