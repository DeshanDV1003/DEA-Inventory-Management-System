package inventorymanagement.user_service.service.impl;

import inventorymanagement.user_service.dto.AuthResponseDto;
import inventorymanagement.user_service.dto.LoginRequestDto;
import inventorymanagement.user_service.dto.RegisterRequestDto;
import inventorymanagement.user_service.entity.User;
import inventorymanagement.user_service.repository.UserRepository;
import inventorymanagement.user_service.service.UserService;
import inventorymanagement.user_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String registerUser(RegisterRequestDto request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setDesignation(request.getDesignation());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setUserType(request.getUserType() != null ? request.getUserType() : "USER");
        user.setCompanyId(request.getCompanyId());
        user.setWarehouseId(request.getWarehouseId());

        userRepository.save(user);
        return "User registered successfully";
    }

    @Override
    public AuthResponseDto loginUser(LoginRequestDto request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getUserType());

        return new AuthResponseDto(token, user.getUsername(), user.getUserType());
    }

    @Override
    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }
}