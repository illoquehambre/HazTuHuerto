package com.triana.salesianos.HazTuHuertoAPI.repository;

import com.triana.salesianos.HazTuHuertoAPI.model.Question;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository <User, UUID>,
        JpaSpecificationExecutor<User> {
    Optional<User> findFirstByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);


    @Query(value="SELECT CASE WHEN COUNT(q) > 0 THEN true ELSE false END FROM Question q JOIN q.likes u WHERE q.id = :questionId AND u.id = :userId")
    boolean checkUserLiked(@Param("userId") UUID userId, @Param("questionId") Long questionId);

   /* @Query(value="SELECT u FROM User u JOIN FETCH u.publishedQuestions")
    User getQuestionList();
*/


    @Query(value="SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Answer a JOIN a.publisher u WHERE a.id = :answerId AND u.id = :userId")
    boolean checkUserLogedInAnswer(@Param("userId") UUID userId, @Param("answerId") Long answerId);

    @Query(value="SELECT CASE WHEN COUNT(q) > 0 THEN true ELSE false END FROM Question q JOIN q.publisher u WHERE q.id = :questionId AND u.id = :userId")
    boolean checkUserLogedInQuestion(@Param("userId") UUID userId, @Param("questionId") Long questionId);

    @Query(value="SELECT CASE WHEN COUNT(g) > 0 THEN true ELSE false END FROM VegetableGarden g JOIN g.owner u WHERE g.id = :gardenId AND u.id = :userId")
    boolean checkUserLogedInGarden(@Param("userId") UUID userId, @Param("gardenId") Long gardenId);

    @Query(value="SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Patch p JOIN p.garden.owner u WHERE p.id = :patchId AND u.id = :userId")
    boolean checkUserLogedInPatch(@Param("userId") UUID userId, @Param("patchId") Long patchId);


}
