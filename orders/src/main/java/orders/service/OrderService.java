/*
 * Copyright (c) 2022. No party of this code may be reused without permision from the author Edford Jongwe or from Aphrnx LLC,
 */

package orders.service;

import orders.model.Order;
import orders.model.enums.OrderStatus;
import orders.model.enums.PaymentMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {
    String createOrder(
            HttpServletRequest httpServletRequest,
            Long productId, int quantity,
            PaymentMethod paymentMethod,
            double amount);

    List<Order> getAllCustomerOrders(HttpServletRequest httpServletRequest);

    List<Order> getAllVendorOrders(HttpServletRequest httpServletRequest);

    String updateOrderStatus(long id, OrderStatus orderStatus);
}
