package media.repository;

import media.model.CategoryImageActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryImageActivityLogRepository extends JpaRepository<CategoryImageActivityLog, Long> {
}