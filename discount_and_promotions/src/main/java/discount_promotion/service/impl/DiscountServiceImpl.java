package discount_promotion.service.impl;

import discount_promotion.model.Discount;
import discount_promotion.repository.DiscountRepository;
import discount_promotion.service.DiscountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository repository;
    private final RestTemplate restTemplate;

    @Override
    public void addDiscount(long productId, float amount) {

        Discount newDiscount = Discount
                .builder()
                .productId(productId)
                .amount(amount)
                .endDate(Instant.now().plus(1, ChronoUnit.MINUTES))
                .startDate(Instant.now())
                .build();
        repository.save(newDiscount);
        //:Todo update  product info and set on discount true
        String getUrl = "http://PRODUCTS-SERVICE/products/update/discount/" + productId + "?status=" + true + "&amount=" + amount;
        Integer userResponseEntity = restTemplate.getForObject(getUrl, Integer.class);

    }

    @Override
    public boolean validate(long discountId) {
        Discount discount = repository.findDiscountById(discountId);

        return Instant.now().compareTo(discount.getEndDate()) > 0;
    }
}
