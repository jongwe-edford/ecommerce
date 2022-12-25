package products.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import products.model.Category;
import products.repository.CategoryRepository;
import products.service.CategoryService;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final RestTemplate restTemplate;

    @Override
    public Category saveCategory(Category category, MultipartFile image) {
        Category before = categoryRepository.save(category);
        String imageUrl = saveImageToDb(image, category.getId());
        before.setImageUrl(imageUrl);
        return categoryRepository.save(before);
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> findAllCategoriesLike(String name) {
        return categoryRepository.findAllByNameIsLike(name);
    }

    @Override
    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    private String saveImageToDb(MultipartFile file, long categoryId) {
        String postUrl = "https://MEDIA-SERVICE/media/images/category/save?categoryId={categoryId}";
        String getUrl = "https://MEDIA-SERVICE/media/images/category/c?categoryId=" + categoryId;

        //Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        // multipart form body
        Resource imageResource = file.getResource();

        LinkedMultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("file", imageResource);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(parts, httpHeaders);

        restTemplate.postForEntity(postUrl, httpEntity, String.class, categoryId);
        String imageUrl = restTemplate.getForObject(getUrl, String.class);
        System.out.print("Image url:::===> %s \n" + imageUrl);

        return imageUrl;
    }
}
