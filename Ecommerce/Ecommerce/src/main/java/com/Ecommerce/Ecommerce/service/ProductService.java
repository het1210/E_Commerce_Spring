package com.Ecommerce.Ecommerce.service;

import com.Ecommerce.Ecommerce.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {


    List<Product> getAllProducts();
    Product getProductById(int productId);


    Product addProduct(Product product, MultipartFile imageFile) throws IOException;

    Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException;

    void deleteProduct(int id);

    List<Product> searchProducts(String keyword);
}
