package discount_promotion.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity()
@Table(name = "discounts"
        , indexes = {
        @Index(name = "idx_productId", columnList = "productId"),
        @Index(name = "idx_discountId", columnList = "id"),
}
)
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "discount_sequence_generator", sequenceName = "discount_sequence")
    private long id;
    private long productId;
    private double amount;
    private Instant startDate;
    private Instant endDate;

    public Discount() {

    }
}