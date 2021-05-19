package lt.sda.store.service;

import lt.sda.store.model.Product;
import lt.sda.store.model.ProductType;
import lt.sda.store.repo.ProductRepo;
import lt.sda.store.validator.StockValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepo productRepo;
    private final StockValidator stockValidator;
    private final Map<ProductType, Long> productsBasket;

    @Autowired
    public ProductService(ProductRepo productRepo,
                          ProductRepo productStockRepo,
                          StockValidator stockValidator) {
        this.productRepo = productRepo;
        this.stockValidator = stockValidator;
        this.productsBasket = new HashMap<>();
    }

    public Map<ProductType, Product> getAllProducts() {
        return productRepo.findAll()
                .stream()
                .collect(Collectors.toMap(Product::getProductName, product -> product));
    }

    public Map<ProductType, Double> getAllProductsPrices() {
        return productRepo.findAll()
                .stream()
                .collect(Collectors.toMap(Product::getProductName,
                        Product::getProductPrice));
    }

    public Double getProductPriceByProductType(ProductType product) {
        return productRepo.findProductByProductName(product).getProductPrice();
    }

    public Double calculateTotalPrice() {
        return productsBasket
                .entrySet()
                .stream()
                .mapToDouble((entry) ->
                        getProductPriceByProductType(entry.getKey())
                        * entry.getValue())
                .sum();
    }

    public Double calculateCartPrice(Map<ProductType, Long> shoppingCart) {
        stockValidator.validateStock(shoppingCart);
        return shoppingCart
                .entrySet()
                .stream()
                .mapToDouble(productTypeAndAmount
                        -> getProductPriceByProductType(productTypeAndAmount.getKey())
                        * productTypeAndAmount.getValue())
                .sum();
    }

    public void updateProductStock(Map<ProductType, Long> shoppingCart) {
        stockValidator.validateStock(shoppingCart);
        shoppingCart.forEach((productType, amount) -> {
            Product product = getProductByProductName(productType);
            product.setProductStock(product.getProductStock() - amount);
            productRepo.save(product);
        });
    }

    public Product getProductByProductName(ProductType product) {
        return productRepo.findProductByProductName(product);
    }

    public Map<ProductType, Long> getAllProductsStock() {
        return productRepo.findAll()
                .stream()
                .collect(Collectors.toMap(Product::getProductName,
                        Product::getProductStock));
    }

    public void addProductToBasket(ProductType productName, Long quantity) {
        if (productsBasket.containsKey(productName)) {
            stockValidator.validateAddingProductStock(productName, quantity + productsBasket.get(productName));
            productsBasket.put(productName, productsBasket.get(productName) + quantity);
        } else {
            stockValidator.validateAddingProductStock(productName, quantity);
            productsBasket.put(productName, quantity);
        }
    }

    public void removeProductFromBasket(ProductType productType) {
        productsBasket.remove(productType);
    }

    public Map<ProductType, Long> getProductsBasket() {
        return productsBasket;
    }

    public void clearProductsBasket() {
        productsBasket.clear();
    }
}
