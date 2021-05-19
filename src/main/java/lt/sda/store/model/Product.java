package lt.sda.store.model;

import lombok.Data;

import javax.imageio.ImageIO;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

@Entity
@Data
@Valid
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ProductType productName;
    private Double productPrice;
    @Min(value = 0, message = "stock can't be negative")
    private Long productStock;
}
