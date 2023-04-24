package FoodStore.FoodStore.Repository;

import FoodStore.FoodStore.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
        User findByUserName(String name);

        User findByUserEmail(String email);
}