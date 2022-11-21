package products.controller.category;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import products.model.Category;
import products.service.CategoryService;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping(path = "categories")
public class CategoryControllerImpl implements CategoryController {
    private final CategoryService categoryService;

    @Override
    @PostMapping(path = "create")
    public ResponseEntity<Category> saveCategory(@RequestPart("category") Category category, @RequestPart("image") MultipartFile image) {

        return new ResponseEntity<>(categoryService.saveCategory(category, image), HttpStatus.CREATED);
    }

    @Override
    @GetMapping(path = "a")
    public ResponseEntity<List<Category>> findCategoriesLike(@RequestParam(value = "name") String name) {
        return ResponseEntity.ok(categoryService.findAllCategoriesLike(name));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<Category>> findAllCategories() {
        return ResponseEntity.ok(categoryService.findAllCategories());
    }

    @Override
    @GetMapping(path = "{categoryId}")
    public ResponseEntity<Category> findCategoryById(@PathVariable("categoryId") long id) {
        return ResponseEntity.ok(categoryService.findCategoryById(id));
    }
}
