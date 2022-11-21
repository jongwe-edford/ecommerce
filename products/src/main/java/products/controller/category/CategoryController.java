package products.controller.category;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import products.model.Category;

import java.util.List;

public interface CategoryController {

    ResponseEntity<Category> saveCategory(Category category, MultipartFile image);

    ResponseEntity<List<Category>> findCategoriesLike(String name);

    ResponseEntity<List<Category>> findAllCategories();

    ResponseEntity<Category> findCategoryById(long id);

}
