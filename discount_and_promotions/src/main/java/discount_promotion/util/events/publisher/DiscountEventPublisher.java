package discount_promotion.util.events.publisher;

import discount_promotion.util.events.DiscountExpiredEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DiscountEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public void publishDiscountExpiredEvent(DiscountExpiredEvent discountExpiredEvent) {
        eventPublisher.publishEvent(discountExpiredEvent);
    }
}
