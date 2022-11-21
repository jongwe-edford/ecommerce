package products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import products.model.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByProductId(long productId);
}
