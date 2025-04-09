package com.example.productcatalog.controller;

import com.example.productcatalog.dto.ProductDTO;
import com.example.productcatalog.dto.ProductUpdateDTO;
import com.example.productcatalog.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ProductDTO productDTO;

    @BeforeEach
    public void setup() {
        HandlerMethodArgumentResolver pageableResolver = new PageableHandlerMethodArgumentResolver();

        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setCustomArgumentResolvers(pageableResolver)
                .build();
        productDTO = new ProductDTO(1L, "Sample Product", 100.0, "This is a sample product.");
    }

    @Test
    public void createProduct_shouldReturnProductDTO() throws Exception {
        when(productService.createProduct(any())).thenReturn(productDTO);

        mockMvc.perform(post("/api/v1/products")
                        .contentType("application/json")
                        .content("{ \"name\": \"Sample Product\", \"price\": 100.0, \"description\": \"This is a sample product.\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Sample Product"))
                .andExpect(jsonPath("$.price").value(100.0))
                .andExpect(jsonPath("$.description").value("This is a sample product."));
    }

    @Test
    public void getProductById_shouldReturnProductDTO() throws Exception {
        when(productService.getProductById(1L)).thenReturn(productDTO);

        mockMvc.perform(get("/api/v1/products/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sample Product"))
                .andExpect(jsonPath("$.price").value(100.0))
                .andExpect(jsonPath("$.description").value("This is a sample product."));
    }

    @Test
    public void getAllProducts_shouldReturnPaginatedList() throws Exception {
        List<ProductDTO> products = Arrays.asList(
                new ProductDTO(1L, "Product 1", 100.0, "Description 1"),
                new ProductDTO(2L, "Product 2", 150.0, "Description 2")
        );

        Page<ProductDTO> productPage = new PageImpl<>(products, PageRequest.of(0, 10), 2);

        when(productService.getAllProducts(any(Pageable.class))).thenReturn(productPage);

        mockMvc.perform(get("/api/v1/products?page=0&size=10&sort=name,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].name").value("Product 1"))
                .andExpect(jsonPath("$.content[1].name").value("Product 2"));
    }

    @Test
    public void updateProduct_shouldReturnUpdatedProductDTO() throws Exception {
        when(productService.updateProduct(eq(1L), any())).thenReturn(productDTO);

        mockMvc.perform(put("/api/v1/products/{id}", 1L)
                        .contentType("application/json")
                        .content("{ \"name\": \"Updated Product\", \"price\": 120.0, \"description\": \"Updated description.\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sample Product"))
                .andExpect(jsonPath("$.price").value(100.0))
                .andExpect(jsonPath("$.description").value("This is a sample product."));
    }

    @Test
    public void patchProduct_shouldReturnPatchedProductDTO() throws Exception {
        ProductUpdateDTO patchDTO = new ProductUpdateDTO();
        patchDTO.setName("Updated Product");

        when(productService.patchProduct(eq(1L), any(ProductUpdateDTO.class)))
                .thenReturn(new ProductDTO(1L, "Updated Product", 100.0, "This is a sample product."));

        mockMvc.perform(patch("/api/v1/products/{id}", 1L)
                        .contentType("application/json")
                        .content("{ \"name\": \"Updated Product\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Product"))
                .andExpect(jsonPath("$.price").value(100.0))
                .andExpect(jsonPath("$.description").value("This is a sample product."));
    }

    @Test
    public void deleteProduct_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/products/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(productService).deleteProduct(eq(1L));
    }
}
