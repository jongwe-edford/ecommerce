package orders.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Setter
public class User {
    private Long id;
    private String email;
    @JsonIgnore
    private String password;
    private String country;
    private String registrationIp;
    private boolean isCustomer = false;
    private boolean isVendor = false;
    private boolean isSysAdmin = false;
    private boolean isSysManager = false;
    private LocalDateTime registrationDate;
    private LocalDateTime lastLoginDate;

}
