package com.triana.salesianos.HazTuHuertoAPI.controller;

import com.triana.salesianos.HazTuHuertoAPI.exception.NoMatchPasswordException;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.PageDto;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.user.*;
import com.triana.salesianos.HazTuHuertoAPI.search.util.SearchCriteria;
import com.triana.salesianos.HazTuHuertoAPI.search.util.SearchCriteriaExtractor;
import com.triana.salesianos.HazTuHuertoAPI.security.jwt.access.JwtProvider;
import com.triana.salesianos.HazTuHuertoAPI.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtProvider jwtProvider;


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



        return ResponseEntity.status(HttpStatus.CREATED)
                .body(JwtUserResponse.of(user, token));


    }
/*
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
*/


    @PutMapping("/user/changePassword")
    public ResponseEntity<UserResponse> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest,
                                                       @AuthenticationPrincipal User loggedUser) throws NoMatchPasswordException {
        //Esto debería funcionar pero hay que revisarlo porque no me fio un carajo
        User modified = userService.editPassword(loggedUser, changePasswordRequest);

        return ResponseEntity.ok(UserResponse.fromUser(modified));
    }


    //VerTodosLosUsuarios
    @GetMapping("/user")
    public PageDto<UserResponse> findAll(@RequestParam(value = "search", defaultValue = "") String search,
                                      @PageableDefault(size = 20, page = 0) Pageable pageable) {

        List<SearchCriteria> params = SearchCriteriaExtractor.extractSearchCriteriaList(search);

        return userService.search(params, pageable);


    }
    //VerUnUsuarioPorID(GET by Id)

    @GetMapping("/user/{name}")
    public UserDetailsDto findUserByName (@PathVariable String name) {

        return UserDetailsDto.fromUser(userService.findByUsername(name));

    }
    //Modifcar datos usuario (PUT) (profile)

    @PutMapping("/user/{id}")
    public UserResponse editDetails(@PathVariable UUID id,
                                    @RequestPart("file") MultipartFile file,
                                    @Valid @RequestPart("editUser") EditUser editUser) {

        User edited = userService.edit(id, editUser, file);

        return UserResponse.fromUser(edited);

    }

    //Eliminar un usuario (Tiene sentido eliminar un usuario??)En vez de eliminarlo podemos desabilitar la cuenta para el usuario

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> adminDelete(@PathVariable UUID id) {
        userService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
    //LogOut(se elimina el token de refresco)(si no hay token de refresco, no hay logout en el back)







}
