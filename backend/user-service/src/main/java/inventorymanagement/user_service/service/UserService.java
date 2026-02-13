package inventorymanagement.user_service.service;

import inventorymanagement.user_service.entity.User;
import inventorymanagement.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void insertUser(User user){
        userRepository.save(user);
    }
    public User getUserById(Long id){
        return userRepository.getUserById(id);
    }
    public List<User> getUsers(){
        return userRepository.findAll();
    }
    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }
}
