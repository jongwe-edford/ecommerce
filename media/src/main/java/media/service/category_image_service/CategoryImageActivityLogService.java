package media.service.category_image_service;


import media.model.CategoryImageActivityLog;
import media.model.UserProfileActivityLog;

public interface CategoryImageActivityLogService {
    void saveActivity(CategoryImageActivityLog categoryImageActivityLog);
}
