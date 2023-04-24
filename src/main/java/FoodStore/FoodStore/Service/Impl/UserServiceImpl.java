package FoodStore.FoodStore.Service.Impl;

import FoodStore.FoodStore.Entity.User;
import FoodStore.FoodStore.Repository.UserRepository;
import FoodStore.FoodStore.Service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        super();
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user){
        return userRepository.save(user);
    }


    @Override
    public User getUserByLoginName(String name){
        return userRepository.findByUserName(name);
//        return new User();
    }

    @Override
    public User getUserByEmail(String mail){
        return userRepository.findByUserEmail(mail);
    }
}
