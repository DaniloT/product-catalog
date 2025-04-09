package com.example.productcatalog.service;

import com.example.productcatalog.dto.ProductDTO;
import com.example.productcatalog.dto.ProductUpdateDTO;
import com.example.productcatalog.exception.ProductNotFoundException;
import com.example.productcatalog.model.Product;
import com.example.productcatalog.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Create Product - Returns the created ProductDTO
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = new Product(productDTO.getName(), productDTO.getPrice(), productDTO.getDescription());
        product = productRepository.save(product);
        return new ProductDTO(product.getId(), product.getName(), product.getPrice(), product.getDescription());
    }

    // Get Product By ID - Returns a single ProductDTO
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id)); // Handle non-existent product
        return new ProductDTO(product.getId(), product.getName(), product.getPrice(), product.getDescription());
    }

    // Get All Products - Returns a list of ProductDTOs
    @Transactional
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(product -> new ProductDTO(product.getId(), product.getName(), product.getPrice(), product.getDescription()));
    }

    // Update Product - Replace the full product (PUT)
    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product = productRepository.save(product);
        return new ProductDTO(product.getId(), product.getName(), product.getPrice(), product.getDescription());
    }

    // Update Product - Replace partial fields of product (PATCH)
    @Transactional
    public ProductDTO patchProduct(Long id, ProductUpdateDTO patchDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        // Update only the fields that are provided in the patch
        if (patchDTO.getName() != null) {
            existingProduct.setName(patchDTO.getName());
        }
        if (patchDTO.getDescription() != null) {
            existingProduct.setDescription(patchDTO.getDescription());
        }
        if (patchDTO.getPrice() != null) {
            existingProduct.setPrice(patchDTO.getPrice());
        }

        productRepository.save(existingProduct);

        return new ProductDTO(existingProduct.getId(), existingProduct.getName(), existingProduct.getPrice(), existingProduct.getDescription());
    }

    // Delete Product - Removes the product from the repository
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }
}
