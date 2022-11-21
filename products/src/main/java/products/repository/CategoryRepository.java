package products.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import products.model.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findAllByNameIsLike(String name);
}
