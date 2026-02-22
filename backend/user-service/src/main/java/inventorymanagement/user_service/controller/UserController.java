package inventorymanagement.user_service.controller;

import inventorymanagement.user_service.entity.User;
import inventorymanagement.user_service.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value="/apii")
public class UserController {
    @Autowired
    private UserServiceImpl userService;


}
