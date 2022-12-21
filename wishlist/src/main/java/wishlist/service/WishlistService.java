package wishlist.service;

import wishlist.exception.AlreadyInWishlist;
import wishlist.model.Wishlist;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;

public interface WishlistService {
    Flux<Wishlist> findAllProductsByCustomerId(HttpServletRequest httpServletRequest);

    Mono<?> addProductToWishlist(long productId, HttpServletRequest httpServletRequest) throws AlreadyInWishlist;

    Mono<Void> deleteAllByEmail(HttpServletRequest httpServletRequest);

    Mono<Void> deleteItemFromWishlist(String  id);
}
