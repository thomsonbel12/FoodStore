package FoodStore.FoodStore.Service;

import FoodStore.FoodStore.Entity.Favorite;

import java.util.List;

public interface FavoriteService {
    List<Favorite> getAllFavotieProcByUserId(int id);

    void deleteFavoriteById(int id);

    void saveFavorite(Favorite favorite);
}
