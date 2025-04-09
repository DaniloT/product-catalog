package com.example.productcatalog.service;

import com.example.productcatalog.dto.ProductDTO;
import com.example.productcatalog.dto.ProductUpdateDTO;
import com.example.productcatalog.exception.ProductNotFoundException;
import com.example.productcatalog.model.Product;
import com.example.productcatalog.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product(1L, "Test Product", 100.0, "Test Description");
    }

    @Test
    public void createProduct_shouldReturnProductDTO() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO createdProduct = productService.createProduct(new ProductDTO("Test Product", 100.0, "Test Description"));

        assertNotNull(createdProduct);
        assertEquals("Test Product", createdProduct.getName());
        assertEquals(100.0, createdProduct.getPrice());
    }

    @Test
    public void getProductById_shouldThrowProductNotFoundException_whenProductDoesNotExist() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1L));
    }

    @Test
    public void getAllProducts_shouldReturnPaginatedProductDTOs() {
        Product product2 = new Product(2L, "Another Product", 150.0, "Another Description");

        List<Product> products = Arrays.asList(product, product2);
        Page<Product> productPage = new PageImpl<>(products);

        Pageable pageable = PageRequest.of(0, 10);

        when(productRepository.findAll(pageable)).thenReturn(productPage);

        Page<ProductDTO> result = productService.getAllProducts(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("Test Product", result.getContent().get(0).getName());
        assertEquals("Another Product", result.getContent().get(1).getName());
    }

    @Test
    public void patchProduct_shouldUpdateOnlyProvidedFields() {
        ProductUpdateDTO patchDTO = new ProductUpdateDTO();
        patchDTO.setName("Updated Product");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO updatedProduct = productService.patchProduct(1L, patchDTO);

        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.getName());
        assertEquals("Test Description", updatedProduct.getDescription());
        assertEquals(100.0, updatedProduct.getPrice());
    }

    @Test
    public void updateProduct_shouldReplaceProductWithNewValues() {
        ProductDTO updateDTO = new ProductDTO("Updated Product", 120.0, "Updated Description");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO updatedProduct = productService.updateProduct(1L, updateDTO);

        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.getName());
        assertEquals("Updated Description", updatedProduct.getDescription());
        assertEquals(120.0, updatedProduct.getPrice());
    }

    @Test
    public void deleteProduct_shouldDeleteExistingProduct() {
        when(productRepository.existsById(anyLong())).thenReturn(true);

        productService.deleteProduct(1L);

        verify(productRepository).deleteById(1L);
    }

    @Test
    public void deleteProduct_shouldThrowProductNotFoundException_whenProductDoesNotExist() {
        when(productRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(1L));
    }
}
