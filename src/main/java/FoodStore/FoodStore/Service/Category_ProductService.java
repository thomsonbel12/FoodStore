package FoodStore.FoodStore.Service;

import FoodStore.FoodStore.Entity.Category_Product;

import java.util.List;

public interface Category_ProductService {
    List<Category_Product> getAllCategory_Product();

    List<Category_Product> getAllCategory_ProductByCategoryId(int id);

    List<Category_Product> getAllProductOfCategoryByCategoryName(String name);
}
