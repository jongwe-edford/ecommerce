package wishlist.repository;

import wishlist.model.Wishlist;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WishlistRepository extends ReactiveMongoRepository<Wishlist, String> {
    Flux<Wishlist> findAllByCustomerId(String id);

    void deleteAllByCustomerId(String id);

    Mono<Boolean> existsByCustomerIdAndProductId(String customerId, long productId);
}
