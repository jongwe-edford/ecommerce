package security.auth.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import security.auth.config.JwtUtil;
import security.auth.exception.PhoneNumberAlreadyInUseException;
import security.auth.exception.UserNotFoundException;
import security.auth.exception.VendorAlreadyExistsException;
import security.auth.exception.VendorNotFoundException;
import security.auth.model.User;
import security.auth.model.Vendor;
import security.auth.payload.request.VendorRegistrationRequest;
import security.auth.payload.request.VendorUpdateRequest;
import security.auth.payload.response.Response;
import security.auth.repository.UserRepository;
import security.auth.repository.VendorRepository;
import security.auth.service.VendorService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@AllArgsConstructor
@Transactional
public class VendorServiceImpl implements VendorService {
    private final UserRepository userRepository;
    private final VendorRepository vendorRepository;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    @Override
    public Response registerVendor(
            VendorRegistrationRequest vendorRegistrationRequest,
            HttpServletRequest httpServletRequest,
            String token,
            MultipartFile image) throws VendorAlreadyExistsException, PhoneNumberAlreadyInUseException {
        String authToken = token.substring(7);
        String email = jwtUtil.getEmailFromToken(authToken);
        System.out.println("The email is: " + email);
        if (vendorRepository.existsByEmail(email))
            throw new VendorAlreadyExistsException("The provided email is already in use by another account");
        if (vendorRepository.existsByPhoneNumber(vendorRegistrationRequest.getPhone_number()))
            throw new PhoneNumberAlreadyInUseException("The provided phone number is already in use by another account");
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException("No account exist"));

        //Save vendor image to db
        String vendorImage = saveImageToDb(image, email);

        Vendor vendor = Vendor
                .builder()
                .address(vendorRegistrationRequest.getAddress())
                .firstname(vendorRegistrationRequest.getFirstname())
                .lastname(vendorRegistrationRequest.getLastname())
                .phoneNumber(vendorRegistrationRequest.getPhone_number())
                .country(httpServletRequest.getLocale().getDisplayCountry())
                .user(user)
                .shopName(vendorRegistrationRequest.getShopName())
                .createdAt(LocalDateTime.now())
                .registrationIpAddress(httpServletRequest.getRemoteAddr())
                .shopBanner("")
                .email(email)
                .image(vendorImage)
                .build();

        vendorRepository.save(vendor);
        return new Response("Vendor registration successful", Map.of("vendor", vendor), LocalDateTime.now());
    }

    @Override
    public Response retrieveVendorInfo(String token) {
        String authToken = token.substring(7);
        String email = jwtUtil.getEmailFromToken(authToken);
        System.out.println("The email is: " + email);
        Vendor vendor = vendorRepository.findVendorByEmail(email).orElseThrow(() -> new UserNotFoundException("No such account exists"));
        return new Response("Information retrieved successfully", Map.of("vendor", vendor), LocalDateTime.now());
    }

    @Override
    public Response updateVendorInfo(VendorUpdateRequest request) throws VendorNotFoundException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Vendor vendor = vendorRepository.findVendorByEmail(userDetails.getUsername()).orElseThrow(() -> new VendorNotFoundException("No such account exists"));
        vendor.setAddress(request.getAddress());
        vendor.setImage(request.getImage());
        vendor.setPhoneNumber(request.getPhoneNumber());
        vendor.setShopName(request.getShopName());
        vendor.setShopBanner(request.getShopBanner());

        vendorRepository.save(vendor);

        return new Response("Update successful", Map.of("new-vendor", vendor), LocalDateTime.now());
    }

    @Override
    public Response getVendorId(String token) {
        String email = jwtUtil.getEmailFromToken(token);
        System.out.println("The email is: " + email);
        Vendor vendor = vendorRepository.findVendorByEmail(email).orElseThrow(() -> new UserNotFoundException("No such account exists"));
        return new Response("Information retrieved successfully", Map.of("id", vendor.getId()), LocalDateTime.now());
    }


    private String saveImageToDb(MultipartFile file, String email) {
        String postUrl = "http://MEDIA-SERVICE/media/images/profile/save?email={email}";
        String getUrl = "http://MEDIA-SERVICE/media/images/profile/user?email=" + email;

        // multipart form body
        Resource imageResource = file.getResource();

        LinkedMultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("file", imageResource);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(parts, httpHeaders);

        restTemplate.postForEntity(postUrl, httpEntity, String.class, email);
        String imageUrl = restTemplate.getForObject(getUrl, String.class);
        System.out.print("Image url:::===> %s \n" + imageUrl);

        return imageUrl;
    }
}
