package FoodStore.FoodStore.Service;

import FoodStore.FoodStore.Entity.User;

public interface UserService {


    User saveUser(User user);
//    User getUserById(int User_id);
    User getUserByLoginName(String User_name);

    User getUserByEmail(String mail);

//    User
}
