package security;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import security.auth.model.Role;
import security.auth.model.RoleEnum;
import security.auth.repository.RoleRepository;

import java.util.List;

@SpringBootApplication
@EnableEurekaClient
@AllArgsConstructor
public class SecurityApplication {
    private final RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner() {
        return args -> {
            roleRepository.saveAll(List.of(new Role(RoleEnum.CUSTOMER), new Role(RoleEnum.VENDOR), new Role(RoleEnum.SYSADMIN), new Role(RoleEnum.SHOP_MANAGER)));
        };
    }
}
