package com.triana.salesianos.HazTuHuertoAPI.controller;

import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.user.*;
import com.triana.salesianos.HazTuHuertoAPI.security.jwt.access.JwtProvider;
import com.triana.salesianos.HazTuHuertoAPI.security.jwt.refresh.RefreshToken;
import com.triana.salesianos.HazTuHuertoAPI.security.jwt.refresh.RefreshTokenException;
import com.triana.salesianos.HazTuHuertoAPI.security.jwt.refresh.RefreshTokenRequest;
import com.triana.salesianos.HazTuHuertoAPI.security.jwt.refresh.RefreshTokenService;
import com.triana.salesianos.HazTuHuertoAPI.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> createUserWithUserRole(@Valid @RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUserWithUserRole(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(user));
    }

    // Más adelante podemos manejar la seguridad de acceso a esta petición

    @PostMapping("/auth/register/admin")
    public ResponseEntity<UserResponse> createUserWithAdminRole(@Valid @RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUserWithAdminRole(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(user));
    }


    @PostMapping("/auth/login")
    public ResponseEntity<JwtUserResponse> login(@RequestBody LoginRequest loginRequest) {

        // Realizamos la autenticación

        Authentication authentication =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword()
                        )
                );

        // Una vez realizada, la guardamos en el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Devolvemos una respuesta adecuada
        String token = jwtProvider.generateToken(authentication);

        User user = (User) authentication.getPrincipal();

        // Eliminamos el token (si existe) antes de crearlo, ya que cada usuario debería tener solamente un token de refresco simultáneo
        refreshTokenService.deleteByUser(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(JwtUserResponse.of(user, token, refreshToken.getToken()));


    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verify)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtProvider.generateToken(user);
                    refreshTokenService.deleteByUser(user);
                    RefreshToken refreshToken2 = refreshTokenService.createRefreshToken(user);
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(JwtUserResponse.builder()
                                    .token(token)
                                    .refreshToken(refreshToken2.getToken())
                                    .build());
                })
                .orElseThrow(() -> new RefreshTokenException("Refresh token not found"));

    }



    @PutMapping("/user/changePassword")
    public ResponseEntity<UserResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest,
                                                       @AuthenticationPrincipal User loggedUser) {
        //Esto debería funcionar pero hay que revisarlo porque no me fio un carajo
        User modified = userService.editPassword(loggedUser.getId(), changePasswordRequest);

        return ResponseEntity.ok(UserResponse.fromUser(modified));
    }


    //VerTodosLosUsuarios
    @GetMapping("/user")
    public List<UserResponse> findAll() {

        return userService.findAll()
                .stream()
                .map(UserResponse::fromUser)
                .toList();

    }
    //VerUnUsuarioPorID(GET by Id)

    @GetMapping("/user/{name}")
    public UserDetailsDto findUserByName (@PathVariable String name) {

        return UserDetailsDto.fromUser(userService.findByUsername(name));

    }

    //LogOut(se elimina el token de refresco)(si no hay token de refresco, no hay logout en el back)







}
