package ca.gbc.productservice.controller;

import ca.gbc.productservice.dto.ProductRequest;
import ca.gbc.productservice.dto.ProductResponse;
import ca.gbc.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse createdProduct = productService.createProduct(productRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("location", "/api/product" + createdProduct.id());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .body(createdProduct);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        /*
        try{
            Thread.sleep(5000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
         */

        return productService.getAllProducts();
    }


    //http://localhost:8080/api/product/bjndjjeei
    @PutMapping("/{productId}")
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> updateProduct(@PathVariable("productId") String productId,
                                                         @RequestBody ProductRequest productRequest) {
        String updateproductId = productService.updateProduct(productId, productRequest);

        // Set the location header attribute
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/product" + updateproductId);

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") String productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
