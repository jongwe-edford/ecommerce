package products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import products.model.ProductLog;

import java.util.List;

public interface ProductLogRepository extends JpaRepository<ProductLog, Long> {
    List<ProductLog> findAllByUserId(String userId);

    List<ProductLog> findAllByProductId(long productId);
}
