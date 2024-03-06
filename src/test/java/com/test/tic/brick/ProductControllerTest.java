package com.test.tic.brick;

import com.test.tic.brick.dto.ProductDTO;
import com.test.tic.brick.persistence.entity.Category;
import com.test.tic.brick.persistence.entity.Product;
import com.test.tic.brick.persistence.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {


    private final TestRestTemplate restTemplate;
    private final ProductRepository productRepository;

    @Autowired
    public ProductControllerTest(TestRestTemplate restTemplate, ProductRepository productRepository) {
        this.restTemplate = restTemplate;
        this.productRepository = productRepository;
    }

    Integer ultimoId = 0;
    Category category = new Category(1, "test", "testeo", null, null);
    Product product = new Product("prueba", 15.5, 10, category);

    @BeforeEach
    public void setUp() {
        productRepository.save(product);

        List<Product> products = productRepository.findAll();
        Product lastProduct = products.get(products.size() - 1);
        ultimoId = lastProduct.getId();
        System.out.println(products.size());
    }

    @AfterEach
    public void clean() {
        productRepository.deleteById(ultimoId);
    }

    @Test
    @DisplayName("Test get by id")
    public void testGetById() {
        ResponseEntity<ProductDTO> responseEntity = restTemplate.exchange(
                "/products/{ultimoId}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ProductDTO>() {
                },
                ultimoId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody().getPrice(), 15.50);
    }

    @Test
    @DisplayName("Test get by Name")
    public void testGetByName() {
        ResponseEntity<List<ProductDTO>> responseEntity = restTemplate.exchange(
                "/products?name={name}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDTO>>() {
                },
                product.getName());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody().get(0).getName(), product.getName());
    }

    @Test
    @DisplayName("Test get by price")
    public void testGetBy() {
        ResponseEntity<List<ProductDTO>> responseEntity = restTemplate.exchange(
                "/products/price?price={price}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDTO>>() {
                },
                product.getPrice());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody().get(0).getPrice(), product.getPrice());
    }


}
