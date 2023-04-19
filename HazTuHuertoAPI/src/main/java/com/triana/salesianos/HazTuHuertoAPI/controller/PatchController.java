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
        return PatchDetails.fromPatch(patchService.findById(id, user));
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
        Patch patch=patchService.findById(id, logguedUser);
        Patch edited = patchService.edit(patch, editPatch,logguedUser, file);

        return PatchDetails.fromPatch(edited);

    }

    @DeleteMapping("/patch/{id}")//Hay que hacer todavia las politicas de borrado
    public ResponseEntity<?> delete(@AuthenticationPrincipal @NotNull User user, @PathVariable Long id) {
        System.out.println("1");
        if(userService.checkUserLogedInPatch(user.getId(), id))
            patchService.deleteById(id);
        System.out.println("2");
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/patch/{patchID}")//divide un patch en otro con us mismo historial
    public ResponseEntity<PatchDetails> divide( @PathVariable Long gardenId,
                                                @PathVariable Long patchId,
                                                 @AuthenticationPrincipal User user) {

        VegetableGarden garden = gardenService.findById(gardenId);
        Patch patch = patchService.findById(patchId, user);
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


}
