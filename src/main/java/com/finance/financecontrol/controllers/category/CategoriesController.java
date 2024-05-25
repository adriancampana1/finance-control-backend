package com.finance.financecontrol.controllers.category;

import com.finance.financecontrol.domain.category.Category;
import com.finance.financecontrol.domain.category.CategoryDTO;
import com.finance.financecontrol.exceptions.ResourceNotFoundException;
import com.finance.financecontrol.repositories.CategoryRepository;
import com.finance.financecontrol.repositories.UserRepository;
import com.finance.financecontrol.services.AuthenticatedUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("categories")
public class CategoriesController {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

    private final AuthenticatedUserService authenticatedUserService;

    @Autowired
    public CategoriesController(AuthenticatedUserService authenticatedUserService) {
        this.authenticatedUserService = authenticatedUserService;
    }

    @PostMapping("/new")
    public ResponseEntity create(@RequestBody @Valid CategoryDTO data){
        String userId = authenticatedUserService.getCurrentUserId();
        Category newCategory = new Category(data.title(), data.description(), userId);

        this.categoryRepository.save(newCategory);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        String userId = authenticatedUserService.getCurrentUserId();
        List<Category> userCategories = categoryRepository.findByUserId(userId);
        List<CategoryDTO> userCategoryDTOs = userCategories.stream().map(category -> new CategoryDTO(category.getId(), category.getTitle(), category.getDescription(), category.getUserId())).collect(Collectors.toList());
        return ResponseEntity.ok(userCategoryDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable String id){
        String userId = authenticatedUserService.getCurrentUserId();
        Category category = categoryRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        CategoryDTO categoryDTO = new CategoryDTO(category.getId(), category.getTitle(), category.getDescription(), category.getUserId());

        return ResponseEntity.ok(categoryDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable String id, @RequestBody CategoryDTO data){
        String userId = authenticatedUserService.getCurrentUserId();
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found!"));

        if(!category.getUserId().equals(userId)){
            return ResponseEntity.badRequest().build();
        }

        if(!(data.title().isEmpty())){category.setTitle(data.title());}
        if(!(data.description().isEmpty())){category.setDescription(data.description());}
        categoryRepository.save(category);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id){
        String userId = authenticatedUserService.getCurrentUserId();
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found!"));

        if(!category.getUserId().equals(userId)){
            return ResponseEntity.badRequest().build();
        }

        categoryRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
