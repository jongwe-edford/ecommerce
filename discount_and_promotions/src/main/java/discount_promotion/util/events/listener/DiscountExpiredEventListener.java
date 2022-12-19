package discount_promotion.util.events.listener;


import discount_promotion.util.events.DiscountExpiredEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DiscountExpiredEventListener {

    @EventListener
    @Async
    public void listenForDiscountExpiredEvent(DiscountExpiredEvent discountExpiredEvent) {
        discountExpiredEvent.disableDiscount();
    }
}
