package com.triana.salesianos.HazTuHuertoAPI.repository;

import com.triana.salesianos.HazTuHuertoAPI.model.VegetableGarden;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface VegetableGardenRepository extends JpaRepository<VegetableGarden, Long>,
        JpaSpecificationExecutor<VegetableGarden> {

    Page<VegetableGarden> findAllByOwner(User owner, Pageable pageable);
}
