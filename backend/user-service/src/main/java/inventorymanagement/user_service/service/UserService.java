package inventorymanagement.user_service.service;

import inventorymanagement.user_service.dto.AuthResponse;
import inventorymanagement.user_service.dto.LoginRequest;
import inventorymanagement.user_service.dto.RegisterRequest;
import inventorymanagement.user_service.entity.User;
import inventorymanagement.user_service.repository.UserRepository;
import inventorymanagement.user_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

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

    // Register new user
    public String registerUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setUserType(request.getUserType() != null ? request.getUserType() : "USER");

        userRepository.save(user);
        return "User registered successfully";
    }

    // Login user
    public AuthResponse loginUser(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getUserType());

        return new AuthResponse(token, user.getUsername(), user.getUserType());
    }

    // Validate token (for other services to call)
    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }
}
