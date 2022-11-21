package media.controller;

import lombok.AllArgsConstructor;
import media.service.category_image_service.CategoryImageService;
import media.service.profile_picture.UserProfilePhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping(path = "media/images/category")
@AllArgsConstructor
public class CategoryImageController {
    private final CategoryImageService userProfilePhotoService;

    @PostMapping(path = "save")
    public ResponseEntity<String> postImage(@RequestParam("categoryId") long categoryId, @RequestPart(value = "file") MultipartFile file, HttpServletRequest request) throws IOException {
        return new ResponseEntity<>(userProfilePhotoService.savePhoto(categoryId, file, request), HttpStatus.OK);
    }

    @GetMapping(path = "c")
    public ResponseEntity<String> getImageUrl(@RequestParam("categoryId") long id, HttpServletRequest request) {
        return new ResponseEntity<>(userProfilePhotoService.getPhotoUrl(id, request), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<byte[]> getImage(@RequestParam("categoryId") long id, HttpServletRequest request) {
        return userProfilePhotoService.getImageBytes(id, request);
    }
}
