package com.triana.salesianos.HazTuHuertoAPI.repository;

import com.triana.salesianos.HazTuHuertoAPI.model.Comentary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentaryRepository extends JpaRepository <Comentary, Long> {
}
