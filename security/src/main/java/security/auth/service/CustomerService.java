package security.auth.service;

import security.auth.exception.PhoneNumberAlreadyInUseException;
import security.auth.exception.VendorAlreadyExistsException;
import security.auth.exception.VendorNotFoundException;
import security.auth.payload.request.CustomerRegistrationRequest;
import security.auth.payload.request.CustomerUpdateRequest;
import security.auth.payload.response.Response;

import javax.servlet.http.HttpServletRequest;

public interface CustomerService {
    Response saveCustomer(CustomerRegistrationRequest registrationRequest, HttpServletRequest servletRequest) throws VendorAlreadyExistsException, PhoneNumberAlreadyInUseException, PhoneNumberAlreadyInUseException;
     Response retrieveCustomer() throws VendorNotFoundException;
     Response updateCustomerInfo(CustomerUpdateRequest updateRequest) throws VendorNotFoundException;
}
