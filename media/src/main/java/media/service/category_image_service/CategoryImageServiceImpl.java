package media.service.category_image_service;

import lombok.AllArgsConstructor;
import media.model.CategoryImage;
import media.model.CategoryImageActivityLog;
import media.model.enums.ActivityType;
import media.repository.CategoryImageRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CategoryImageServiceImpl implements CategoryImageService {
    private final CategoryImageRepository categoryImageRepository;
    private final CategoryImageActivityLogService categoryImageActivityLogService;

    @Override
    public String savePhoto(long categoryId, MultipartFile file, HttpServletRequest request) throws IOException {
        if (categoryImageRepository.existsByCategoryId(categoryId))
            deletePhoto(categoryId, request);

        CategoryImage userProfileImage = CategoryImage
                .builder()
                .categoryId(categoryId)
                .imageBytes(file.getBytes())
                .name(file.getOriginalFilename())
                .postedOn(LocalDateTime.now())
                .type(file.getContentType())
                .build();
        categoryImageRepository.save(userProfileImage);
        CategoryImageActivityLog userProfileActivityLog =
                CategoryImageActivityLog
                        .builder()
                        .categoryId(categoryId)
                        .activityType(ActivityType.UPLOAD)
                        .timestamp(Timestamp.from(Instant.now()))
                        .ipAddress(request.getRemoteAddr())
                        .build();
        categoryImageActivityLogService.saveActivity(userProfileActivityLog);

        return "Photo uploaded successfully";
    }

    @Override
    public String getPhotoUrl(long categoryId, HttpServletRequest request) {
        CategoryImageActivityLog userProfileActivityLog =
                CategoryImageActivityLog
                        .builder()
                        .categoryId(categoryId)
                        .activityType(ActivityType.READ)
                        .timestamp(Timestamp.from(Instant.now()))
                        .ipAddress(request.getRemoteAddr())
                        .build();
        categoryImageActivityLogService.saveActivity(userProfileActivityLog);
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/media/images/category")
                .queryParam("categoryId", categoryId)
                .toUriString();
    }

    @Override
    public void deletePhoto(long email, HttpServletRequest request) {
        categoryImageRepository.deleteByCategoryId(email);
        CategoryImageActivityLog userProfileActivityLog =
                CategoryImageActivityLog
                        .builder()
                        .categoryId(email)
                        .activityType(ActivityType.DELETE)
                        .timestamp(Timestamp.from(Instant.now()))
                        .ipAddress(request.getRemoteAddr())
                        .build();
        categoryImageActivityLogService.saveActivity(userProfileActivityLog);
    }

    @Override
    public ResponseEntity<byte[]> getImageBytes(long categoryId, HttpServletRequest request) {
        CategoryImage categoryImage = getPhoto(categoryId, request);
        byte[] imageBytes = categoryImage.getImageBytes();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + categoryImage.getName() + "\"")
                .contentType(MediaType.parseMediaType(categoryImage.getType()))
                .body(imageBytes);
    }

    @Override
    public CategoryImage getPhoto(long categoryId, HttpServletRequest request) {
        CategoryImage categoryImage = categoryImageRepository.findCategoryImageByCategoryId(categoryId);
        CategoryImageActivityLog userProfileActivityLog =
                CategoryImageActivityLog
                        .builder()
                        .categoryId(categoryId)
                        .activityType(ActivityType.DELETE)
                        .timestamp(Timestamp.from(Instant.now()))
                        .ipAddress(request.getRemoteAddr())
                        .build();
        categoryImageActivityLogService.saveActivity(userProfileActivityLog);
        return categoryImage;
    }

    @Override
    public void updatePhoto(long categoryId, MultipartFile file, HttpServletRequest request) throws IOException {
        deletePhoto(categoryId, request);
        savePhoto(categoryId, file, request);
    }
}
