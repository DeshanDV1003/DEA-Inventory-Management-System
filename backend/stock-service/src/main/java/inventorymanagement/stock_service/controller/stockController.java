package inventorymanagement.stock_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/stock")
public class stockController {
    @GetMapping("/test")
    public String testApp(){
        return "Hello!";
    }
}
