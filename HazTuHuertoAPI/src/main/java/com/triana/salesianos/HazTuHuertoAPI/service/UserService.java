package com.triana.salesianos.HazTuHuertoAPI.service;

import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.model.UserRole;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.user.ChangePasswordRequest;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.user.CreateUserRequest;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.user.EditUser;
import com.triana.salesianos.HazTuHuertoAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User createUser(CreateUserRequest createUserRequest, EnumSet<UserRole> roles) {
        User user =  User.builder()
                .username(createUserRequest.getUsername())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .avatar(createUserRequest.getAvatar())
                .fullName(createUserRequest.getFullName())
                .roles(roles)
                .build();

        return userRepository.save(user);
    }

    public User createUserWithUserRole(CreateUserRequest createUserRequest) {
        return createUser(createUserRequest, EnumSet.of(UserRole.USER));
    }

    public User createUserWithAdminRole(CreateUserRequest createUserRequest) {
        return createUser(createUserRequest, EnumSet.of(UserRole.ADMIN));
    }

    public List<User> findAll() {

        List<User> result = userRepository.findAll();

        if (result.isEmpty())
            throw new EntityNotFoundException("No users with this search criteria");

        return userRepository.findAll();
    }

    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No user with id: " + id));

    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findFirstByUsername(username);
    }

    public User edit(UUID id, EditUser editUser) {

        // El username no se puede editar
        // La contraseña se edita en otro método

        return userRepository.findById(id)
                .map(u -> {
                    u.setAvatar(editUser.getAvatar());
                    u.setFullName(editUser.getFullName());
                    return userRepository.save(u);
                }).orElseThrow(() ->new EntityNotFoundException("No user with id: " + id));

    }

    public User editPassword(UUID userId, ChangePasswordRequest changePassword) {

        // Aquí no se realizan comprobaciones de seguridad. Tan solo se modifica

        return userRepository.findById(userId)
                .map(u -> {
                    if(Objects.equals(u.getPassword(), passwordEncoder.encode(changePassword.getNewPassword()))){
                        u.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
                        return userRepository.save(u);
                    }else{
                        //Esto debería ser una excepción personalizada indicando que als contraseñas no cionciden
                        throw new EntityNotFoundException("No user with id: " + userId);
                    }
                }).orElseThrow(() ->new EntityNotFoundException("No user with id: " + userId));

    }

    public void delete(User user) {
        deleteById(user.getId());
    }

    public void deleteById(UUID id) {
        // Prevenimos errores al intentar borrar algo que no existe
        if (userRepository.existsById(id))
            userRepository.deleteById(id);
    }
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }


    public boolean passwordMatch(User user, String clearPassword) {
        return passwordEncoder.matches(clearPassword, user.getPassword());
    }

}
