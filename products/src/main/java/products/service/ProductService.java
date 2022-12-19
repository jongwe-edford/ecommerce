package products.service;

import org.springframework.web.multipart.MultipartFile;
import products.exception.PriceMismatchException;
import products.exception.ReviewNotFoundException;
import products.model.Product;
import products.model.Review;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ProductService {

    Product saveProduct(Product product, MultipartFile[] multipartFiles,HttpServletRequest request);
    Product findProductById(Long id, HttpServletRequest request);
    String reviewProduct(long productId,String authorizationHeader,String message);
    List<Product> allProducts(HttpServletRequest request);
    List<Product> allProductsByShopId(int shopId,HttpServletRequest request);
    int updateProductDiscountStatus(long id,boolean  status,float amount) throws PriceMismatchException;

}
