package media.service.category_image_service;

import media.model.CategoryImage;
import media.model.UserProfileImage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface CategoryImageService {
    String savePhoto(long categoryId, MultipartFile file, HttpServletRequest request) throws IOException;

    String getPhotoUrl(long categoryId,HttpServletRequest request);

    void deletePhoto(long email,HttpServletRequest request);
    ResponseEntity<byte[]> getImageBytes(long categoryId, HttpServletRequest request);
    CategoryImage getPhoto(long categoryId, HttpServletRequest request);

    void updatePhoto(long categoryId,MultipartFile file,HttpServletRequest request) throws IOException;
}
