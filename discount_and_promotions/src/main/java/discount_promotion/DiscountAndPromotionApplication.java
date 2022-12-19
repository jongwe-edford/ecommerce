package discount_promotion;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@EnableEurekaClient
@EnableAsync
public class DiscountAndPromotionApplication {
    public static void main(String[] args) {
        run(DiscountAndPromotionApplication.class, args);
    }
}
