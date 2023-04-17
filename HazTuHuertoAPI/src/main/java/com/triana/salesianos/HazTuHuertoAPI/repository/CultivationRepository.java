package com.triana.salesianos.HazTuHuertoAPI.repository;

import com.triana.salesianos.HazTuHuertoAPI.model.Cultivation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CultivationRepository extends JpaRepository<Cultivation, Long>,
        JpaSpecificationExecutor<Cultivation> {
}
