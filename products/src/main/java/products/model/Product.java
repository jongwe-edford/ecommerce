package products.model;

import lombok.*;


import javax.persistence.*;
import java.util.List;


@Entity
@Table(
        name = "products", indexes = {
        @Index(name = "product_table_index", columnList = "vendorId"),
        @Index(name = "idx_product_id", columnList = "id")}
)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_sequence_generator")
    @SequenceGenerator(sequenceName = "product_id_sequence", name = "product_id_sequence_generator")
    private long id;
    private Long vendorId;
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
    private long timeViewed;
    private long timePurchased;
    private long total;
}
