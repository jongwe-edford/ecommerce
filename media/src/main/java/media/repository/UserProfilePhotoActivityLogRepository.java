package media.repository;


import media.model.UserProfileActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProfilePhotoActivityLogRepository extends JpaRepository<UserProfileActivityLog, Long> {
    List<UserProfileActivityLog> findAllByEmail(String email);
    void deleteByEmail(String email);
}