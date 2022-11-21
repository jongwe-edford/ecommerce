package media.repository;

import media.model.CategoryImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryImageRepository extends JpaRepository<CategoryImage, Long>, JpaSpecificationExecutor<CategoryImage> {
    Boolean existsByCategoryId(long categoryId);
    void deleteByCategoryId(long id);
    CategoryImage  findCategoryImageByCategoryId(Long id);
}