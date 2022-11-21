package products.model;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(
        name = "categories", indexes = {
        @Index(name = "idx_category_id", columnList = "id")}
)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_sequence_generator")
    @SequenceGenerator(sequenceName = "category_id_sequence", name = "category_id_sequence_generator")
    private Long id;
    private String name;
    private String imageUrl;
    private int numberVisited;

}
