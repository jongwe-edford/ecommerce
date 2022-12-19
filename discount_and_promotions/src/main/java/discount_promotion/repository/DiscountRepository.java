package discount_promotion.repository;

import discount_promotion.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DiscountRepository extends JpaRepository<Discount, Long>, JpaSpecificationExecutor<Discount> {
    Discount findDiscountByProductId(long productId);

    Discount findDiscountById(long id);

}