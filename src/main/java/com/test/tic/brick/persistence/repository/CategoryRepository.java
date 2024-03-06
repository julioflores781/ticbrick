package com.test.tic.brick.persistence.repository;

import com.test.tic.brick.persistence.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByName(String name);
}
