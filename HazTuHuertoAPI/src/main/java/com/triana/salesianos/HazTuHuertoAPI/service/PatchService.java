package com.triana.salesianos.HazTuHuertoAPI.service;

import com.triana.salesianos.HazTuHuertoAPI.files.service.StorageService;
import com.triana.salesianos.HazTuHuertoAPI.model.Cultivation;
import com.triana.salesianos.HazTuHuertoAPI.model.Patch;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.model.VegetableGarden;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.patch.CreatePatch;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.patch.EditPatchCultivation;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.vegetableGarden.CreateVegetableGarden;
import com.triana.salesianos.HazTuHuertoAPI.repository.CultivationRepository;
import com.triana.salesianos.HazTuHuertoAPI.repository.PatchRepository;
import com.triana.salesianos.HazTuHuertoAPI.repository.VegetableGardenRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PatchService {

    private final PatchRepository patchRepository;
    private final UserService userService;
    private final StorageService storageService;
    private final CultivationRepository cultivationRepository;
    private final CultivationService cultivationService;


    public List<Patch> findAll() {

        List<Patch> result = patchRepository.findAll();

        if (result.isEmpty())
            throw new EntityNotFoundException("No questions with this search criteria");

        return patchRepository.findAll();
    }

    public Patch findById(Long id) {

        return patchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No patch with id: " + id));

    }
    public List<Patch> findByGarden(VegetableGarden garden, User user) {
        if(userService.checkUserLogedInGarden(user.getId(), garden.getId())) {
            List<Patch> result = patchRepository.findAllByGarden(garden);

            if (result.isEmpty())
                throw new EntityNotFoundException("No hay huertos pa este usuario");

            return patchRepository.findAll();
        }else
            throw new SecurityException("Access Denied");

    }

    public Patch save(CreatePatch newPatch, VegetableGarden garden, User user) {
        //Hacer comprobaciones de usuario
        if(userService.checkUserLogedInGarden(user.getId(), garden.getId())) {

            Patch patch=Patch.builder()
                    .name(newPatch.getName())
                    .garden(garden)
                    .build();
            //La creacion de un cultivo vacio no deberia realizarse
            Cultivation cultivation = cultivationService.createEmpty(patchRepository.save(patch));
            patch.setCultivation(cultivation);
           return patchRepository.save(patch);


        }else
            throw new SecurityException("Access Denied.");

    }
    public Patch divide(Patch patch, User user) {
        //Hacer comprobaciones de usuario
        if(userService.checkUserLogedInPatch(user.getId(), patch.getId())) {

            Patch newPatch= Patch.builder()
                            .name("COPY-".concat(patch.getName()))
                            .garden(patch.getGarden())
                            .cultivationHistory(new ArrayList<>(patch.getCultivationHistory()))
                            .build();
            Cultivation cultivation = cultivationService.createEmpty(patchRepository.save(patch));
            patch.setCultivation(cultivation);
            return patchRepository.save(newPatch);
        }else
            throw new SecurityException("Access Denied.");

    }
    public void deleteById(Long id) {
        //Esto es una gitanada, perdón
        if (patchRepository.existsById(id)){
            patchRepository.deleteById(id);
        }

    }

    public Patch edit(Patch patch, EditPatchCultivation edit, User user, MultipartFile file) {
        //Yo no se si esto está bien, habria qwu preguntarle al señor lusimi, parese chapuza
        if(userService.checkUserLogedInPatch(user.getId(), patch.getId())) {
            if(patch.getCultivation().getImg()!=null)
                storageService.deleteFile(patch.getCultivation().getImg());
            String fileName = storageService.store(file);
            patch.setName(edit.getPatchName());
            patch.getCultivation().setName(edit.getCultivationName());
            patch.getCultivation().setVariety(edit.getVariety());
            patch.getCultivation().setPlantingDate(LocalDate.parse(edit.getPlantDate(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")) );
            patch.getCultivation().setHarvestingDate(LocalDate.parse(edit.getHarvestDate(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            patch.getCultivation().setImg(fileName);
        }
        return patchRepository.save(patch);
    }

    public Patch harvest(Patch patch, User user) {
        //Yo no se si esto está bien, habria qwu preguntarle al señor lusimi, parese chapuza
        if(userService.checkUserLogedInPatch(user.getId(), patch.getId())) {
            Cultivation cultivation = patch.getCultivation();
            patch.setCultivation(cultivationService.createEmpty(patch));//no deberia crearse un cultivo vacio
            patch.getCultivationHistory().add(cultivation);
            cultivationRepository.save(cultivation);

        }
        return patchRepository.save(patch);
    }
}
