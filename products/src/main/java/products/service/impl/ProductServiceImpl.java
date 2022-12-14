package products.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import products.exception.PriceMismatchException;
import products.model.Product;
import products.model.Response;
import products.model.User;
import products.repository.ProductRepository;
import products.service.ProductLogService;
import products.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;
    private final ProductLogService productLogService;

    @Override
    public Product saveProduct(Product product, MultipartFile[] multipartFiles, HttpServletRequest request) {
        Response responseEntity = restTemplate.getForObject("https://SECURITY/vendor/id?token=" + request.getHeader("Authorization").substring(7), Response.class);
        System.out.println("Current vendor id: " + Objects.requireNonNull(responseEntity).data().get("id"));

        product.setVendorId((int) responseEntity.data().get("id"));
        Product before = productRepository.save(product);
        String postUrl = "https://MEDIA-SERVICE/media/images/products/c/" + before.getId();
        // multipart form body
        LinkedMultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        for (MultipartFile file : multipartFiles) {
            Resource resource = file.getResource();
            parts.add("files", resource);
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(parts, httpHeaders);
        restTemplate.postForEntity(postUrl, httpEntity, String.class);

        return before;


    }

    @Override
    public Product findProductById(Long id, HttpServletRequest request) {
        productLogService.log(id, "12", request);
        Product product = productRepository.findById(id)
                .orElseThrow();
        product.setImages(getProductImagesFromMediaService(id));
        return product;
    }

    @Override
    public String reviewProduct(long productId, String authorizationHeader, String message) {

        return "Reviewed successfully";
    }

    @Override
    public List<Product> allProducts(HttpServletRequest request) {
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            productLogService.log(product.getId(), "12", request);
        }
        return products;
    }

    @Override
    public List<Product> allProductsByShopId(int shopId, HttpServletRequest request) {
        System.out.println("Authorization header " + request.getHeader("Authorization"));
        String token = request.getHeader("Authorization").substring(7);
        String getUrl = "https://SECURITY/auth/current-user?token=" + token;
        System.out.println("Url::" + getUrl);
        System.out.println("Token::" + token);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", request.getHeader("Authorization"));
        ResponseEntity<User> userResponseEntity = restTemplate.getForEntity(getUrl, User.class, httpHeaders);
        System.out.println("Current user: " + userResponseEntity.getBody());
        List<Product> products = productRepository.findAllByVendorId(shopId);
        for (Product product : products) {
            productLogService.log(product.getId(), String.valueOf(Objects.requireNonNull(userResponseEntity.getBody()).getId()), request);
            product.setImages(getProductImagesFromMediaService(product.getId()));
        }
        return products;
    }

    @Override
    public int updateProductDiscountStatus(long id, boolean status, float amount) throws PriceMismatchException {
        if (amount >= productRepository.findProductById(id).getPrice())
            throw new PriceMismatchException("Discount value cannot be more than the actual price");
        return productRepository.updateProductDiscountStatus(id, status, amount);
    }


    private List<String> getProductImagesFromMediaService(Long productId) {

        String getUrl = "https://MEDIA-SERVICE/media/images/products/{id}";

        ResponseEntity<String[]> images = restTemplate.getForEntity(getUrl, String[].class, productId);
        List<String> imagesList = Arrays.asList(Objects.requireNonNull(images.getBody()));
        System.out.println("Images list::" + imagesList);
//        productRepository.updateProductImages(before.getId(), imagesList);
        return imagesList;
    }

}
