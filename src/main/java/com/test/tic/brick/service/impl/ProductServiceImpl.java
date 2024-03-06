package com.test.tic.brick.service.impl;


import com.test.tic.brick.client.rest.CategoryClient;
import com.test.tic.brick.dto.CategoryDTO;
import com.test.tic.brick.dto.ProductDTO;
import com.test.tic.brick.exception.ConnectClientExceptionHandler;
import com.test.tic.brick.exception.EntityNotFoundException;
import com.test.tic.brick.exception.IllegalArgumentExceptionHandler;
import com.test.tic.brick.exception.JdbcSQLIntegrityConstraintViolationExceptionHandler;
import com.test.tic.brick.persistence.entity.Category;
import com.test.tic.brick.persistence.entity.Product;
import com.test.tic.brick.persistence.mapper.CategoryMapper;
import com.test.tic.brick.persistence.mapper.ProductMapper;
import com.test.tic.brick.persistence.repository.CategoryRepository;
import com.test.tic.brick.persistence.repository.ProductRepository;
import com.test.tic.brick.service.ProductService;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "categoryCache")
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryClient categoryClient;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, CategoryClient categoryClient) {
        this.categoryRepository = categoryRepository;
        this.categoryClient = categoryClient;
        this.productRepository = productRepository;
    }

    @Override
    public Optional<ProductDTO> getById(Integer id) {
        return productRepository.findById(id).map(ProductMapper::toDTO);
    }

    @Override
    public Optional<List<ProductDTO>> getByName(String name) {
        List<Product> listProduct = productRepository.findByNameContaining(name);
        if (!listProduct.isEmpty()) {
            return Optional.of(listProduct.stream()
                    .map(ProductMapper::toDTO)
                    .collect(Collectors.toList()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<ProductDTO>> getByPrice(Double price) {
        List<Product> listProduct = productRepository.findByPrice(price);
        if (!listProduct.isEmpty()) {
            return Optional.of(listProduct.stream()
                    .map(ProductMapper::toDTO)
                    .collect(Collectors.toList()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<ProductDTO>> getByStock(Integer stock) {
        List<Product> listProduct = productRepository.findByStock(stock);
        if (!listProduct.isEmpty()) {
            return Optional.of(listProduct.stream()
                    .map(ProductMapper::toDTO)
                    .collect(Collectors.toList()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<ProductDTO>> getByCategory(String categoryName) {
        Category category = categoryRepository.findByName(categoryName);
        if (category != null) {
            List<Product> listProduct = productRepository.findByCategoryId(category.getId());
            if (!listProduct.isEmpty()) {
                return Optional.of(listProduct.stream()
                        .map(ProductMapper::toDTO)
                        .collect(Collectors.toList()));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Page<ProductDTO>> getPageable(Pageable pageable) {
        try {
            List<Product> listProduct = productRepository.findAll();
            Page<ProductDTO> productDTOS = ProductMapper.toPage(listProduct, pageable);
            return Optional.of(productDTOS);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new IllegalArgumentExceptionHandler("Page of Product Not Found : " + ex.getMessage());
        }
    }

    @Override
    public Optional<List<ProductDTO>> getAll() {
        return Optional.of(productRepository.findAll().stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @Override
    public Optional<ProductDTO> create(ProductDTO productDTO) {
        validateProductDTO(productDTO);

        Category category = getCategoryById(productDTO.getCategory().getId());
        Product product = createProductFromDTO(productDTO, category);
        Product savedProduct = saveProduct(product);

        return Optional.of(ProductMapper.toDTO(savedProduct));
    }


    @Override
    public Optional<ProductDTO> update(Integer id, ProductDTO productDTO) {
        Optional<Product> optionalProduct = productRepository.findById(id)
                .map(product -> {
                    updateProduct(product, productDTO);
                    return productRepository.save(product);
                });

        return optionalProduct.map(ProductMapper::toDTO);
    }

    @Override
    public boolean delete(Integer id) throws EntityNotFoundException {
        return getById(id).map(product -> {
            productRepository.deleteById(id);
            return true;
        }).orElseThrow(() -> new EntityNotFoundException("Product Not Found  with this ID: " + id));
    }


    @Override
    @Cacheable
    public Optional<List<CategoryDTO>> getAllCategory() {
        try {

            List<CategoryDTO> listCategory = categoryClient.getCategoryData();

            saveMissingCategories(listCategory);
            return Optional.of(listCategory);
        } catch (Exception ex) {
            log.warn(ex.getMessage());
            List<Category> listCategory = categoryRepository.findAll();
            return Optional.of(listCategory.stream()
                    .map(CategoryMapper::toDTO)
                    .collect(Collectors.toList()));
        }
    }

    @Override
    public Optional<List<ProductDTO>> getFilterProduct(String name, Double price, Integer stock, String category) {
        List<Product> listProduct = productRepository.findByFilter(name,price,stock,category);
        if (!listProduct.isEmpty()) {
            return Optional.of(listProduct.stream()
                    .map(ProductMapper::toDTO)
                    .collect(Collectors.toList()));
        }
        return Optional.empty();
    }

    private void saveMissingCategories(List<CategoryDTO> categoryDTOs) {
        List<String> existingNames = categoryRepository.findAll().stream()
                .map(Category::getName)
                .collect(Collectors.toList());

        for (CategoryDTO categoryDTO : categoryDTOs) {
            if (!existingNames.contains(categoryDTO.getName())) {
                Category category = CategoryMapper.toEntity(categoryDTO);
                categoryRepository.save(category);
                existingNames.add(categoryDTO.getName());
            }
        }
        log.info("Update DB and Cache!");
    }

    private void updateProduct(Product existingProduct, ProductDTO updatedProduct) {

        if (updatedProduct.getName() != null)
            existingProduct.setName(updatedProduct.getName());
        if (updatedProduct.getStock() != 0)
            existingProduct.setStock(updatedProduct.getStock());
        if (updatedProduct.getPrice() != 0)
            existingProduct.setPrice(updatedProduct.getPrice());
        if (updatedProduct.getCategory() != null)
            existingProduct.setCategory(getCategoryById(updatedProduct.getCategory().getId()));
    }

    private void validateProductDTO(ProductDTO productDTO) {
        if (productDTO == null) {
            log.warn("ProductDTO cannot be null");
            throw new IllegalArgumentExceptionHandler("Product cannot be null");
        } else if (StringUtils.isBlank(productDTO.getName()) || productDTO.getPrice() <= 0 || productDTO.getStock() < 0 || productDTO.getCategory() == null) {
            log.warn("All fields are required.");
            throw new IllegalArgumentExceptionHandler("All fields are required.");
        }
    }

    private Category getCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));
    }

    private Product createProductFromDTO(ProductDTO productDTO, Category category) {
        Product product = ProductMapper.toEntity(productDTO);
        product.setCategory(category);
        return product;
    }

    private Product saveProduct(Product product) {
        try {
            return productRepository.save(product);
        } catch (DataIntegrityViolationException ex) {
            log.error("Error saving product: {}", ex.getMessage());
            throw new JdbcSQLIntegrityConstraintViolationExceptionHandler("Error saving the product: " + ex.getMessage());
        }
    }

}