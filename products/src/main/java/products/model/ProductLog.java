package products.model;

import lombok.*;


import javax.persistence.*;
import java.time.LocalDateTime;

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
public class ProductLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_id_sequence_generator")
    @SequenceGenerator(sequenceName = "tag_id_sequence", name = "tag_id_sequence_generator")
    private long id;
    private Long productId;
    private String userId;
    private LocalDateTime viewedAt;
    private String ipAddress;
}
