package orders.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import orders.model.Product;
import orders.model.User;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WishlistResponse {
    private Product product;
    private User user;
    private long id;
}
