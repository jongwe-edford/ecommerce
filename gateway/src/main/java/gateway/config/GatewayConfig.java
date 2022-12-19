package gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    private AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route("auth", r -> r.path("/auth/**", "/vendor/**", "/customer/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://SECURITY"))
                .route("products", r -> r.path("/products/**", "/categories/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://PRODUCTS-SERVICE"))
                .route("discounts", r -> r.path("/discounts/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://DISCOUNT-PROMOTION-SERVICE"))
                .build();
    }

}
