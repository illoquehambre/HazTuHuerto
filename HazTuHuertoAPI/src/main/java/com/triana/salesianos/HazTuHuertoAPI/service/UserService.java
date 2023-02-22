package com.triana.salesianos.HazTuHuertoAPI.service;

import com.triana.salesianos.HazTuHuertoAPI.exception.NoMatchPasswordException;
import com.triana.salesianos.HazTuHuertoAPI.files.service.StorageService;
import com.triana.salesianos.HazTuHuertoAPI.model.Answer;
import com.triana.salesianos.HazTuHuertoAPI.model.Question;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.model.UserRole;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.PageDto;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.user.ChangePasswordRequest;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.user.CreateUserRequest;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.user.EditUser;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.user.UserResponse;
import com.triana.salesianos.HazTuHuertoAPI.repository.UserRepository;
import com.triana.salesianos.HazTuHuertoAPI.search.spec.UserSpecificationBuilder;
import com.triana.salesianos.HazTuHuertoAPI.search.util.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Filter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final StorageService storageService;
    private final EntityManager entityManager;

    @Transactional
    public User createUser(CreateUserRequest createUserRequest, EnumSet<UserRole> roles) {
        User user =  User.builder()
                .username(createUserRequest.getUsername())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .fullName(createUserRequest.getFullName())
                .email(createUserRequest.getEmail())
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

    public PageDto<UserResponse> searchBanned(List<SearchCriteria> params, Pageable pageable) {//Funciona
        //Search criteria
        UserSpecificationBuilder userSpecificationBuilder = new UserSpecificationBuilder(params);
        Specification<User> spec =  userSpecificationBuilder.build();
        //SoftDelete
        Session session = entityManager.unwrap(Session.class);//Se debería hacer un try catch?
        Filter filter = session.enableFilter("bannedProductFilter");
        filter.setParameter("isBanned", true);

        //Pageable
        Page<UserResponse> pageUserResponse = userRepository.findAll(spec, pageable).map(UserResponse::fromUser);
        session.disableFilter("bannedProductFilter");
        if(pageUserResponse.isEmpty())
            throw new EntityNotFoundException("No users with this search criteria");
        return new PageDto<>(pageUserResponse);
    }
//Deberia fusionar ambos search y hacer comprobaciones de si el usuario logueado es admin???
    public PageDto<UserResponse> search(List<SearchCriteria> params, Pageable pageable) {
        //Search criteria
        UserSpecificationBuilder userSpecificationBuilder = new UserSpecificationBuilder(params);
        Specification<User> spec =  userSpecificationBuilder.build();
        //SoftDelete
        Session session = entityManager.unwrap(Session.class);//Se debería hacer un try catch?
        Filter filter = session.enableFilter("bannedProductFilter");
        filter.setParameter("isBanned", false);
        //Pageable
        Page<UserResponse> pageUserResponse = userRepository.findAll(spec, pageable).map(UserResponse::fromUser);

        if(pageUserResponse.isEmpty())
            throw new EntityNotFoundException("No users with this search criteria");
        return new PageDto<>(pageUserResponse);
    }

    public User bannUser(UUID id){
        User user = this.findById(id);
        user.setBanned(!user.isBanned());
        return userRepository.save(user);
    }

    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No user with id: " + id));

    }

    public User findByUsername(String username) {
        return userRepository.findFirstByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("No user with name: " + username));
    }

    public User edit(User u, EditUser editUser, MultipartFile file) {

        String filename = storageService.store(file);
            u.setAvatar(filename);
            u.setFullName(editUser.getFullName());
            return userRepository.save(u);


    }

    public User editPassword(User user, ChangePasswordRequest changePassword) throws NoMatchPasswordException {

        // Aquí no se realizan comprobaciones de seguridad. Tan solo se modifica

        if(passwordEncoder.matches(changePassword.getOldPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
            return userRepository.save(user);
        }else{
            //Esto debería ser una excepción personalizada indicando que als contraseñas no cionciden
            throw new NoMatchPasswordException();
        }


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

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }


    public boolean passwordMatch(User user, String clearPassword) {
        return passwordEncoder.matches(clearPassword, user.getPassword());
    }

    public boolean checkUserLiked(UUID userId, Long questionId) {
        return userRepository.checkUserLiked(userId,questionId);
    }

    public boolean checkUserLogedInAnswer(UUID userId, Long answerId) {//Comprobar tambien si es admin para que pueda elimianr cuando quiera
        return userRepository.checkUserLogedInAnswer(userId,answerId);
    }
    public boolean checkUserLogedInQuestion(UUID userId, Long questionId) {
        return userRepository.checkUserLogedInQuestion(userId,questionId);
    }
    @Transactional
    public void addQuestion(Question question,  User user){
        user.getPublishedQuestions().add(question);

    }




}
