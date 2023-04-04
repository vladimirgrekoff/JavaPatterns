package com.grekoff.market.core.repositories;

import com.grekoff.market.core.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("select count(*) from Product")
    Long countProducts();

    @Query("select p from Product p where p.title = ?1")
    Optional<Product> findByTitle(String title);
}