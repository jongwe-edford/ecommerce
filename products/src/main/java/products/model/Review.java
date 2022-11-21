package products.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(
        name = "reviews", indexes = {
        @Index(name = "revies_table_index", columnList = "user"),
        @Index(name = "idx_review_id", columnList = "id")}
)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_id_sequence_generator")
    @SequenceGenerator(sequenceName = "review_id_sequence", name = "review_id_sequence_generator")
    private Long id;
    private String user;
    private long productId;
    private int likes;
    private String message;
}
