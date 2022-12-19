package orders.service;

import orders.model.Wishlist;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;

public interface WishlistService {
    Flux<Wishlist> findAllProductsByCustomerId(String email, HttpServletRequest httpServletRequest);

    Mono<String> addProductToWishlist(long productId,  HttpServletRequest httpServletRequest);

    Mono<Void> deleteAllByEmail(String email, HttpServletRequest httpServletRequest);
}
