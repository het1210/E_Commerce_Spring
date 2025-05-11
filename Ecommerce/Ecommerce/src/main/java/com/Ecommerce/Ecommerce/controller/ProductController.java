package com.Ecommerce.Ecommerce.controller;

import com.Ecommerce.Ecommerce.model.Product;
import com.Ecommerce.Ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @RequestMapping("/")
    public String greet(){
        return "home";
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
       return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable int productId){
        Product product = productService.getProductById(productId);
        if(product != null)
            return new ResponseEntity<>(product, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile) {
        try {

            System.out.println(product);
            Product prd = productService.addProduct(product, imageFile);
            return new ResponseEntity<>(prd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("product/{id}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable("id") int id){
        Product product = productService.getProductById(id);
        byte[] imageFile = product.getImageData();

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable("id") int id, @RequestPart Product product, @RequestPart MultipartFile imageFile) throws IOException {
        Product prod = productService.updateProduct(id,product, imageFile);
        if(prod != null)
            return new ResponseEntity<>("Product Updated", HttpStatus.OK);
        else
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") int id){
        Product product = productService.getProductById(id);
        if(product != null){
            productService.deleteProduct(id);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam( name = "keyword") String keyword){
        List<Product> products = productService.searchProducts(keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

}
