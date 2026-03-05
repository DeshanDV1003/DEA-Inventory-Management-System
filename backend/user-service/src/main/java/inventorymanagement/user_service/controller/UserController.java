package inventorymanagement.user_service.controller;

import inventorymanagement.user_service.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api/v1/users")
public class UserController {
    @Autowired
    private UserServiceImpl userService;


}
