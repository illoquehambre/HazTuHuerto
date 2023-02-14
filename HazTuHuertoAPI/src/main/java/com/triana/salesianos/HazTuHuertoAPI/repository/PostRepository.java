package com.triana.salesianos.HazTuHuertoAPI.repository;

import com.triana.salesianos.HazTuHuertoAPI.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository <Question, Long> {
}
