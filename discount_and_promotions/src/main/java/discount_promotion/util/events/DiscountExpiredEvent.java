package discount_promotion.util.events;

import discount_promotion.model.Discount;
import discount_promotion.repository.DiscountRepository;
import discount_promotion.service.DiscountService;
import org.springframework.web.client.RestTemplate;


public record DiscountExpiredEvent(DiscountService discountService, DiscountRepository discountRepository,
                                   RestTemplate restTemplate) {

    public void disableDiscount() {
        for (Discount d : discountRepository.findAll()) {
            if (discountService.validate(d.getId())) {
                String getUrl = "http://PRODUCTS-SERVICE/products/update/discount/" + d.getProductId() + "?status=" + false + "&amount=" + 0;
                Integer userResponseEntity = restTemplate.getForObject(getUrl, Integer.class);
            }
            return;
        }
    }
}
