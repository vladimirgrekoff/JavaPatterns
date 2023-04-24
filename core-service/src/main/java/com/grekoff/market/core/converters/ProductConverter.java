package com.grekoff.market.core.converters;

import com.grekoff.market.api.core.ProductDto;
import com.grekoff.market.core.entities.Category;
import com.grekoff.market.core.entities.Product;
import com.grekoff.market.core.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductConverter {
    private final CategoryService categoryService;

    public Product dtoToEntity(ProductDto pDto) {
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

    public ProductDto entityToDto(Product p) {
        ProductDto productDto = new ProductDto();
        productDto.setId(p.getId());
        productDto.setTitle(p.getTitle());
        productDto.setPrice(p.getPrice());
        productDto.setCategoryTitle(p.getCategory().getTitle());
        return productDto;
    }

}
