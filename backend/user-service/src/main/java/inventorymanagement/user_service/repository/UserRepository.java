package inventorymanagement.user_service.repository;

import inventorymanagement.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User getUserById(Long id);
}
