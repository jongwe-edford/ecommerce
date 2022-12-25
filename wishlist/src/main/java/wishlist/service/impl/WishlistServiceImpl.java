package wishlist.service.impl;

import lombok.AllArgsConstructor;
import wishlist.exception.AlreadyInWishlist;
import wishlist.model.User;
import wishlist.model.Wishlist;
import wishlist.repository.WishlistRepository;
import wishlist.service.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Objects;

@AllArgsConstructor
@Service
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final RestTemplate restTemplate;


    @Override
    public Flux<Wishlist> findAllProductsByCustomerId(HttpServletRequest httpServletRequest) {
        System.out.println("Authorization header " + httpServletRequest.getHeader("Authorization"));
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        String getUrl = "https://SECURITY/auth/current-user?token=" + token;
        ResponseEntity<User> userResponseEntity = restTemplate.getForEntity(getUrl, User.class);

        return wishlistRepository.findAllByCustomerId(String.valueOf(Objects.requireNonNull(userResponseEntity.getBody()).getEmail()));
    }

    @Override
    public Mono<Wishlist> addProductToWishlist(long productId, HttpServletRequest httpServletRequest) throws AlreadyInWishlist {
        System.out.println("Authorization header " + httpServletRequest.getHeader("Authorization"));
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        String getUrl = "https://SECURITY/auth/current-user?token=" + token;
        ResponseEntity<User> userResponseEntity = restTemplate.getForEntity(getUrl, User.class);
        Wishlist wishlist = Wishlist
                .builder()
                .addedOn(Instant.now())
                .customerId(Objects.requireNonNull(userResponseEntity.getBody()).getEmail())
                .productId(productId)
                .build();
        Mono<Boolean> existsByCustomerIdAndProductId = wishlistRepository.existsByCustomerIdAndProductId(userResponseEntity.getBody().getEmail(), productId);
        if (existsByCustomerIdAndProductId.block())
            throw new AlreadyInWishlist("Already in wishlist!");
        return Objects.requireNonNull(wishlistRepository.save(wishlist));
    }

    @Override
    public Mono<Void> deleteAllByEmail(HttpServletRequest httpServletRequest) {
        System.out.println("Authorization header " + httpServletRequest.getHeader("Authorization"));
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        String getUrl = "https://SECURITY/auth/current-user?token=" + token;
        ResponseEntity<User> userResponseEntity = restTemplate.getForEntity(getUrl, User.class);
        wishlistRepository.deleteAllByCustomerId(Objects.requireNonNull(userResponseEntity.getBody()).getEmail());
        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteItemFromWishlist(String  id) {
        return wishlistRepository.deleteById(id);
    }
}
