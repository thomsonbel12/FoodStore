package FoodStore.FoodStore.Service.Impl;

import FoodStore.FoodStore.Entity.Favorite;
import FoodStore.FoodStore.Repository.FavoriteRepository;
import FoodStore.FoodStore.Service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoriteImpl implements FavoriteService {
    @Autowired
    FavoriteRepository favoriteRepository;
    @Override
    public List<Favorite> getAllFavotieProcByUserId(int id){
        return favoriteRepository.findByUser_UserId(id);
    }

    @Override
    @Transactional
    public void deleteFavoriteById(int id){
        favoriteRepository.deleteByFavoriteId(id);
    }

    @Override
    public void saveFavorite(Favorite favorite) {
        favoriteRepository.save(favorite);
    }
}
