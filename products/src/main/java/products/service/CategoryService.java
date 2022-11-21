package products.service;

import org.springframework.web.multipart.MultipartFile;
import products.model.Category;

import java.util.List;

public interface CategoryService {

    Category saveCategory(Category category, MultipartFile image);

    List<Category> findAllCategories();

    List<Category> findAllCategoriesLike(String name);

    Category findCategoryById(Long id);
}
