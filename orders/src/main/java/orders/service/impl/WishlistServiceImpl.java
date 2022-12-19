package orders.service.impl;

import lombok.AllArgsConstructor;
import orders.model.User;
import orders.model.Wishlist;
import orders.repository.WishlistRepository;
import orders.service.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@AllArgsConstructor
@Service
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final RestTemplate restTemplate;


    @Override
    public Flux<Wishlist> findAllProductsByCustomerId(String email, HttpServletRequest httpServletRequest) {
        System.out.println("Authorization header " + httpServletRequest.getHeader("Authorization"));
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        String getUrl = "http://SECURITY/auth/current-user?token=" + token;
        ResponseEntity<User> userResponseEntity = restTemplate.getForEntity(getUrl, User.class);


        return wishlistRepository.findAllByCustomerId(String.valueOf(Objects.requireNonNull(userResponseEntity.getBody()).getId()));
    }

    @Override
    public Mono<String> addProductToWishlist(long productId, HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public Mono<Void> deleteAllByEmail(String email, HttpServletRequest httpServletRequest) {
        return null;
    }
}
