/*
 * Copyright (c) 2022. No party of this code may be reused without permision from the author Edford Jongwe or from Aphrnx LLC,
 */

package orders.controller;

import lombok.AllArgsConstructor;
import orders.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    public String createOrder(@RequestParam("productId") long productId, HttpServletRequest servletRequest, @RequestParam("quantity") int quantity, @RequestParam("amount") double amount) {
        return orderService.createOrder(servletRequest, productId, quantity, amount);
    }
}
