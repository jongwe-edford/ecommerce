package wishlist.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wishlist.model.Product;
import wishlist.model.User;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WishlistResponse {
    private Product product;
    private User user;
    private long id;
}
