package media.service.shop_banner;



import media.model.ShopBannerActivityLog;

import java.util.List;

public interface ShopBannerActivityLogService {
    void saveActivity(ShopBannerActivityLog shopBannerActivityLog);
    List<ShopBannerActivityLog> activitiesList(String shopId);
}
