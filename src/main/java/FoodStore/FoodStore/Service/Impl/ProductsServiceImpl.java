package FoodStore.FoodStore.Service.Impl;

import FoodStore.FoodStore.Entity.Category_Product;
import FoodStore.FoodStore.Entity.Products;
import FoodStore.FoodStore.Repository.ProductsRepository;
import FoodStore.FoodStore.Service.ProductsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService {


    private final ProductsRepository productsRepository;

    public ProductsServiceImpl(ProductsRepository productsRepository){
        super();
        this.productsRepository = productsRepository;
    }
    @Override
    public List<Products> getAllProducts() {
        return productsRepository.findAll();
    }

    @Override
    public List<Products> getAllProductsByName(String name){
        return productsRepository.findByProductsName(name);
    }

    @Override
    public Products getProductsById(int id) {
        return productsRepository.findByProductsId(id);
    }

    @Override
    public List<Products> getAllProductsBySearchName(String name) {
        return productsRepository.findByProductsNameContainingIgnoreCase(name);
    }
    @Override
    public List<Products> getTop10ProductsByCategory(List<Category_Product> category_products){
        return productsRepository.findTop8ByProductCategoryIn(category_products);
    }

    @Override
    public  Page<Products> getAllProductsByNameAndCategory(String name, List<Category_Product> category_products, Pageable pageable){
        return productsRepository.findDistinctByProductsNameContainingIgnoreCaseOrProductCategoryIn(name, category_products, pageable);
    }
}
