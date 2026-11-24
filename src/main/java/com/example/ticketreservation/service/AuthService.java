package com.example.ticketreservation.service;
import com.example.ticketreservation.dto.*;
import com.example.ticketreservation.exception.ApiException;
import com.example.ticketreservation.model.*;
import com.example.ticketreservation.repository.UserRepository;
import com.example.ticketreservation.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class AuthService {
    private final UserRepository users; private final PasswordEncoder encoder; private final JwtService jwt;
    public AuthService(UserRepository users,PasswordEncoder encoder,JwtService jwt) { this.users=users; this.encoder=encoder; this.jwt=jwt; }
    public UserResponse register(RegisterRequest r) {
        if(users.existsByUsername(r.username())) throw new ApiException(HttpStatus.CONFLICT,"Username already exists");
        AppUser u=new AppUser(null,r.username(),encoder.encode(r.password()),r.fullName(),Role.CUSTOMER);
        users.save(u); return toResponse(u);
    }
    public AuthResponse login(LoginRequest r) {
        AppUser u=users.findByUsername(r.username()).orElseThrow(()->new ApiException(HttpStatus.UNAUTHORIZED,"Invalid credentials"));
        if(!encoder.matches(r.password(),u.getPassword())) throw new ApiException(HttpStatus.UNAUTHORIZED,"Invalid credentials");
        return new AuthResponse(jwt.generateToken(u.getUsername()),"Bearer",jwt.getExpirationMs(),u.getUsername(),u.getFullName(),u.getRole().name());
    }
    public UserResponse me(String username) { return toResponse(users.findByUsername(username).orElseThrow(()->new ApiException(HttpStatus.NOT_FOUND,"User not found"))); }
    public List<UserResponse> listUsers() { return users.findAll().stream().map(this::toResponse).toList(); }
    private UserResponse toResponse(AppUser u) { return new UserResponse(u.getId(),u.getUsername(),u.getFullName(),u.getRole().name()); }
}
