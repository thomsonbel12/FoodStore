package FoodStore.FoodStore.Service.Impl;

import FoodStore.FoodStore.Entity.Category;
import FoodStore.FoodStore.Repository.CategoryRepository;
import FoodStore.FoodStore.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getAllCategoryById(int id) {
        return categoryRepository.findByCategoryId(id);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
}
