package com.test.tic.brick.controller;


import com.test.tic.brick.dto.CategoryDTO;
import com.test.tic.brick.dto.ProductDTO;
import com.test.tic.brick.service.ProductService;
import com.test.tic.brick.service.impl.ProductServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping("/{id:^[+-]?\\d+$}")
    @Operation(summary = "Get product By Id", description = "Gets a product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Product Not Found")
    })
    public ResponseEntity<ProductDTO> getById(@PathVariable("id") Integer id) {
        log.info("GET /api/products/" + id);
        return productService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    @Operation(summary = "Get product By name", description = "Gets a product by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Product Not Found")
    })
    public ResponseEntity<List<ProductDTO>> getByName(@RequestParam("name") String name) {
        log.info("GET /api/products?name=" + name);
        return productService.getByName(name)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/price")
    @Operation(summary = "Gets products by price", description = "Gets all products by price")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Product Not Found")
    })
    public ResponseEntity<List<ProductDTO>> getByPrice(@RequestParam("price") Double price) {
        log.info("GET /api/products/price?price=" + price);
        return productService.getByPrice(price)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/stock")
    @Operation(summary = "Gets product By stock", description = "Gets all products by stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Product Not Found")
    })
    public ResponseEntity<List<ProductDTO>> getByStock(@RequestParam("stock") Integer stock) {
        log.info("GET /api/products/stock?stock=" + stock);
        return productService.getByStock(stock)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/category")
    @Operation(summary = "Gets products By category", description = "Gets all product by category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Product Not Found")
    })
    public ResponseEntity<List<ProductDTO>> getByCategory(@RequestParam("category") String category) {
        log.info("GET /api/products/category?category=" + category);
        return productService.getByCategory(category)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @Operation(summary = "Get all products", description = "Gets a list of all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<List<ProductDTO>> getAll() {
        log.info("GET /api/products");
        return productService.getAll()
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/category/", method = RequestMethod.GET)
    @Operation(summary = "Get all category", description = "Gets a list of all category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<List<CategoryDTO>> getAllCategory() {
        log.info("GET /api/products/category");

        return productService.getAllCategory()
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @GetMapping("/pageable")
    @Operation(summary = "Get pageable list of products", description = "Obtains a pageable list of product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Page of product Not Found")
    })
    public ResponseEntity<Page<ProductDTO>> getProductPageable(@ParameterObject Pageable pageable) {
        log.info(" GET /api/product/" + pageable);
        return productService.getPageable(pageable)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping("/")
    @Operation(summary = "Create a new Product", description = "Create a new Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "404", description = "Product Not Found")
    })
    public ResponseEntity<ProductDTO> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Product object that needs to be created",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            schema = @Schema(implementation = ProductDTO.class),
                            examples = {
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Example Request",
                                            value = "{\"name\": \"string\", \"price\": 0.0, \"stock\": 0, \"category\": {\"id\": 0}}"
                                    )
                            }
                    )
            ) @RequestBody ProductDTO ProductDTO) {
        log.info("POST /api/product");
        return productService.create(ProductDTO)
                .map(product -> new ResponseEntity<>(product, HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Product by ID", description = "Updates a Product with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Product Not Found")
    })
    public ResponseEntity<ProductDTO> update(@PathVariable("id") Integer id, @RequestBody() ProductDTO ProductDTO) {
        log.info(" PUT /api/product/" + id);
        return productService.update(id, ProductDTO)
                .map(nave -> new ResponseEntity<>(nave, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Product by ID", description = "Deletes a Product with the product ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Product Not Found")
    })
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        log.info(" DELETE /api/product/" + id);
        if (productService.delete(id)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filter")
    @Operation(summary = "Gets products By Filter", description = "Gets all product by Filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Product Not Found")
    })
    public ResponseEntity<List<ProductDTO>> getByFilter(@RequestParam(value = "name" , required = false) String name,
                                                        @RequestParam(value = "price",required = false) Double price,
                                                        @RequestParam(value = "stock",required = false) Integer stock,
                                                        @RequestParam(value = "category",required = false) String category) {
        log.info("GET /api/products/filter?name=" + name + "?price=" + price + "?stock=" + stock + "?category=" + category  );
        return productService.getFilterProduct(name,price,stock,category)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
