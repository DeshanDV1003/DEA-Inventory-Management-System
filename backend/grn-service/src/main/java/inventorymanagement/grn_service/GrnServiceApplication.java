package inventorymanagement.grn_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GrnServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GrnServiceApplication.class, args);
    }
}