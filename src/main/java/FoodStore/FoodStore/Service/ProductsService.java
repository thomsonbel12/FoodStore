package FoodStore.FoodStore.Service;

import FoodStore.FoodStore.Entity.Category_Product;
import FoodStore.FoodStore.Entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductsService {
//    List<Products>
    List<Products> getAllProducts();

    List<Products> getAllProductsByName(String name);

    Products getProductsById(int id);

    List<Products> getAllProductsBySearchName(String name);

    List<Products> getTop10ProductsByCategory(List<Category_Product> category_products);

    Page<Products> getAllProductsByNameAndCategory(String name, List<Category_Product> category_products, Pageable pageable);

//    Page<Products> getPageProductByCategoryId(List<Category_Product> category_products, Pageable pageable);
}
