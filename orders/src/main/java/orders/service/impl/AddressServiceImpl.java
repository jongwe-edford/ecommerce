/*
 * Copyright (c) 2022. No party of this code may be reused without permision from the author Edford Jongwe or from Aphrnx LLC,
 */

package orders.service.impl;

import lombok.AllArgsConstructor;
import orders.exception.NotFoundException;
import orders.model.Address;
import orders.model.User;
import orders.payload.request.NewAddressRequest;
import orders.repository.AddressRepository;
import orders.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final RestTemplate restTemplate;
    private final AddressRepository addressRepository;

    @Override
    public Address getDefaultAddress(HttpServletRequest httpServletRequest) {
        User user = getUser(httpServletRequest);
        return addressRepository.findAllByEmail(user.getEmail())
                .stream()
                .findFirst()
                .filter(Address::isDefault)
                .get();

    }

    @Override
    public Address saveNewAddress(NewAddressRequest addressRequest, HttpServletRequest servletRequest) {
        User user = getUser(servletRequest);
        Address address = Address
                .builder()
                .city(addressRequest.city())
                .country(addressRequest.country())
                .email(Objects.requireNonNull(user.getEmail()))
                .phoneNumber(addressRequest.phoneNumber())
                .state(addressRequest.state())
                .streetAddress(addressRequest.streetAddress())
                .postalCode(addressRequest.postalCode())
                .build();
        if (addressRepository.count() <= 0) {
            address.setDefault(true);
        }
        return addressRepository.save(address);
    }

    @Override
    public List<Address> findAllCustomerAddresses(HttpServletRequest serverRequest) {
        User user = getUser(serverRequest);
        return addressRepository.findAllByEmail(Objects.requireNonNull(user.getEmail()));
    }

    @Override
    public void deleteAddress(long id) {
        addressRepository.deleteById(id);
    }

    @Override
    public void setDefaultAddress(long id, HttpServletRequest servletRequest) throws NotFoundException {
        User user = getUser(servletRequest);
        Iterable<Address> addresses = addressRepository.findAllByEmail(user.getEmail());
        for (Address a : addresses) {
            a.setDefault(false);
        }
        addressRepository.saveAll(addresses);

        System.out.println(addressRepository.findAllByEmail(user.getEmail()));


        Address address = addressRepository.findById(id).orElseThrow(() -> new NotFoundException("No item found"));
        assert address != null;
        address.setDefault(true);

        addressRepository.save(address);

    }

    private User getUser(HttpServletRequest httpServletRequest) {
        System.out.println("Authorization header " + httpServletRequest.getHeader("Authorization"));
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        String getUrl = "http://SECURITY/auth/current-user?token=" + token;
        ResponseEntity<User> userResponseEntity = restTemplate.getForEntity(getUrl, User.class);

        return userResponseEntity.getBody();
    }
}
