package discount_promotion.controller;

import discount_promotion.repository.DiscountRepository;
import discount_promotion.service.DiscountService;
import discount_promotion.util.events.DiscountExpiredEvent;
import discount_promotion.util.events.publisher.DiscountEventPublisher;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path = "discounts")
@AllArgsConstructor
public class DiscountController {

    private final DiscountService discountService;
    private final DiscountEventPublisher eventPublisher;
    private final DiscountRepository discountRepository;
    private final RestTemplate restTemplate;

    @PostMapping(path = "add/{id}")
    public ResponseEntity<Void> discountStatus(@PathVariable(value = "id") long productId, @RequestParam("amount") float amount) {
        discountService.addDiscount(productId, amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "notify")
    public void publishEvent() {
        DiscountExpiredEvent discountExpiredEvent = new DiscountExpiredEvent(discountService, discountRepository, restTemplate);
        eventPublisher.publishDiscountExpiredEvent(discountExpiredEvent);
    }
}
