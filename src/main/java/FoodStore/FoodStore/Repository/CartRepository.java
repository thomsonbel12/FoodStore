package FoodStore.FoodStore.Repository;

import FoodStore.FoodStore.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUser_UserId(int id);
    void deleteByCartId(int id);

    Cart findByProducts_ProductsId(int procId);
}