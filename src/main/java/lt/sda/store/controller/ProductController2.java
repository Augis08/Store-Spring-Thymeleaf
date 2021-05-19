package lt.sda.store.controller;

import lt.sda.store.model.ProductType;
import lt.sda.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController2 {
    private final ProductService productService;

    @Autowired
    public ProductController2(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getAllProductsWithPrice(Model model){
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("productsBasket", productService.getProductsBasket());
        return "products";
    }

    @GetMapping(value = "/{productName}/{amount}")
    public String addProductToBasket(@PathVariable ProductType productName, @PathVariable Long amount){
        productService.addProductToBasket(productName, amount);
        return "redirect:/products";
    }

    @GetMapping(value = "/basket/remove/{productName}")
    public String removeProductToBasket(@PathVariable ProductType productName){
        productService.removeProductFromBasket(productName);
        return "redirect:/products/basket";
    }

    @GetMapping(value = "/basket")
    public String getProductBasket(Model model){
        model.addAttribute("basket", productService.getProductsBasket());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("totalPrice", productService.calculateTotalPrice());
        return "productsBasket";
    }

    @GetMapping(value = "/buy")
    public String buyProducts(){
        productService.updateProductStock(productService.getProductsBasket());
        productService.clearProductsBasket();
        return "redirect:/products";
    }
}
