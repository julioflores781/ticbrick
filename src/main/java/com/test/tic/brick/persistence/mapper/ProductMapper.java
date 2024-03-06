package com.test.tic.brick.persistence.mapper;


import com.test.tic.brick.dto.ProductDTO;
import com.test.tic.brick.persistence.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface ProductMapper {

    static ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        if (Objects.nonNull(product)) {
            dto.setId(product.getId());
            dto.setPrice(product.getPrice());
            dto.setName(product.getName());
            dto.setStock(product.getStock());
            dto.setCategory(CategoryMapper.toDTO(product.getCategory()));
        }
        return dto;
    }

    static Product toEntity(ProductDTO dto) {
        if (dto == null) {
            return null;
        }

        Product product = new Product();
        product.setId(dto.getId());
        product.setPrice(dto.getPrice());
        product.setName(dto.getName());
        product.setStock(dto.getStock());
        product.setCategory(CategoryMapper.toEntity(dto.getCategory()));

        return product;
    }

    public static Page<ProductDTO> toPage(List<Product> productList, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = (int) (start + pageable.getPageSize());

        List<ProductDTO> productDTOList = productList.subList(start, Math.min(end, productList.size())).stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(productDTOList, pageable, productList.size());
    }


}


