package products.service.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import products.exception.ReviewNotFoundException;
import products.model.Review;
import products.repository.ReviewRepository;
import products.service.ReviewService;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final RestTemplate restTemplate;

    @Override
    public Review save(String productId, String authorizationHeader, String message) {
//        String token = authorizationHeader.substring(7);
//        String url = "http://SHOP-AUTH-SERVICE/shop/auth/u?token={token}";
//        User user = restTemplate.getForObject(url, User.class, token);
//        Review review = Review
//                .builder()
//                .message(message)
//                .user(user)
//                .productId(productId)
//                .build();
        return null;
    }

    @Override
    public Review reply(String productId, String reviewId, String authorizationHeader, String message) throws ReviewNotFoundException {
        return null;
    }

    @Override
    public void deleteReview(String id) {

    }

    @Override
    public Review findReviewById(Long id) {
        return null;
    }

    @Override
    public Review save(Review review) {
        return null;
    }

    @Override
    public List<Review> reviews() {
        return null;
    }

}
