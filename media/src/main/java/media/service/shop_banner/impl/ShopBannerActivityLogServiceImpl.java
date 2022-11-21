package media.service.shop_banner.impl;

import lombok.AllArgsConstructor;
import media.model.ShopBannerActivityLog;
import media.repository.ShopBannerActivityLogRepository;
import media.service.shop_banner.ShopBannerActivityLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ShopBannerActivityLogServiceImpl implements ShopBannerActivityLogService {
    private final ShopBannerActivityLogRepository activityLogRepository;

    @Override
    public void saveActivity(ShopBannerActivityLog shopBannerActivityLog) {
        activityLogRepository.save(shopBannerActivityLog);
    }

    @Override
    public List<ShopBannerActivityLog> activitiesList(String shopId) {
        return null;
    }
}
