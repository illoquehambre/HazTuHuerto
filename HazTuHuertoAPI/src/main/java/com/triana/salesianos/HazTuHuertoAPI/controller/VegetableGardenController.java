package com.triana.salesianos.HazTuHuertoAPI.controller;

import com.triana.salesianos.HazTuHuertoAPI.model.Question;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.model.VegetableGarden;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.user.EditUser;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.user.UserResponse;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.vegetableGarden.CreateVegetableGarden;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.vegetableGarden.VegetableGardenDetails;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.vegetableGarden.VegetableGardenResponse;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.question.CreateQuestion;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.question.QuestionDetails;
import com.triana.salesianos.HazTuHuertoAPI.repository.UserRepository;
import com.triana.salesianos.HazTuHuertoAPI.service.UserService;
import com.triana.salesianos.HazTuHuertoAPI.service.VegetableGardenService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
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
@Tag(name = "VegetableGarden", description = "This class implements Restcontrollers for the Entity Vegetable Garden")
@CrossOrigin("http://localhost:3000")
public class VegetableGardenController {

    private final VegetableGardenService gardenService;
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/vegetableGarden")
    public List<VegetableGardenResponse> findAll(@AuthenticationPrincipal User user) {
        return gardenService.findByUser(user)
                .stream()
                .map(VegetableGardenResponse::fromGarden)
                .toList();
    }

    @GetMapping("/vegetableGarden/{id}")
    public VegetableGardenDetails findGardenById(@AuthenticationPrincipal User user, @PathVariable Long id) {
        return VegetableGardenDetails.fromGarden(gardenService.findById(id));

    }

    @PostMapping("/vegetableGarden")
    public ResponseEntity<VegetableGardenDetails> register(@RequestPart("file") MultipartFile file,
                                                    @Valid @RequestPart("newGarden") CreateVegetableGarden newGarden,
                                                    @AuthenticationPrincipal User user) {
        User userFound=userService.findById(user.getId());
        VegetableGarden created = gardenService.save(newGarden, user, file);
        userFound.addGarden(created);
        userRepository.save(userFound);
        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity
                .created(createdURI)
                .body(VegetableGardenDetails.fromGarden(created));


    }
    @PutMapping("/vegetableGarden/{id}")
    public VegetableGardenDetails editDetails(@AuthenticationPrincipal User logguedUser,
                                    @PathVariable Long id,
                                    @RequestPart("file") MultipartFile file,
                                    @Valid @RequestPart("editGarden") CreateVegetableGarden editGarden) {
        VegetableGarden garden=gardenService.findById(id);
        VegetableGarden edited = gardenService.edit(garden, editGarden,  file, logguedUser);

        return VegetableGardenDetails.fromGarden(edited);

    }

    @DeleteMapping("/vegetableGarden/{id}")//Hay que hacer todavia las politicas de borrado
    public ResponseEntity<?> delete(@AuthenticationPrincipal User user,@PathVariable Long id) {
        //.Question quest=questionService.findById(id);
        if(userService.checkUserLogedInGarden(user.getId(), id))
            gardenService.deleteById(id);

        return ResponseEntity.noContent().build();
    }



}
