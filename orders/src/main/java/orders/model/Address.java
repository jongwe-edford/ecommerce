/*
 * Copyright (c) 2022. No party of this code may be reused without permision from the author Edford Jongwe or from Aphrnx LLC,
 */

package orders.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "addresses", indexes = {@Index(name = "idx_address_id", columnList = "id"),
        @Index(name = "idx_email", columnList = "email")})
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Builder
@ToString
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_sequence_generator")
    @SequenceGenerator(name = "address_sequence_generator", sequenceName = "address_sequence")
    private long id;
    private String country;
    private String city;
    private String streetAddress;
    private String phoneNumber;
    private String state;
    private String postalCode;
    private String email;
    private boolean isDefault;
}
