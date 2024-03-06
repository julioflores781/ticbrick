package com.test.tic.brick.persistence.mapper;

import com.test.tic.brick.dto.CategoryDTO;
import com.test.tic.brick.persistence.entity.Category;

import java.util.Objects;

public interface CategoryMapper {


    static CategoryDTO toDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        if (Objects.nonNull(category)) {
            dto.setId(category.getId());
            dto.setName(category.getName());
            dto.setCode(category.getCode());
            dto.setDescription(category.getDescription());
        }
        return dto;
    }

    static Category toEntity(CategoryDTO dto) {
        Category category = new Category();
        if (Objects.nonNull(dto)) {
            category.setId(dto.getId());
            category.setName(dto.getName());
            category.setCode(dto.getCode());
            category.setDescription(dto.getDescription());
        }
        return category;
    }

}
