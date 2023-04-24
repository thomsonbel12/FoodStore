package FoodStore.FoodStore.Repository;

import FoodStore.FoodStore.Entity.Category_Product;
import FoodStore.FoodStore.Entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Category_ProductRepository extends JpaRepository<Category_Product, Long> {
    @Override
    List<Category_Product> findAll();

//    @Query(value = "select * from foodstore.category_product where category_id = ?1", nativeQuery = true)
    List<Category_Product> findByCategory_CategoryId(int id);

    Page<Category_Product> findAllByCategory_CategoryId(int id, Pageable pageable);

    List<Category_Product> findByCategory_CategoryName(String name);

    List<Category_Product> findByCategory_CategoryNameContainingIgnoreCase(String name);
}
