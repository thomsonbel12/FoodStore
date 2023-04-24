package FoodStore.FoodStore.Repository;

import FoodStore.FoodStore.Entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUser_UserId(int id);

    void deleteByFavoriteId(int id);
}