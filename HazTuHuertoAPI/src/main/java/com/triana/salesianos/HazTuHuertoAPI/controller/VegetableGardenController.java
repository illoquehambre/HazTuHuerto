package com.triana.salesianos.HazTuHuertoAPI.controller;

import com.triana.salesianos.HazTuHuertoAPI.model.Question;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.model.VegetableGarden;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.PageDto;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.user.EditUser;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.user.UserDetailsDto;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @Operation(summary = "This method list all Gardens of an user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "At least one garden was found",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserPageResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                  "content": [
                                                      {
                                                          "id": "3",
                                                          "name": "Brickellia incana A. Gray",
                                                          "latitude": "26.511966",
                                                          "longitude": "-78.640226",
                                                          "img": "mandarina.jpg",
                                                          "numPatch": 1,
                                                          "patchList": [
                                                              {
                                                                  "cultivation": {
                                                                      "cultivationName": "Aleutian Brome",
                                                                      "daysLeft": -263
                                                                  },
                                                                  "id": 7
                                                              }
                                                          ]
                                                      },
                                                      {
                                                          "id": "4",
                                                          "name": "Malpighia setosa Spreng.",
                                                          "latitude": "-37.8136276",
                                                          "longitude": "144.9630576",
                                                          "img": "mandarina.jpg",
                                                          "numPatch": 3,
                                                          "patchList": [
                                                              {
                                                                  "cultivation": {
                                                                      "cultivationName": "Mt. Etna Broom",
                                                                      "daysLeft": -251
                                                                  },
                                                                  "id": 14
                                                              },
                                                              {
                                                                  "cultivation": {
                                                                      "cultivationName": "Chestnutleaf Trumpetbush",
                                                                      "daysLeft": -339
                                                                  },
                                                                  "id": 15
                                                              },
                                                              {
                                                                  "cultivation": {
                                                                      "cultivationName": "New Mexican Nightshade",
                                                                      "daysLeft": -171
                                                                  },
                                                                  "id": 45
                                                              }
                                                          ]
                                                      },
                                                  ],
                                                  "last": true,
                                                  "first": true,
                                                  "totalPages": 1,
                                                  "totalElements": 2
                                              }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "Any garden were found",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed to do this request",
                    content = @Content),
    })
    @GetMapping("/vegetableGarden")
    public PageDto<VegetableGardenResponse> findAll(@AuthenticationPrincipal User user,
                                                    @PageableDefault(size = 5, page = 0) Pageable pageable) {
        return gardenService.search(pageable,user);
    }


    @Operation(summary = "This method find a garden by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "the garden was successfully found",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserDetailsDto.class)),
                            examples = @ExampleObject(value = """
                                    {
                                          "id": "2",
                                          "name": "Astragalus coltonii M.E. Jones var. coltonii",
                                          "latitude": "31.2633956",
                                          "longitude": "72.3110986",
                                          "patchList": [
                                              {
                                                  "name": "Shortleaf Crabgrass",
                                                  "id": 12,
                                                  "cultivation": {
                                                      "name": "Arizona Milkvetch",
                                                      "plantDate": "2022-10-23",
                                                      "harvestDate": "2023-09-11",
                                                      "daysLeft": -323,
                                                      "daysPlanted": 323,
                                                      "cultivationImg": "mandarina.jpg",
                                                      "numNotes": 1
                                                  }
                                              },
                                              {
                                                  "name": "Wrinkleseed Pygmyweed",
                                                  "id": 28,
                                                  "cultivation": {
                                                      "name": "Mockernut Hickory",
                                                      "plantDate": "2023-01-20",
                                                      "harvestDate": "2023-10-21",
                                                      "daysLeft": -274,
                                                      "daysPlanted": 274,
                                                      "cultivationImg": "mandarina.jpg",
                                                      "numNotes": 1
                                                  }
                                              }
                                          ],
                                          "img": "mandarina.jpg"
                                      }
                                    """)) }),
            @ApiResponse(responseCode = "404",
                    description = "The garden was not found",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed to do this request",
                    content = @Content),
    })
    @GetMapping("/vegetableGarden/{id}")
    public VegetableGardenDetails findGardenById(@AuthenticationPrincipal User user, @PathVariable Long id) {
        return VegetableGardenDetails.fromGarden(gardenService.findById(id));

    }


    @Operation(summary = "This method create a new garden")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado un nuevo Huerto",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VegetableGardenDetails.class)),
                            examples = @ExampleObject(value = """
                                     {
                                          "id": "21",
                                          "name": "garden",
                                          "latitude": null,
                                          "longitude": null,
                                          "patchList": [],
                                          "img": "Imagen de WhatsApp 2023-04-17 a las 10.23.10_570146.jpg"
                                      }
                                    """)) }),
            @ApiResponse(responseCode = "400",
                    description = "The data submited have a wrong format",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "The user account was not found",
                    content = @Content),
    })
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


    @Operation(summary = "This method edit a garden")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The operation was succesfully",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VegetableGardenDetails.class)),
                            examples = @ExampleObject(value = """
                                    {
                                          "id": "21",
                                          "name": "garden",
                                          "latitude": null,
                                          "longitude": null,
                                          "patchList": [],
                                          "img": "Imagen de WhatsApp 2023-04-17 a las 10.23.10_570146.jpg"
                                      }
                                    """)) }),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed to do this request",
                    content = @Content),
    })
    @PutMapping("/vegetableGarden/{id}")
    public VegetableGardenDetails editDetails(@AuthenticationPrincipal User logguedUser,
                                    @PathVariable Long id,
                                    @RequestPart("file") MultipartFile file,
                                    @Valid @RequestPart("editGarden") CreateVegetableGarden editGarden) {
        VegetableGarden garden=gardenService.findById(id);
        VegetableGarden edited = gardenService.edit(garden, editGarden,  file, logguedUser);

        return VegetableGardenDetails.fromGarden(edited);

    }

    @Operation(summary = "This method delete a garden.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "No content ",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed to do this request",
                    content = @Content),
    })
    @DeleteMapping("/vegetableGarden/{id}")//Hay que hacer todavia las politicas de borrado
    public ResponseEntity<?> delete(@AuthenticationPrincipal User user,@PathVariable Long id) {
        //Question quest=questionService.findById(id);
        if(userService.checkUserLogedInGarden(user.getId(), id))
            gardenService.deleteById(id);

        return ResponseEntity.noContent().build();
    }



}
