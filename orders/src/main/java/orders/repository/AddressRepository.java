/*
 * Copyright (c) 2022. No party of this code may be reused without permision from the author Edford Jongwe or from Aphrnx LLC,
 */

package orders.repository;

import orders.model.Address;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {


    List<Address> findAllByEmail(String email);
}
