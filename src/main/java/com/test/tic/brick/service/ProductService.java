package com.test.tic.brick.service;

import com.test.tic.brick.dto.CategoryDTO;
import com.test.tic.brick.dto.ProductDTO;
import com.test.tic.brick.exception.ConnectClientExceptionHandler;
import com.test.tic.brick.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    /**
     * GET         /product           Obtiene el listado de productos (filtro por name, price, stock y category) (Paginado)
     * GET         /product/{id}      Obtiene un producto
     * POST       /product            Crea un producto
     * DELETE   /product            Elimina un producto
     * PUT          /product           Actualiza un producto
     * GET         /category          Listado de categorías
     */

    Optional<ProductDTO> getById(Integer id);

    Optional<List<ProductDTO>> getByName(String name);

    Optional<List<ProductDTO>> getByPrice(Double price);

    Optional<List<ProductDTO>> getByStock(Integer stock);

    Optional<List<ProductDTO>> getByCategory(String categoryName);

    Optional<Page<ProductDTO>> getPageable(Pageable pageable);

    Optional<List<ProductDTO>> getAll();

    Optional<ProductDTO> create(ProductDTO productDTO);

    Optional<ProductDTO> update(Integer id, ProductDTO productDTO);

    boolean delete(Integer id) throws EntityNotFoundException;

    Optional<List<CategoryDTO>> getAllCategory() throws ConnectClientExceptionHandler;
    Optional<List<ProductDTO>> getFilterProduct(String name, Double price, Integer stock, String category);


}
