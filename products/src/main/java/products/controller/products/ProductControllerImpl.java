package products.controller.products;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import products.exception.PriceMismatchException;
import products.model.Product;
import products.service.ProductLogService;
import products.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path = "products")
@AllArgsConstructor
public class ProductControllerImpl implements ProductController {
    private final ProductService productService;
    private final ProductLogService productLogService;

    @Override
    @PostMapping(path = "create")
    public ResponseEntity<Product> createProduct(
            @RequestPart(value = "product") Product product,
            @RequestPart(value = "images") MultipartFile[] multipartFiles,
            HttpServletRequest request) {
        return new ResponseEntity<>(productService.saveProduct(product, multipartFiles, request), HttpStatus.CREATED);
    }

    @Override
    @GetMapping(path = "d/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable(value = "id") long id, HttpServletRequest request) {
        return ResponseEntity.ok(productService.findProductById(id, request));
    }

    @Override
    @PatchMapping(path = "review/{id}")
    public ResponseEntity<String> reviewProduct(
            @PathVariable("id") long productId,
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @RequestBody String message
    ) {
        return ResponseEntity.ok(productService.reviewProduct(productId, authorizationHeader, message));
    }

    @Override
    @GetMapping(path = "all")
    public ResponseEntity<List<Product>> findAllProducts(HttpServletRequest request) {
        return ResponseEntity.ok(productService.allProducts(request));
    }

    @Override
    @GetMapping(path = "all/{productId}")
    public ResponseEntity<List<Product>> findAllProductsByShopId(@PathVariable(value = "productId") int shopId, HttpServletRequest request) {
        return ResponseEntity.ok(productService.allProductsByShopId(shopId, request));
    }

    @GetMapping(path = "/update/discount/{id}")
    @Override
    public ResponseEntity<Integer> updateProductDiscountStatus(
            @PathVariable(value = "id") long id,
            @RequestParam("status") boolean status,
            @RequestParam("amount") float amount) throws PriceMismatchException {
        return new ResponseEntity<>(productService.updateProductDiscountStatus(id, status, amount), HttpStatus.OK);
    }

}
