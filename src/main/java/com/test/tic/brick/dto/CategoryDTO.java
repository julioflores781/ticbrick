package com.test.tic.brick.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(name = "Category")
public class CategoryDTO {

    private Integer id;
    private String code;
    private String name;
    private String description;
}
