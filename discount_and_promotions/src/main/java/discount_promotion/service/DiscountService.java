package discount_promotion.service;

import discount_promotion.payload.request.DiscountRequest;

public interface DiscountService {
    void addDiscount( long productId,float amount);
    boolean validate(long discountId);
}
