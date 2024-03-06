package com.test.tic.brick.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(name = "Product")
public class ProductDTO {
    private Integer id;
    private String name;
    private double price;
    private int stock;
    private CategoryDTO category;
}
