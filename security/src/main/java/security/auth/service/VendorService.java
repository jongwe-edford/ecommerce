package security.auth.service;

import org.springframework.web.multipart.MultipartFile;
import security.auth.exception.PhoneNumberAlreadyInUseException;
import security.auth.exception.VendorAlreadyExistsException;
import security.auth.exception.VendorNotFoundException;
import security.auth.payload.request.VendorRegistrationRequest;
import security.auth.payload.request.VendorUpdateRequest;
import security.auth.payload.response.Response;

import javax.servlet.http.HttpServletRequest;

public interface VendorService {
    Response registerVendor(
            VendorRegistrationRequest vendorRegistrationRequest,
            HttpServletRequest httpServletRequest,
            String token,
            MultipartFile image) throws VendorAlreadyExistsException, PhoneNumberAlreadyInUseException;

    Response retrieveVendorInfo(String token) throws VendorNotFoundException;

    Response updateVendorInfo(VendorUpdateRequest vendor) throws VendorNotFoundException;

    Response getVendorId(String token);

}
