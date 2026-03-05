package inventorymanagement.user_service.service;

import inventorymanagement.user_service.dto.AuthResponseDto;
import inventorymanagement.user_service.dto.LoginRequestDto;
import inventorymanagement.user_service.dto.RegisterRequestDto;
import inventorymanagement.user_service.entity.User;

import java.util.List;

public interface UserService {
    public String registerUser(RegisterRequestDto request);
    public AuthResponseDto loginUser(LoginRequestDto request);
    public boolean validateToken(String token);
    public List<User> getAllUsers();
    public String addUser(RegisterRequestDto request);
    public void deleteUser(Long id);
}
