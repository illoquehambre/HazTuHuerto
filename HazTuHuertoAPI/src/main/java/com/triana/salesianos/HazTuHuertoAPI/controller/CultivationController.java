package com.triana.salesianos.HazTuHuertoAPI.controller;

import com.triana.salesianos.HazTuHuertoAPI.model.Patch;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.model.VegetableGarden;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.cultivation.CultivationDetails;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.cultivation.CultivationResponse;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.patch.CreatePatch;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.patch.PatchDetails;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.vegetableGarden.VegetableGardenResponse;
import com.triana.salesianos.HazTuHuertoAPI.repository.UserRepository;
import com.triana.salesianos.HazTuHuertoAPI.repository.VegetableGardenRepository;
import com.triana.salesianos.HazTuHuertoAPI.service.CultivationService;
import com.triana.salesianos.HazTuHuertoAPI.service.PatchService;
import com.triana.salesianos.HazTuHuertoAPI.service.UserService;
import com.triana.salesianos.HazTuHuertoAPI.service.VegetableGardenService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@OpenAPIDefinition(info = @Info(title ="Haz-Tu-Huerto"))
@Tag(name = "Cultivation", description = "This class implements Restcontrollers for the Entity Cultivation")
@CrossOrigin("http://localhost:3000")
public class CultivationController {

    private final PatchService patchService;
    private final VegetableGardenService gardenService;
    private final VegetableGardenRepository gardenRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final CultivationService cultivationService;




    @GetMapping("/cultivation/{id}")
    public List<CultivationDetails> findAllCultivationByPatchId(@AuthenticationPrincipal User user, @PathVariable Long id) {
        Patch patch =patchService.findById(id);
        if(userService.checkUserLogedInPatch(user.getId(), id)) {
            return patch.getCultivationHistory()
                    .stream()
                    .map(CultivationDetails::fromCultivation)
                    .toList();
        }
        else
            throw new SecurityException("Acess Denied");
    }

    @PostMapping("/cultivation/{id}")//Esto tiene checkear que no hay ya un cultivo. En verdad se puede hacer con el edit?
    public ResponseEntity<PatchDetails> register(@Valid @RequestPart("newPatch") CreatePatch newPatch,
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
                .body(PatchDetails.fromPatch(created));


    }
    @DeleteMapping("/patch/{id}")//Hay que hacer todavia las politicas de borrado
    public ResponseEntity<?> delete(@AuthenticationPrincipal User user,@PathVariable Long id) {
        if(userService.checkUserLogedInPatch(user.getId(), id))
            patchService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/patch/{patchID}")//divide un patch en otro con us mismo historial
    public ResponseEntity<PatchDetails> divide( @PathVariable Long gardenId,
                                                @PathVariable Long patchId,
                                                 @AuthenticationPrincipal User user) {

        VegetableGarden garden = gardenService.findById(gardenId);
        Patch patch = patchService.findById(patchId);
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
