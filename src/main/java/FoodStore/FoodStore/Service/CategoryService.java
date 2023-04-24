package FoodStore.FoodStore.Service;

import FoodStore.FoodStore.Entity.Category;

import java.util.List;

public interface CategoryService {
    Category getAllCategoryById(int id);

    List<Category> getAllCategory();
}
