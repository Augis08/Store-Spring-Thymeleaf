package lt.sda.store.validator;

import lt.sda.store.model.ProductType;
import lt.sda.store.repo.ProductRepo;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StockValidator {
    private final ProductRepo productRepo;

    public StockValidator(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public void validateStock(Map<ProductType, Long> shoppingCart) {
        for (ProductType product : shoppingCart.keySet()) {
            if (productRepo.findProductByProductName(product).getProductStock()
                    < shoppingCart.get(product)) {
                throw new RuntimeException("Insufficient quantity of " + product);
            }
        }
    }

    public void validateAddingProductStock(ProductType productType, Long amount) {
        if (productRepo.findProductByProductName(productType).getProductStock()
                < amount) {
            throw new RuntimeException("Insufficient quantity of " + productType);
        }
    }
}
