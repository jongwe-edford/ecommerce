/*
 * Copyright (c) 2022. No party of this code may be reused without permision from the author Edford Jongwe or from Aphrnx LLC,
 */

package orders.payload.request;

public record NewAddressRequest(String country,
                                String city,
                                String streetAddress,
                                String phoneNumber,
                                String state,
                                String postalCode) {
}
