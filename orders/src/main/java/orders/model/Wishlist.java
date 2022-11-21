package orders.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;

@Document(collection = "wishlist")
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class Wishlist {
    @MongoId(value = FieldType.INT64)
    @Id
    private String id;
    private String email;
    private long productId;
    private LocalDate timestamp;
}
