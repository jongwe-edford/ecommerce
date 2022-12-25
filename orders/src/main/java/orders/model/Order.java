/*
 * Copyright (c) 2022. No party of this code may be reused without permision from the author Edford Jongwe or from Aphrnx LLC,
 */

package orders.model;

import lombok.*;
import orders.model.enums.OrderStatus;
import orders.model.enums.PaymentMethod;

import javax.persistence.*;

@Entity
@Table(name = "orders", indexes = {@Index(name = "idx_address_id", columnList = "id"),
        @Index(name = "idx_email", columnList = "email")})
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Builder
@ToString
public class Order {
    @Id
    private long id;
    private String email;

    private long productId;
    private double amount;
    private int quantity;
    @ManyToOne
    private Address address;
    private String customerId;
    private String vendorId;
    private OrderStatus status;
    private PaymentMethod paymentMethod;

}
