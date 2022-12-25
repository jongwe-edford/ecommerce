/*
 * Copyright (c) 2022. No party of this code may be reused without permision from the author Edford Jongwe or from Aphrnx LLC,
 */

package orders.model.enums;

public enum OrderStatus {
    PICKED_FOR_DELIVERY(""),
    DELIVERED(""),
    PAID(""),
    EN_ROUTE(""),
    CANCELLED(""),
    WAITING_FOR_PICKUP("");

    String sts;

    OrderStatus(String status) {
        this.sts = status;
    }
}
