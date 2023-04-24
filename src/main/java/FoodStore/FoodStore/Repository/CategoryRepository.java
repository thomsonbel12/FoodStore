package FoodStore.FoodStore.Repository;

import FoodStore.FoodStore.Entity.Category;
import FoodStore.FoodStore.Entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryId(int id);

}