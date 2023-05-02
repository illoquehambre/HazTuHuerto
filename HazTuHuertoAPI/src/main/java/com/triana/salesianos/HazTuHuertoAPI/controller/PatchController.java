package com.triana.salesianos.HazTuHuertoAPI.controller;

import com.triana.salesianos.HazTuHuertoAPI.model.Patch;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.model.VegetableGarden;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.patch.*;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.vegetableGarden.CreateVegetableGarden;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.vegetableGarden.VegetableGardenDetails;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.vegetableGarden.VegetableGardenResponse;
import com.triana.salesianos.HazTuHuertoAPI.repository.UserRepository;
import com.triana.salesianos.HazTuHuertoAPI.repository.VegetableGardenRepository;
import com.triana.salesianos.HazTuHuertoAPI.service.PatchService;
import com.triana.salesianos.HazTuHuertoAPI.service.UserService;
import com.triana.salesianos.HazTuHuertoAPI.service.VegetableGardenService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@OpenAPIDefinition(info = @Info(title ="Haz-Tu-Huerto"))
@Tag(name = "Patch", description = "This class implements Restcontrollers for the Entity Patch")
@CrossOrigin("http://localhost:3000")
public class PatchController {

    private final PatchService patchService;
    private final VegetableGardenService gardenService;
    private final VegetableGardenRepository gardenRepository;
    private final UserService userService;
    private final UserRepository userRepository;


    @GetMapping("/patch/{id}")
    public PatchDetails findByPatchId(@AuthenticationPrincipal User user,
                                      @PathVariable Long id) {
        Patch patch = patchService.findById(id);
        //Por alguna razon todos los datos de garden son nullos en este momento
        if(userService.checkUserLogedInPatch(user.getId(), id))
            return PatchDetails.fromPatch(patch);
            //sin embargo al hacer la conversión si que coge de forma adecuada los datos de garden
        else
            throw new SecurityException("Access Denied");

    }


    @PostMapping("/patch/garden/{id}")//El id es de garden pero crea un nuevo patch en este
    public ResponseEntity<PatchSimplify> register(@Valid @RequestBody CreatePatch newPatch,
                                                    @PathVariable Long id,
                                                    @AuthenticationPrincipal User user) {

        VegetableGarden garden = gardenService.findById(id);
        Patch created = patchService.save(newPatch,garden, user);
        garden.addPatch(created);
        gardenRepository.save(garden);
        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity
                .created(createdURI)
                .body(PatchSimplify.fromPatch(created));


    }
    @PutMapping("/patch/{id}") //Revisar la respuesta
    public PatchDetails editDetails(@AuthenticationPrincipal User logguedUser,
                                     @PathVariable Long id,
                                    @RequestPart("file") MultipartFile file,
                                     @Valid @RequestPart("editPatch") EditPatchCultivation editPatch) {
        Patch patch=patchService.findById(id);
        Patch edited = patchService.edit(patch, editPatch,logguedUser, file);

        return PatchDetails.fromPatch(edited);

    }

    //No se que cojones pasa que este delete no lo pilla, no hay erroreres pero tampoco devuelve 204 ni nada
    //raro
    @DeleteMapping("/patch/{id}")//Hay que hacer todavia las politicas de borrado
    public ResponseEntity<?> deletePatch(@AuthenticationPrincipal User user, @PathVariable Long id) {
        if(userService.checkUserLogedInPatch(user.getId(), id))
            patchService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/patch/divide/{id}")//divide un patch en otro con us mismo historial
    public ResponseEntity<PatchDetails> divide(@PathVariable Long id,
                                                 @AuthenticationPrincipal User user) {


        Patch patch = patchService.findById(id);
        VegetableGarden garden = patch.getGarden();//Acá se traga un nullPointer como una casa
        Patch created = patchService.divide(patch, user);
        garden.addPatch(created);
        gardenRepository.save(garden);
        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity
                .created(createdURI)
                .body(PatchDetails.fromPatch(created));


    }
    @PutMapping("/patch/harvest/{id}") //Revisar la respuesta
    public PatchDetails editDetails(@AuthenticationPrincipal User logguedUser,
                                    @PathVariable Long id) {
        Patch patch=patchService.findById(id);
        Patch edited = patchService.harvest(patch, logguedUser);

        return PatchDetails.fromPatch(edited);

    }

}
