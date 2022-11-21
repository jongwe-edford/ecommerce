package products.service;

import org.springframework.http.HttpRequest;
import products.model.ProductLog;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ProductLogService {
    void log(long productId, String userId, HttpServletRequest httpRequest);
    List<ProductLog> findAllLogsByProductId(Long productId);
    List<ProductLog> findAllLogsByUserId(String userId);
}
