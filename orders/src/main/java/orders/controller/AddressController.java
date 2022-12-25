/*
 * Copyright (c) 2022. No party of this code may be reused without permision from the author Edford Jongwe or from Aphrnx LLC,
 */

package orders.controller;

import lombok.AllArgsConstructor;
import orders.exception.NotFoundException;
import orders.model.Address;
import orders.payload.request.NewAddressRequest;
import orders.service.AddressService;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "orders/addresses")
@AllArgsConstructor
public class AddressController {
    private final AddressService addressService;


    @PostMapping(path = "create")
    ResponseEntity<Address> saveAddress(@RequestBody NewAddressRequest newAddressRequest, HttpServletRequest servletRequest) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(30, TimeUnit.DAYS))
                .body(addressService.saveNewAddress(newAddressRequest, servletRequest));
    }

    @GetMapping(path = "c/all")
    public ResponseEntity<List<Address>> getAllCustomerAddresses(HttpServletRequest serverRequest) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(30, TimeUnit.DAYS))
                .body(addressService.findAllCustomerAddresses(serverRequest));
    }

    @DeleteMapping(path = "delete/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable("id") long addressId) {
        addressService.deleteAddress(addressId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(path = "update/{id}")
    public ResponseEntity<?> setDefaultAddress(@PathVariable("id") long id, HttpServletRequest servletRequest) throws NotFoundException {
        addressService.setDefaultAddress(id, servletRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path = "default")
    public ResponseEntity<Address> getDefaultAddress(HttpServletRequest servletRequest) {
        return ResponseEntity.ok(addressService.getDefaultAddress(servletRequest));
    }

}
