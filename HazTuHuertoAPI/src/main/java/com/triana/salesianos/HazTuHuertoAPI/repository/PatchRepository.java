package com.triana.salesianos.HazTuHuertoAPI.repository;

import com.triana.salesianos.HazTuHuertoAPI.model.Cultivation;
import com.triana.salesianos.HazTuHuertoAPI.model.Patch;
import com.triana.salesianos.HazTuHuertoAPI.model.VegetableGarden;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PatchRepository extends JpaRepository<Patch, Long>,
        JpaSpecificationExecutor<Patch> {
    List<Patch> findAllByGarden(VegetableGarden garden);
    @Transactional(readOnly = true)
    Optional<Patch> findById(@NotNull Long id);

    Optional<Cultivation> findFirstCultivationById(Long id);

}
