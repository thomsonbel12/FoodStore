package FoodStore.FoodStore.Service.Impl;

import FoodStore.FoodStore.Entity.Category_Product;
import FoodStore.FoodStore.Repository.Category_ProductRepository;
import FoodStore.FoodStore.Service.Category_ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Catergory_ProductImpl implements Category_ProductService {
    final
    Category_ProductRepository category_productRepository;

    public Catergory_ProductImpl(Category_ProductRepository category_productRepository) {
        this.category_productRepository = category_productRepository;
    }

    @Override
    public List<Category_Product> getAllCategory_Product(){
        return category_productRepository.findAll();
    }

    @Override
    public List<Category_Product> getAllCategory_ProductByCategoryId(int id) {
        return category_productRepository.findByCategory_CategoryId(id);
    }
    @Override
    public List<Category_Product> getAllProductOfCategoryByCategoryName(String name){
        return category_productRepository.findByCategory_CategoryNameContainingIgnoreCase(name);
    }
}
