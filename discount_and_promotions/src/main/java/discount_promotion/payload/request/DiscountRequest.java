package discount_promotion.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@AllArgsConstructor
@Data
public class DiscountRequest {
    private double amount;
}
