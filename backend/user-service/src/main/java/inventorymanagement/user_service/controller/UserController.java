package inventorymanagement.user_service.controller;

import inventorymanagement.user_service.entity.User;
import inventorymanagement.user_service.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value="api")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping(value ="users")
    public List<User> getAllProducts() {
        return userService.getUsers();
    }

    @PostMapping(value = "addUser")
    public String insert(@RequestBody User product) {
        userService.insertUser(product);
        return "User added successfully";
    }

    @DeleteMapping("deleteUser")
    public String delete(@RequestBody Long id) {
        userService.deleteUserById(id);
        return "The user with id: " + id + " has been deleted";
    }

    @GetMapping("getUser")
    public User getProduct(Long id) {
        return userService.getUserById(id);
    }
}
