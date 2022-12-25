package orders.model.product;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Product {
    private long id;
    private int vendorId;
    private String name;
    @OneToOne
    private Category category;
    @ManyToMany
    private List<Tag> tags;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> images;
    @ElementCollection
    private List<String> colors;
    private double price;
    private float discount;
    private boolean onDiscount;
    private long timeViewed;
    private long timePurchased;
    private long total;
}
