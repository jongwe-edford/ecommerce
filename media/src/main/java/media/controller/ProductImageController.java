package media.controller;


import lombok.AllArgsConstructor;
import media.service.product_images.ProductImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "media/images/products")
@AllArgsConstructor
public class ProductImageController {
    private final ProductImageService productImageService;

    @PostMapping(path = "c/{productId}")
    public ResponseEntity<String> saveImages(
            @PathVariable("productId") String productId,
            @RequestBody MultipartFile[] files) throws IOException {
        return ResponseEntity.ok(productImageService.saveImages(productId, files));
    }

    @GetMapping(path = "/{productId}")
    public ResponseEntity<List<String>> getProductImagesUrl(@PathVariable("productId") String productId){
        return ResponseEntity.ok(productImageService.productImages(productId));
    }

    @GetMapping(path = "i/{productId}")
    public ResponseEntity<byte[]> getImage(@PathVariable("productId") Long shopId) {
        return productImageService.getImageBytes(shopId);
    }
}
