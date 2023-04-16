package com.grekoff.market.core.mappers;

import com.grekoff.market.api.core.ProductDto;
import com.grekoff.market.core.entities.Category;
import com.grekoff.market.core.entities.Product;
import com.grekoff.market.core.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final CategoryService categoryService;

    public ProductDto mapToDto(Product p) {
        ProductDto productDto = new ProductDto();
        productDto.setId(p.getId());
        productDto.setTitle(p.getTitle());
        productDto.setPrice(p.getPrice());
        if (p.getCategory() != null) {
            productDto.setCategoryTitle(p.getCategory().getTitle());
        }
        return productDto;
    }

    public Product mapToEntity(ProductDto pDto) {
        Product product = new Product();
        product.setId(pDto.getId());
        product.setTitle(pDto.getTitle());
        product.setPrice(pDto.getPrice());
        if(pDto.getCategoryTitle() != null){
            categoryService.findByTitle(pDto.getCategoryTitle())
                    .ifPresentOrElse(
                            product::setCategory,
                            ()-> {
                                Category newCategory = categoryService.create(pDto.getCategoryTitle());
                                product.setCategory(newCategory);
                            }
                    );
        }
        return product;
    }
}
