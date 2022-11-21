package orders.model;

import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Product {

    private long id;
    private Long vendorId;
    private String name;
    private Object category;
    private List<Object> tags;
    private List<String> images;
    private List<String> colors;
    private double price;
    private long timeViewed;
    private long timePurchased;
    private long total;
}
