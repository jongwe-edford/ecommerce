package products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import products.model.Product;

import java.util.List;

@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByVendorId(Long shopId);

    @Modifying
    @Query("UPDATE  Product p set p.images=?2 where p.id=?1")
    void updateProductImages(long id, List<String> images);

}
