package orders.repository;

import orders.model.Wishlist;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface WishlistRepository extends ReactiveMongoRepository<Wishlist, String> {
    Flux<Wishlist> findAllByCustomerId(String id);

    void deleteAllByCustomerId(String id);
}
