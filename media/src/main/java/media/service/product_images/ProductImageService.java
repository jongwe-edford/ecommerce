package media.service.product_images;

import media.model.ProductImage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductImageService {
    String saveImages(String productId, MultipartFile[] multipartFiles) throws IOException;

    List<String> productImages(String productId);

    void deleteAllByProductId(String productId);

    ResponseEntity<byte[]> getImageBytes(long productId);

    ProductImage getPhoto(long productId);

}
