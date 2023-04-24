package FoodStore.FoodStore.Repository;

import FoodStore.FoodStore.Entity.Category_Product;
import FoodStore.FoodStore.Entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.jpa.repository.*;
import java.util.List;

public interface ProductsRepository extends JpaRepository<Products, Long> {


//    List<Products> findAllByProductCategory(List<Category_Product> category_products);
//    List<Products> findAll();

        List<Products> findByProductsName(String name);

        Products findByProductsId(int id);

        List<Products> findByProductsNameContainingIgnoreCase(String name);

        List<Products> findTop8ByProductCategoryIn(List<Category_Product> category_products);
        Page<Products> findByProductsNameContainingIgnoreCase(String name, Pageable pageable);


        Page<Products> findByProductCategoryIn(List<Category_Product> category_products, Pageable pageable);

        Page<Products> findAllByProductCategoryIsIn(List<Category_Product> category_products, Pageable pageable);

        Page<Products> findDistinctByProductsNameContainingIgnoreCaseOrProductCategoryIn(String name , List<Category_Product> category_products,Pageable pageable);

}