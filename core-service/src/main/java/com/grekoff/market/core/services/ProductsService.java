package com.grekoff.market.core.services;



import com.grekoff.market.api.core.ProductDto;
import com.grekoff.market.core.converters.ProductConverter;
import com.grekoff.market.core.entities.Product;
import com.grekoff.market.core.event.ChangedDBProductsEvent;
import com.grekoff.market.core.exceptions.ResourceNotFoundException;
import com.grekoff.market.core.mappers.ProductMapper;
import com.grekoff.market.core.proxy.UsersProductsService;
import com.grekoff.market.core.publisher.Publisher;
import com.grekoff.market.core.repositories.ProductsRepository;
import com.grekoff.market.core.services.specifications.ProductsSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductsService implements UsersProductsService {
        private final ProductsRepository productsRepository;
        private final CategoryService categoryService;
        private final ProductConverter productConverter;
        private final ProductMapper productMapper;


    @Autowired
    Publisher eventPublisher;

    public int page = 0;


    public List<Product> findAll() {
        return productsRepository.findAll();
    }



    public Page<Product> findAllPages(Integer minPrice, Integer maxPrice, String partTitle, Integer offset, Integer size, Boolean first, Boolean last,  Integer currentPage) {
        Long numberOfProducts = productsRepository.countProducts();

        Integer lastPage = calculateNumberOfLastPages(numberOfProducts, size);

        checkFirstNumberPage(offset);

        if (first){
            page = 0;
        }

        if (last){
            page = lastPage;
        }
        page = checkLastNumberPage(page, lastPage);


        Specification<Product> spec = Specification.where(null);

        if (minPrice != null) {
            spec = spec.and(ProductsSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductsSpecifications.priceLessOrEqualsThan(maxPrice));
        }
        if (partTitle != null) {
            spec = spec.and(ProductsSpecifications.titleLike(partTitle));
        }
        return productsRepository.findAll(spec, PageRequest.of(page,size));
    }

    private int checkLastNumberPage(int numCurrentPage, int lastPage){
        if (numCurrentPage >= lastPage) {
            numCurrentPage = lastPage;
        }
        return numCurrentPage;
    }
    private int calculateNumberOfLastPages(long number, int numberOnPages){
        int lastPage;
        long onPages = numberOnPages;

        long pages =  (number / onPages);
        if ((number % onPages) > 0) {
            lastPage = (int) pages;
        } else {
            lastPage = (int) pages - 1;
        }
        return lastPage;
    }

    private void checkFirstNumberPage(Integer offset) {
        if (offset != 0) {
            page = page + offset;
            if (page < 0) {
                page = 0;
            }
        }
    }


    public Optional<Product> findById(Long id) {
        return productsRepository.findById(id);
    }

    public void deleteById(Long id) {
        productsRepository.deleteById(id);
    }

    @Transactional
    public void createNewProduct(ProductDto productDto) {
        productsRepository.save(productMapper.mapToEntity(productDto));
        eventPublisher.publishChangedDBProductsEvent(new ChangedDBProductsEvent("Products Database changed"));
    }



    @Transactional
    public void update(ProductDto productDto) {
        Product product = productsRepository.findById(productDto.getId()).orElseThrow(()-> new ResourceNotFoundException("Продукт отсутствует в списке, id: " + productDto.getId()));
        product.setPrice(productDto.getPrice());
        product.setTitle(product.getTitle());
        productsRepository.save(product);
        eventPublisher.publishChangedDBProductsEvent(new ChangedDBProductsEvent("Products Database changed"));

    }

}
