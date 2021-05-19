package lt.sda.store.repo;

import lt.sda.store.model.Product;
import lt.sda.store.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Product findProductPriceByProductName(ProductType product);

    Product findProductByProductName(ProductType product);
}
