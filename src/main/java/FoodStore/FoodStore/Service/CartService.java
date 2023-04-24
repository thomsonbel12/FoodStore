package FoodStore.FoodStore.Service;

import FoodStore.FoodStore.Entity.Cart;

import java.util.List;

public interface CartService {
    List<Cart> getAllCartByUserId(int id);

    void saveCart(Cart cart);
    void deleteCartByCartId(int cartId);

    Cart getCartByProductsId(int procId);
}
