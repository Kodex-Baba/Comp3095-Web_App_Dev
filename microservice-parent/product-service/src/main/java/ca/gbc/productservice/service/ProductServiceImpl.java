package ca.gbc.productservice.service;

import ca.gbc.productservice.dto.ProductRequest;
import ca.gbc.productservice.dto.ProductResponse;
import ca.gbc.productservice.model.Product;
import ca.gbc.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
         log.debug("Creating a new Product {}", productRequest.name());

         Product product = Product.builder()
                 .name(productRequest.name())
                 .description(productRequest.description())
                 .price(productRequest.price())
                 .build();

         productRepository.save(product);

         log.info("New product {}: {} saved to database", product.getId(), product.getName());

         return new ProductResponse(product.getId(),
                 product.getName(),
                 product.getDescription(),
                 product.getPrice());

    }

    @Override
    public List<ProductResponse> getAllProducts() {
        log.debug("Retrieving all products");

        List<Product> products = productRepository.findAll();

        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return new ProductResponse(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice());
    }

    @Override
    public String updateProduct(String id, ProductRequest productRequest) {
        log.debug("Updating product with id {}", id);

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Product product = mongoTemplate.findOne(query, Product.class);

        if(product != null) {
            product.setName(productRequest.name());
            product.setDescription(productRequest.description());
            product.setPrice(productRequest.price());
            productRepository.save(product).getId();
        }

        return id;
    }

    @Override
    public void deleteProduct(String id) {
        log.debug("Deleting product with id {}", id);
        productRepository.deleteById(id);
    }
}
