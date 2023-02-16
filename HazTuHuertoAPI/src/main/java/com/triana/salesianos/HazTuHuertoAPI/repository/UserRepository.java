package com.triana.salesianos.HazTuHuertoAPI.repository;

import com.triana.salesianos.HazTuHuertoAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository <User, UUID>,
        JpaSpecificationExecutor<User> {
    Optional<User> findFirstByUsername(String username);
    boolean existsByUsername(String username);

    @Query(value="SELECT CASE WHEN COUNT(q) > 0 THEN true ELSE false END FROM Question q JOIN q.likes u WHERE q.id = :questionId AND u.id = :userId")
    boolean checkUserLiked(@Param("userId") UUID userId, @Param("questionId") Long questionId);

    @Query(value="SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Answer a JOIN a.publisher u WHERE a.id = :answerId AND u.id = :userId")
    boolean checkUserLoged(@Param("userId") UUID userId, @Param("answerId") Long answerId);
}
