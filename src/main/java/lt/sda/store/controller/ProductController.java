package lt.sda.store.controller;

import lt.sda.store.model.Product;
import lt.sda.store.model.ProductType;
import lt.sda.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
    ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/allProducts")
    public Map<ProductType, Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping(value = "/price")
    public Map<ProductType, Double> getAllProductsPrices() {
        return productService.getAllProductsPrices();
    }

    @GetMapping(value = "/price/{product}")
    public Product getProductByName(@PathVariable ProductType product) {
        return productService.getProductByProductName(product);
    }

    @PostMapping(value = "/cart")
    public Double calculateCartPrice(@RequestBody Map<ProductType, Long> shoppingCart) {
        return productService.calculateCartPrice(shoppingCart);
    }

    @PostMapping(value = "/buy")
    public void buyProducts(@RequestBody Map<ProductType, Long> shoppingCart) {
        productService.updateProductStock(shoppingCart);
    }

    @GetMapping(value = "/stock")
    public Map<ProductType, Long> getAllProductStock(){
        return productService.getAllProductsStock();
    }
}
