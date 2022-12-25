/*
 * Copyright (c) 2022. No party of this code may be reused without permision from the author Edford Jongwe or from Aphrnx LLC,
 */

package orders.service;

import orders.exception.NotFoundException;
import orders.model.Address;
import orders.model.User;
import orders.payload.request.NewAddressRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AddressService {

    Address getDefaultAddress(HttpServletRequest httpServletRequest);

    Address saveNewAddress(NewAddressRequest addressRequest, HttpServletRequest servletRequest);

    List<Address> findAllCustomerAddresses(HttpServletRequest serverRequest);

    void deleteAddress(long id);

    void setDefaultAddress(long id, HttpServletRequest servletRequest) throws NotFoundException;

    User getUser(HttpServletRequest httpServletRequest);
}
