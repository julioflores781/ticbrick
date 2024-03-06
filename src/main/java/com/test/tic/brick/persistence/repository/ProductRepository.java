package com.test.tic.brick.persistence.repository;

import com.test.tic.brick.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByNameContaining(String name);

    List<Product> findByPrice(Double price);

    List<Product> findByStock(Integer stock);

    List<Product> findByCategoryId(Integer categoryId);

    @Query("SELECT p FROM Product p " +
            "WHERE (:name IS NULL OR p.name LIKE CONCAT('%', :name, '%')) " +
            "AND (:price IS NULL OR p.price = :price) " +
            "AND (:stock IS NULL OR p.stock = :stock) " +
            "AND (:category IS NULL OR p.category.name = :category)")
    List<Product> findByFilter(String name, Double price, Integer stock, String category);



}
