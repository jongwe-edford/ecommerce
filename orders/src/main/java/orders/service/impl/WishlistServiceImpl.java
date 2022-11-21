package orders.service.impl;

import lombok.AllArgsConstructor;
import orders.model.Wishlist;
import orders.repository.WishlistRepository;
import orders.service.WishlistService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final RestTemplate restTemplate;

    @Override
    public Flux<Wishlist> findAllByEmail(String authHeader) {

        return null;
    }

    @Override
    public Mono<Wishlist> addProductToWishlist(long productId, String authorizationHeader) {
        return null;
    }


    @Override
    public void deleteAllByEmail(String email) {

    }
}
