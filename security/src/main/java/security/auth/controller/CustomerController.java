/*
 * Copyright (c) 2022. No party of this code may be reused without permision from the author Edford Jongwe or from Aphrnx LLC,
 */

package security.auth.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import security.auth.exception.PhoneNumberAlreadyInUseException;
import security.auth.exception.VendorAlreadyExistsException;
import security.auth.exception.VendorNotFoundException;
import security.auth.payload.request.CustomerRegistrationRequest;
import security.auth.payload.request.CustomerUpdateRequest;
import security.auth.payload.response.Response;
import security.auth.service.CustomerService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "customer")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @PostMapping(path = "create")
    public ResponseEntity<Response> saveCustomer(CustomerRegistrationRequest customerRegistrationRequest, HttpServletRequest httpServletRequest) throws VendorAlreadyExistsException, PhoneNumberAlreadyInUseException {
        return new ResponseEntity<>(customerService.saveCustomer(customerRegistrationRequest, httpServletRequest), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Response> retrieveCustomer() throws VendorNotFoundException {
        return new ResponseEntity<>(customerService.retrieveCustomer(), HttpStatus.OK);
    }

    @PatchMapping(path = "update")
    public ResponseEntity<Response> updateCustomerInfo(@RequestBody CustomerUpdateRequest customerUpdateRequest) throws VendorNotFoundException {
        return new ResponseEntity<>(customerService.updateCustomerInfo(customerUpdateRequest), HttpStatus.CREATED);
    }

}
