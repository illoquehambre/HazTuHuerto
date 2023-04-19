package com.triana.salesianos.HazTuHuertoAPI.service;

import com.triana.salesianos.HazTuHuertoAPI.files.service.StorageService;
import com.triana.salesianos.HazTuHuertoAPI.model.Patch;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.model.VegetableGarden;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.patch.CreatePatch;
import com.triana.salesianos.HazTuHuertoAPI.repository.CultivationRepository;
import com.triana.salesianos.HazTuHuertoAPI.repository.PatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CultivationService {

    private final PatchRepository patchRepository;
    private final UserService userService;
    private final StorageService storageService;
    private final CultivationRepository cultivationRepository;


    public List<Patch> findAll() {

        List<Patch> result = patchRepository.findAll();

        if (result.isEmpty())
            throw new EntityNotFoundException("No questions with this search criteria");

        return patchRepository.findAll();
    }
    public Patch findById(Long id) {
        return patchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No user with id: " + id));

    }


    public Patch save(CreatePatch newPatch, VegetableGarden garden, User user) {
        //Hacer comprobaciones de usuario
       // if(userService.checkUserLogedInGarden(user.getId(), garden.getId())) {
            return patchRepository.save(
                    Patch.builder()
                            .name(newPatch.getName())
                            .garden(garden)
                            .build());
       /* }else
            throw new SecurityException("Access Denied.");
*/
    }
    public Patch divide(Patch patch, User user) {
        //Hacer comprobaciones de usuario
        if(userService.checkUserLogedInPatch(user.getId(), patch.getId())) {
            return patchRepository.save(
                    Patch.builder()
                            .name("COPY-".concat(patch.getName()))
                            .garden(patch.getGarden())
                            .cultivationHistory(patch.getCultivationHistory())
                            .build());
        }else
            throw new SecurityException("Access Denied.");

    }
    public void deleteById(Long id) {
        //Esto es una gitanada, perdón
        if (patchRepository.existsById(id)){
            patchRepository.deleteById(id);
        }

    }

    public Patch edit(Patch patch, CreatePatch newPatch, User user) {
        if(userService.checkUserLogedInPatch(user.getId(), patch.getId())) {
            patch.setName(newPatch.getName());
        }
        return patchRepository.save(patch);
    }
}
