package com.shop.phoneshop.services;

import com.shop.phoneshop.domain.RefreshToken;
import com.shop.phoneshop.domain.User;
import com.shop.phoneshop.domain.enums.Role;
import com.shop.phoneshop.dto.JwtResponseDto;
import com.shop.phoneshop.repos.RefreshTokenRepo;
import com.shop.phoneshop.repos.UserRepo;
import com.shop.phoneshop.requests.auth.AuthRequest;
import com.shop.phoneshop.requests.auth.RefreshTokenRequest;
import com.shop.phoneshop.requests.auth.RegisterRequest;
import com.shop.phoneshop.security.jwt.JwtProvider;
import com.shop.phoneshop.security.jwt.TokenResponse;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class AuthService {
    private final UserService userService;
    private final RefreshTokenRepo refreshTokenRepo;
    private final UserRepo userRepo;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserService userService,
                       RefreshTokenRepo refreshTokenRepo,
                       UserRepo userRepo,
                       JwtProvider jwtProvider,
                       PasswordEncoder passwordEncoder
    ) {
        this.userService = userService;
        this.refreshTokenRepo = refreshTokenRepo;
        this.userRepo = userRepo;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public JwtResponseDto authenticateUser(AuthRequest authRequest) {
        User user = userRepo.findByEmail(authRequest.getUserEmail());

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователя с таким email не существует");
        }

        if (!passwordEncoder.matches(authRequest.getUserPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Неверный пароль");
        }

        return generateJwtResponse(user, new RefreshToken(user));
    }

    @Transactional
    public User registerUser(RegisterRequest request) {
        String newEmail = request.getUserEmail();
        if (userRepo.existsByEmail(newEmail)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Пользователь с таким email уже существует");
        }

        User user = new User();
        user.setEmail(request.getUserEmail());
        user.setPassword(passwordEncoder.encode(request.getUserPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRoles(Set.of(Role.USER));
        userRepo.save(user);

        return user;
    }

    @Transactional
    public JwtResponseDto refreshTokens(RefreshTokenRequest request) {
        String oldRefreshToken = request.getRefreshToken();

        if (jwtProvider.validateRefreshToken(oldRefreshToken)) {
            RefreshToken refreshToken = refreshTokenRepo.findByToken(oldRefreshToken).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Refresh-токен не найден"));

            Claims claims = jwtProvider.getRefreshClaims(oldRefreshToken);
            Long userId = Long.valueOf(claims.getSubject());
            User user = userService.getUserById(userId);
            return generateJwtResponse(user, refreshToken);
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Некорректный refresh-токен");
    }

    private JwtResponseDto generateJwtResponse(User user, RefreshToken refreshToken) {
        TokenResponse access = jwtProvider.generateAccessToken(user);
        TokenResponse refresh = jwtProvider.generateRefreshToken(user);
        refreshToken.setToken(refresh.getToken());
        refreshTokenRepo.save(refreshToken);

        return new JwtResponseDto(access, refresh);
    }

    @Transactional
    public void deleteAllById(Long id) {
        User user = userService.getUserById(id);
        refreshTokenRepo.deleteAllByUser(user);
    }
}
