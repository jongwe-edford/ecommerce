package wishlist.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;

@Document(collection = "wishlist")
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Builder
@ToString
public class Wishlist {
    @MongoId(value = FieldType.INT64)
    @Id
    private String id;
    private String customerId;
    private long productId;
    private Instant addedOn;

}
