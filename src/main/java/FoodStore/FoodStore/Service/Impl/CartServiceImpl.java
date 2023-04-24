package FoodStore.FoodStore.Service.Impl;

import FoodStore.FoodStore.Entity.Cart;
import FoodStore.FoodStore.Repository.CartRepository;
import FoodStore.FoodStore.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public List<Cart> getAllCartByUserId(int id) {
        return cartRepository.findAllByUser_UserId(id);
    }

    @Override
    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void deleteCartByCartId(int cartId) {
        cartRepository.deleteByCartId(cartId);
    }

    @Override
    public Cart getCartByProductsId(int procId) {
        return cartRepository.findByProducts_ProductsId(procId);
    }
}
