package security.auth.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import security.auth.config.JwtUtil;
import security.auth.exception.PhoneNumberAlreadyInUseException;
import security.auth.exception.UserNotFoundException;
import security.auth.exception.VendorAlreadyExistsException;
import security.auth.exception.VendorNotFoundException;
import security.auth.model.Customer;
import security.auth.model.User;
import security.auth.payload.request.CustomerRegistrationRequest;
import security.auth.payload.request.CustomerUpdateRequest;
import security.auth.payload.response.Response;
import security.auth.repository.CustomerRepository;
import security.auth.repository.UserRepository;
import security.auth.service.CustomerService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public Response saveCustomer(CustomerRegistrationRequest registrationRequest, HttpServletRequest servletRequest) throws VendorAlreadyExistsException, PhoneNumberAlreadyInUseException {

        String authToken = servletRequest.getHeader("Authorization").substring(7);
        String email = jwtUtil.getEmailFromToken(authToken);
        System.out.println("The email is: " + email);
        if (customerRepository.existsByEmail(email))
            throw new VendorAlreadyExistsException("The provided email is already in use by another account");
        if (customerRepository.existsByPhoneNumber(registrationRequest.getPhoneNumber()))
            throw new PhoneNumberAlreadyInUseException("The provided phone number is already in use by another account");

        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException("No account found"));

        Customer customer = customerRepository.save(Customer
                .builder()
                .address(registrationRequest.getAddress())
                .email(email)
                .country(servletRequest.getLocale().getDisplayCountry())
                .dob(registrationRequest.getDob())
                .firstname(registrationRequest.getFirstname())
                .lastname(registrationRequest.getLastname())
                .user(user)
                .phoneNumber(registrationRequest.getPhoneNumber())
                .image("")
                .build());
        return new Response("Account created successfully", Map.of("customer", customer), LocalDateTime.now());
    }

    @Override
    public Response retrieveCustomer() throws VendorNotFoundException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer vendor = customerRepository.findCustomerByEmail(userDetails.getUsername()).orElseThrow(() -> new VendorNotFoundException("No such account exists"));
        return new Response("Information retrieved successfully", Map.of("customer", vendor), LocalDateTime.now());
    }

    @Override
    public Response updateCustomerInfo(CustomerUpdateRequest request) throws VendorNotFoundException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer vendor = customerRepository.findCustomerByEmail(userDetails.getUsername()).orElseThrow(() -> new VendorNotFoundException("No such account exists"));
        vendor.setAddress(request.getAddress());
        vendor.setPhoneNumber(request.getPhoneNumber());
        customerRepository.save(vendor);
        return new Response("Update successful", Map.of("new-vendor", vendor), LocalDateTime.now());
    }
}
