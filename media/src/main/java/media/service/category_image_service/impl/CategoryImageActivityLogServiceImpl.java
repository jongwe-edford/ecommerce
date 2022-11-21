package media.service.category_image_service.impl;

import lombok.AllArgsConstructor;
import media.model.CategoryImageActivityLog;
import media.repository.CategoryImageActivityLogRepository;
import media.service.category_image_service.CategoryImageActivityLogService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryImageActivityLogServiceImpl implements CategoryImageActivityLogService {
    private  final CategoryImageActivityLogRepository categoryImageActivityLogRepository;
    @Override
    public void saveActivity(CategoryImageActivityLog categoryImageActivityLog) {
        categoryImageActivityLogRepository.save(categoryImageActivityLog);
    }
}
