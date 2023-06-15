package com.triana.salesianos.HazTuHuertoAPI.controller;

import com.triana.salesianos.HazTuHuertoAPI.exception.BannedAccountException;
import com.triana.salesianos.HazTuHuertoAPI.exception.NoMatchPasswordException;
import com.triana.salesianos.HazTuHuertoAPI.files.service.StorageService;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.model.UserRole;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.PageDto;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.user.*;
import com.triana.salesianos.HazTuHuertoAPI.search.util.SearchCriteria;
import com.triana.salesianos.HazTuHuertoAPI.search.util.SearchCriteriaExtractor;
import com.triana.salesianos.HazTuHuertoAPI.security.jwt.access.JwtProvider;
import com.triana.salesianos.HazTuHuertoAPI.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

class UserPageResponse extends PageDto<UserResponse>{}
@RestController
@RequiredArgsConstructor
@OpenAPIDefinition(info = @Info(title ="Haz-Tu-Huerto"))
@Tag(name = "User", description = "This class implements Restcontrollers for the Entity User")
@CrossOrigin("http://localhost:3000")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtProvider jwtProvider;
    private final StorageService storageService;

    @Operation(summary = "This method creates a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado una nueva aportacion",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)),
                            examples = @ExampleObject(value = """
                                     {
                                         "id": "73fcb043-b1a1-4ba8-af88-4ad3abcf2021",
                                         "username": "Programer13",
                                         "avatar": "https://www.google.com/url?sa=i&url=https%3A%2F%2Frap.fandom.com%2Fes%2Fwiki%2FKase.O&psig=",
                                         "fullName": "Paquito programador2",
                                         "createdAt": "12/12/2022 00:00:00"
                                     }
                                    """)) }),
            @ApiResponse(responseCode = "400",
                    description = "The data submited have a wrong format",
                    content = @Content),
    })
    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> createUserWithUserRole(@Valid @RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUserWithUserRole(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(user));
    }

    // Más adelante podemos manejar la seguridad de acceso a esta petición
    @Operation(summary = "This method creates a new admin user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "A new Admin user was successfully created",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)),
                            examples = @ExampleObject(value = """
                                     {
                                         "id": "73fcb043-b1a1-4ba8-af88-4ad3abcf2021",
                                         "username": "Programer13",
                                         "avatar": "https://www.google.com/url?sa=i&url=https%3A%2F%2Frap.fandom.com%2Fes%2Fwiki%2FKase.O&psig=",
                                         "fullName": "Paquito programador2",
                                         "createdAt": "12/12/2022 00:00:00"
                                     }
                                    """)) }),
            @ApiResponse(responseCode = "400",
                    description = "The data submited have a wrong format",
                    content = @Content),
    })
    @PostMapping("/auth/register/admin")
    public ResponseEntity<UserResponse> createUserWithAdminRole(@Valid @RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUserWithAdminRole(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromUser(user));
    }

    @Operation(summary = "This method do a login in a user account already created")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado una nueva aportacion",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)),
                            examples = @ExampleObject(value = """
                                     {
                                         "id": "73fcb043-b1a1-4ba8-af88-4ad3abcf2021",
                                         "username": "Programer13",
                                         "avatar": "https://www.google.com/url?sa=i&url=https%3A%2F%2Frap.fandom.com%2Fes%2Fwiki%2FKase.O&psig=",
                                         "fullName": "Paquito programador2",
                                         "createdAt": "12/12/2022 00:00:00"
                                     }
                                    """)) }),
            @ApiResponse(responseCode = "400",
                    description = "The data submited have a wrong format",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "The user account was not found",
                    content = @Content),
    })
    @PostMapping("/auth/login")
    public ResponseEntity<JwtUserResponse> login(@RequestBody LoginRequest loginRequest) {

        if(userService.findByUsername(loginRequest.getUsername()).isBanned())
            throw new BannedAccountException();
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


    @Operation(summary = "This method do a login in a user account already created")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Login exitoso",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)),
                            examples = @ExampleObject(value = """
                                     {
                                         "id": "73fcb043-b1a1-4ba8-af88-4ad3abcf2021",
                                         "username": "Programer13",
                                         "avatar": "https://www.google.com/url?sa=i&url=https%3A%2F%2Frap.fandom.com%2Fes%2Fwiki%2FKase.O&psig=",
                                         "fullName": "Paquito programador2",
                                         "createdAt": "12/12/2022 00:00:00"
                                     }
                                    """)) }),
            @ApiResponse(responseCode = "400",
                    description = "The data submited have a wrong format",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "The user account was not found",
                    content = @Content),
    })
    @PostMapping("/auth/login/admin")
    public ResponseEntity<JwtUserResponse> adminLogin(@RequestBody LoginRequest loginRequest) {

        if(userService.findByUsername(loginRequest.getUsername()).isBanned())
            throw new BannedAccountException();
        if(!userService.findByUsername(loginRequest.getUsername()).getRoles().contains(UserRole.ADMIN))
            throw new SecurityException("Necesitas ser Admin pendejo");
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

    @Operation(summary = "This method change the current password of an user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The operation was succesfully",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)),
                            examples = @ExampleObject(value = """
                                    {
                                         "id": "73fcb043-b1a1-4ba8-af88-4ad3abcf2021",
                                         "username": "Programer13",
                                         "avatar": "https://www.google.com/url?sa=i&url=https%3A%2F%2Frap.fandom.com%2Fes%2Fwiki%2FKase.O&psig=",
                                         "fullName": "Paquito programador2",
                                         "createdAt": "12/12/2022 00:00:00"
                                     }
                                    """)) }),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed to do this request",
                    content = @Content),
    })
    @PutMapping("/user/changePassword")
    public UserResponse changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest,
                                                       @AuthenticationPrincipal User loggedUser) throws NoMatchPasswordException {
        //Esto debería funcionar pero hay que revisarlo porque no me fio un carajo
        User modified = userService.editPassword(loggedUser, changePasswordRequest);

        return UserResponse.fromUser(modified);
    }


    //VerTodosLosUsuarios
    @Operation(summary = "This method list all users not banned")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "At least one User was found",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserPageResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                 "content": [
                                                     {
                                                         "id": "1b257813-0195-43ab-9a3c-f1b08ab4c5a9",
                                                         "username": "Programer12",
                                                         "avatar": "https://www.google.com/url?sa=i&url=https%3A%2F%2Frap.fandom.com%2Fes%2Fwiki%2FKase.O&psig=",
                                                         "fullName": "Paquito programador",
                                                         "createdAt": "12/12/2022 00:00:00"
                                                     }
                                                 ],
                                                 "last": true,
                                                 "first": true,
                                                 "totalPages": 1,
                                                 "totalElements": 1
                                             }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "Any users were found",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed to do this request",
                    content = @Content),
    })
    @GetMapping("/user")
    public PageDto<UserResponse> findAll(@RequestParam(value = "search", defaultValue = "") String search,
                                        @PageableDefault(size = 5, page = 0) Pageable pageable) {

        List<SearchCriteria> params = SearchCriteriaExtractor.extractSearchCriteriaList(search);

        return userService.search(params, pageable);


    }

    //VerTodosLosUsuariosBaneados
    @Operation(summary = "This method list all users banned")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "At least one User was found",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserPageResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                 "content": [
                                                     {
                                                         "id": "1b257813-0195-43ab-9a3c-f1b08ab4c5a9",
                                                         "username": "Programer12",
                                                         "avatar": "https://www.google.com/url?sa=i&url=https%3A%2F%2Frap.fandom.com%2Fes%2Fwiki%2FKase.O&psig=",
                                                         "fullName": "Paquito programador",
                                                         "createdAt": "12/12/2022 00:00:00"
                                                     }
                                                 ],
                                                 "last": true,
                                                 "first": true,
                                                 "totalPages": 1,
                                                 "totalElements": 1
                                             }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "Any users were found",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed to do this request",
                    content = @Content),
    })
    @GetMapping("/admin/user")//deberia devolver otro tipod e dto que muestre el atributo isbanned?
    public PageDto<UserResponse> findAllBanned(@RequestParam(value = "search", defaultValue = "") String search,
                                         @PageableDefault(size = 20, page = 0) Pageable pageable) {
        List<SearchCriteria> params = SearchCriteriaExtractor.extractSearchCriteriaList(search);
        return userService.searchBanned(params, pageable);

    }

    @Operation(summary = "This method bann or unbann a user. However, the quests and answers published are not being modified.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "No content ",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed to do this request",
                    content = @Content),
    })

    @DeleteMapping("/admin/user/{id}")//deberia devolver otro tipode dto que muestre el atributo isbanned? 
    public ResponseEntity<?> bannUser(@PathVariable UUID id) {

        userService.bannUser(id);

        return ResponseEntity.noContent().build();

    }


    //VerUnUsuarioPorID(GET by Id)
    @Operation(summary = "This method find a user by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "the user was successfully found",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserDetailsDto.class)),
                            examples = @ExampleObject(value = """
                                    {
                                         "id": "c0a83801-866a-18f6-8186-6a9707d60001",
                                         "username": "illo",
                                         "avatar": "monke2.jpg",
                                         "fullName": "illo que hambre",
                                         "createdAt": "19/02/2023 17:52:08",
                                         "publishedQuestions": [],
                                         "publishedAnswers": [],
                                         "favPosts": [],
                                         "reputation": 0
                                     }
                                    """)) }),
            @ApiResponse(responseCode = "404",
                    description = "The user was not found",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed to do this request",
                    content = @Content),
    })
    @GetMapping("/user/{name}")
    public UserDetailsDto findUserByName (@PathVariable String name) {

        return UserDetailsDto.fromUser(userService.findByUsername(name));

    }
    //Modifcar datos usuario (PUT) (profile)
    @Operation(summary = "This method upload a user profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The operation was succesfully",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)),
                            examples = @ExampleObject(value = """
                                    {
                                         "id": "73fcb043-b1a1-4ba8-af88-4ad3abcf2021",
                                         "username": "Programer13",
                                         "avatar": "https://www.google.com/url?sa=i&url=https%3A%2F%2Frap.fandom.com%2Fes%2Fwiki%2FKase.O&psig=",
                                         "fullName": "Paquito programador2",
                                         "createdAt": "12/12/2022 00:00:00"
                                     }
                                    """)) }),
            @ApiResponse(responseCode = "404",
                    description = "The user was not found",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "The data sended was not correct",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed to do this request",
                    content = @Content),
    })
    @PutMapping("/user/")
    public UserResponse editDetails(@AuthenticationPrincipal User logguedUser,
                                    @RequestPart("file") MultipartFile file,
                                    @Valid @RequestPart("editUser") EditUser editUser) {

        User edited = userService.edit(logguedUser, editUser, file);

        return UserResponse.fromUser(edited);

    }

    //No se llega a usar
    @GetMapping(value = "/user/{name}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> image(@PathVariable String name) throws IOException {
        User user= userService.findByUsername(name);
        Resource img = storageService.loadAsResource(user.getAvatar());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(img);

    }



    //LogOut(se elimina el token de refresco)(si no hay token de refresco, no hay logout en el back)







}
