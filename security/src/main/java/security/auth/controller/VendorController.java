package security.auth.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import security.auth.exception.PhoneNumberAlreadyInUseException;
import security.auth.exception.VendorAlreadyExistsException;
import security.auth.exception.VendorNotFoundException;
import security.auth.payload.request.VendorRegistrationRequest;
import security.auth.payload.request.VendorUpdateRequest;
import security.auth.payload.response.Response;
import security.auth.service.VendorService;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
@RequestMapping(path = "vendor")
public class VendorController {

    private final VendorService vendorService;

    @PostMapping("create")
    public ResponseEntity<Response> createVendorAccount(
            @RequestPart("vendor") VendorRegistrationRequest vendorRegistrationRequest,
            HttpServletRequest httpServletRequest,
            @RequestHeader("Authorization") String token,
            @RequestPart("file") MultipartFile file) throws VendorAlreadyExistsException, PhoneNumberAlreadyInUseException {
        return new ResponseEntity<>(vendorService.registerVendor(vendorRegistrationRequest, httpServletRequest, token, file), HttpStatus.CREATED);
    }

    @PatchMapping("update")
    public ResponseEntity<Response> updateVendorInfo(@RequestBody VendorUpdateRequest vendorRegistrationRequest) throws VendorNotFoundException {
        return new ResponseEntity<>(vendorService.updateVendorInfo(vendorRegistrationRequest), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Response> retrieveVendorInfo(@RequestHeader("Authorization") String token) throws VendorNotFoundException {
        return new ResponseEntity<>(vendorService.retrieveVendorInfo(token), HttpStatus.OK);
    }


    @GetMapping("/id")
    public ResponseEntity<Response> retrieveVendorId(@RequestParam("token") String  token) throws VendorNotFoundException {
        return new ResponseEntity<>(vendorService.getVendorId(token), HttpStatus.OK);
    }

}
