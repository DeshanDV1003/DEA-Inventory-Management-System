package inventorymanagement.user_service.service;

import inventorymanagement.user_service.dto.AuthResponseDto;
import inventorymanagement.user_service.dto.LoginRequestDto;
import inventorymanagement.user_service.dto.RegisterRequestDto;

public interface UserService {
    public String registerUser(RegisterRequestDto request);
    public AuthResponseDto loginUser(LoginRequestDto request);
    public boolean validateToken(String token);
}
