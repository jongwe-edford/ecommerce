/*
 * Copyright (c) 2022. No party of this code may be reused without permision from the author Edford Jongwe or from Aphrnx LLC,
 */

package orders.service.impl;

import lombok.AllArgsConstructor;
import orders.model.Order;
import orders.model.User;
import orders.model.enums.OrderStatus;
import orders.model.product.Product;
import orders.repository.OrderRepository;
import orders.service.AddressService;
import orders.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private final AddressService addressService;

    @Override
    public String createOrder(HttpServletRequest httpServletRequest, Long productId, int quantity, double amount) {
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("id", productId);
        ResponseEntity<Product> productResponseEntity = restTemplate.getForEntity("http://PRODUCTS-SERVICE/products/d/{id}", Product.class, uriVariables);

        Order order = Order
                .builder()
                .address(addressService.getDefaultAddress(httpServletRequest))
                .amount(amount)
                .quantity(quantity)
                .customerId(addressService.getDefaultAddress(httpServletRequest).getEmail())
                .vendorId(String.valueOf(Objects.requireNonNull(productResponseEntity.getBody()).getVendorId()))
                .status(OrderStatus.PAID)
                .build();

        orderRepository.save(order);

        //:TODO send email to vendor
        //:TODO send email to customer

        return null;
    }

    @Override
    public List<Order> getAllCustomerOrders(HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public List<Order> getAllVendorOrders(HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public String updateOrderStatus(long id, OrderStatus orderStatus) {
        return null;
    }

    private User getUser(HttpServletRequest httpServletRequest) {
        System.out.println("Authorization header " + httpServletRequest.getHeader("Authorization"));
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        String getUrl = "http://SECURITY/auth/current-user?token=" + token;
        ResponseEntity<User> userResponseEntity = restTemplate.getForEntity(getUrl, User.class);

        return userResponseEntity.getBody();
    }

    private Product getProduct(long id, HttpServletRequest servletRequest) {
        return Product.builder().build();
    }
}
