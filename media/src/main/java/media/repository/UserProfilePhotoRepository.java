package media.repository;


import media.model.UserProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface UserProfilePhotoRepository extends JpaRepository<UserProfileImage,Long> {
    UserProfileImage findByEmail(String email);
    void  deleteByEmail(String email);
    Boolean existsByEmail(String email);
}
