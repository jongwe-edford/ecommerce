package orders.service;

import orders.model.Wishlist;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WishlistService {
    Flux<Wishlist> findAllByEmail(String email);

    Mono<Wishlist> addProductToWishlist(long productId, String authorizationHeader);

    void deleteAllByEmail(String email);
}
