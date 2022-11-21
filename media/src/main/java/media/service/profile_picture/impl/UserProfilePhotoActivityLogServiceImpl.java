package media.service.profile_picture.impl;


import lombok.AllArgsConstructor;
import media.model.UserProfileActivityLog;
import media.repository.UserProfilePhotoActivityLogRepository;
import media.service.profile_picture.UserProfilePhotoActivityLogService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserProfilePhotoActivityLogServiceImpl implements UserProfilePhotoActivityLogService {
    private final UserProfilePhotoActivityLogRepository userProfilePhotoActivityLogRepository;
    @Override
    public void saveActivity(UserProfileActivityLog shopBannerActivityLog) {
        userProfilePhotoActivityLogRepository.save(shopBannerActivityLog);
    }
}
