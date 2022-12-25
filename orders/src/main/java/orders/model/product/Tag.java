package orders.model.product;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(
        name = "tags", indexes = {
        @Index(name = "idx_tag_id", columnList = "id")}
)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_id_sequence_generator")
    @SequenceGenerator(sequenceName = "tag_id_sequence", name = "tag_id_sequence_generator")
    private long id;
    private String value;
}
