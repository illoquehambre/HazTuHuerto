package com.triana.salesianos.HazTuHuertoAPI.service;

import com.triana.salesianos.HazTuHuertoAPI.files.service.StorageService;
import com.triana.salesianos.HazTuHuertoAPI.model.Cultivation;
import com.triana.salesianos.HazTuHuertoAPI.model.Patch;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.model.VegetableGarden;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.patch.CreatePatchCultivation;
import com.triana.salesianos.HazTuHuertoAPI.repository.PatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatchService {

    private final PatchRepository patchRepository;
    private final UserService userService;
    private final StorageService storageService;
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

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
    public Cultivation findCultivationById(Long id) {

        return patchRepository.findFirstCultivationById(id)
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

    public Patch save(CreatePatchCultivation newPatch, VegetableGarden garden, User user, MultipartFile file) {
        //Hacer comprobaciones de usuario
        if(userService.checkUserLogedInGarden(user.getId(), garden.getId())) {

            Patch patch=createPatch(newPatch, garden);
            patchRepository.save(patch);
            Cultivation cultivation =createCultivation(newPatch, patch, file);
            patch.addCultivation(cultivation);
           return patchRepository.save(patch);


        }else
            throw new SecurityException("Access Denied.");

    }

    public Cultivation createCultivation(CreatePatchCultivation newPatch, Patch patch, MultipartFile file){
        String fileName = "";
        if(file!=null)
            fileName=storageService.store(file);

        return Cultivation.builder()
                .patch(patch)
                .name(newPatch.getCultivationName())
                .variety(newPatch.getVariety())
                .harvestingDate(newPatch.getHarvestDate())
                .plantingDate(newPatch.getPlantDate())
                .img(fileName)
                .build();
    }
    public Patch createPatch(CreatePatchCultivation newPatch, VegetableGarden garden){
        return patchRepository.save(
                Patch.builder()
                .name(newPatch.getPatchName())
                .garden(garden)
                .build()
        );
    }


    public Patch divide(Patch patch, User user) {
        //Hacer comprobaciones de usuario
        if(userService.checkUserLogedInPatch(user.getId(), patch.getId())) {

            Patch newPatch= Patch.builder()
                            .name("COPY-".concat(patch.getName()))
                            .garden(patch.getGarden())
                            .cultivationHistory(new ArrayList<>(patch.getCultivationHistory()))
                            .build();
            newPatch.setCultivation(patch.getCultivation());
            return patchRepository.save(newPatch);
        }else
            throw new SecurityException("Access Denied.");

    }
    public void deleteById(Long id) {
        //Esto es una gitanada, perdón
        Patch patch=this.findById(id);
        patch.getGarden().removePatch(patch);
        patchRepository.deleteById(id);

    }

    public Patch edit(Patch patch, CreatePatchCultivation edit, User user, MultipartFile file) {
        //Yo no se si esto está bien, habria qwu preguntarle al señor lusimi, parese chapuza
        if(userService.checkUserLogedInPatch(user.getId(), patch.getId())) {
            if(patch.getCultivation().getImg()!=null)
                storageService.deleteFile(patch.getCultivation().getImg());
            String fileName = storageService.store(file);
            patch.setName(edit.getPatchName());
            patch.getCultivation().setName(edit.getCultivationName());
            patch.getCultivation().setVariety(edit.getVariety());
            patch.getCultivation().setPlantingDate(edit.getPlantDate());
            patch.getCultivation().setHarvestingDate(edit.getHarvestDate());
            patch.getCultivation().setImg(fileName);
        }
        return patchRepository.save(patch);
    }

    public Patch harvest(Patch patch, User user, MultipartFile file, CreatePatchCultivation editPatch ) {
        //Yo no se si esto está bien, habria qwu preguntarle al señor lusimi, parese chapuza
        if(userService.checkUserLogedInPatch(user.getId(), patch.getId())) {
            Cultivation cultivation = patch.getCultivation();
            patch.setCultivation(this.createCultivation(editPatch, patch, file));
            patch.getCultivationHistory().add(cultivation);

        }
        return patchRepository.save(patch);
    }
}
