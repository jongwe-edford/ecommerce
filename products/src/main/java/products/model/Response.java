package products.model;

import java.time.LocalDateTime;
import java.util.Map;

public record Response(String message, Map<String ,Object> data, LocalDateTime timestamp) {
}
