package com.test.tic.brick.client.rest;

import com.test.tic.brick.dto.CategoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "categoryClient", url = "${category.api.url}")
public interface CategoryClient {

    @GetMapping("/loyalty/category/producer")
    List<CategoryDTO> getCategoryData();
}
