package com.triana.salesianos.HazTuHuertoAPI.controller;

import com.triana.salesianos.HazTuHuertoAPI.model.Patch;
import com.triana.salesianos.HazTuHuertoAPI.model.User;
import com.triana.salesianos.HazTuHuertoAPI.model.VegetableGarden;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.patch.*;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.user.UserDetailsDto;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.vegetableGarden.VegetableGardenDetails;
import com.triana.salesianos.HazTuHuertoAPI.repository.UserRepository;
import com.triana.salesianos.HazTuHuertoAPI.repository.VegetableGardenRepository;
import com.triana.salesianos.HazTuHuertoAPI.service.PatchService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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



    @Operation(summary = "This method list all cultivation in the history of a patch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "At least one garden was found",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PatchHistory.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                   "name": "Foothill Larkspur",
                                                   "id": 7,
                                                   "cultivationHistory": [
                                                       {
                                                           "name": "Bastardsage",
                                                           "plantDate": "2023-01-02",
                                                           "harvestDate": "2023-06-30",
                                                           "daysLeft": -179,
                                                           "daysPlanted": 179,
                                                           "cultivationImg": "mandarina.jpg",
                                                           "numNotes": 1
                                                       },
                                                       {
                                                           "name": "Dwarf Nicker",
                                                           "plantDate": "2023-05-10",
                                                           "harvestDate": "2023-12-02",
                                                           "daysLeft": -206,
                                                           "daysPlanted": 206,
                                                           "cultivationImg": "mandarina.jpg",
                                                           "numNotes": 1
                                                       },
                                                       {
                                                           "name": "Giant Forget-me-not",
                                                           "plantDate": "2023-02-20",
                                                           "harvestDate": "2023-07-15",
                                                           "daysLeft": -145,
                                                           "daysPlanted": 145,
                                                           "cultivationImg": "mandarina.jpg",
                                                           "numNotes": 1
                                                       },
                                                   ]
                                               }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "Any cultivation were found",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed to do this request",
                    content = @Content),
    })
    @GetMapping("/patch/history/{id}")
    public PatchHistory findHistoryByPatchId(@AuthenticationPrincipal User user,
                                      @PathVariable Long id) {
        Patch patch = patchService.findById(id);
        if(userService.checkUserLogedInPatch(user.getId(), id))
            return PatchHistory.fromPatch(patch);
        else
            throw new SecurityException("Access Denied");
    }


    @Operation(summary = "This method find a patch by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "the patch was successfully found",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PatchDetails.class)),
                            examples = @ExampleObject(value = """
                                    {
                                        "name": "Foothill Larkspur",
                                        "id": 7,
                                        "cultivation": {
                                            "id": 7,
                                            "name": "Aleutian Brome",
                                            "variety": "Bromus aleutensis Trin. ex Griseb.",
                                            "plantDate": "2023-01-29",
                                            "harvestDate": "2023-10-19",
                                            "daysLeft": 126,
                                            "daysPlanted": 137,
                                            "cultivationImg": "mandarina.jpg",
                                            "notes": [
                                                {
                                                    "title": "Lithium Carbonate",
                                                    "id": 7
                                                }
                                            ]
                                        },
                                        "gardenName": "Brickellia incana A. Gray"
                                    }
                                    """)) }),
            @ApiResponse(responseCode = "404",
                    description = "The patch was not found",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed to do this request",
                    content = @Content),
    })
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

    @Operation(summary = "This method create a new patch and cultivation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado una nueva Parcela y Cultivo",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PatchDetails.class)),
                            examples = @ExampleObject(value = """
                                     {
                                           "name": "newPatch",
                                           "id": 51,
                                           "cultivation": {
                                               "id": 101,
                                               "name": "newCultivation",
                                               "variety": "tomates negros",
                                               "plantDate": null,
                                               "harvestDate": null,
                                               "daysLeft": 0,
                                               "daysPlanted": 0,
                                               "cultivationImg": "Imagen de WhatsApp 2023-04-17 a las 10.23.12_461604.jpg",
                                               "notes": []
                                           },
                                           "gardenName": "Brickellia incana A. Gray"
                                       }
                                    """)) }),
            @ApiResponse(responseCode = "400",
                    description = "The data submited have a wrong format",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "The user account was not found",
                    content = @Content),
    })
    //Esto crea tanto una parcela como un cultivo
    @PostMapping("/patch/garden/{id}")//El id es de garden pero crea un nuevo patch en este
    public ResponseEntity<PatchDetails> createPatch(@Valid @RequestPart CreatePatchCultivation newPatch,
                                                    @RequestPart MultipartFile file,
                                                    @PathVariable Long id,
                                                    @AuthenticationPrincipal User user) {

        VegetableGarden garden = gardenService.findById(id);
        Patch created = patchService.save(newPatch,garden, user, file);
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
    @Operation(summary = "This method edit a patch ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The operation was succesfully",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PatchDetails.class)),
                            examples = @ExampleObject(value = """
                                    {
                                        "name": "editedPatch",
                                        "id": 7,
                                        "cultivation": {
                                            "id": 7,
                                            "name": "tomates",
                                            "variety": "a",
                                            "plantDate": "2023-05-24",
                                            "harvestDate": "2023-08-24",
                                            "daysLeft": 70,
                                            "daysPlanted": 22,
                                            "cultivationImg": "juicy-mandarine-on-a-white-background-fruit-contour-drawing-icon-illustration-vector.jpg",
                                            "notes": [
                                                {
                                                    "title": "Lithium Carbonate",
                                                    "id": 7
                                                }
                                            ]
                                        },
                                        "gardenName": "Brickellia incana A. Gray"
                                    }
                                    """)) }),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed to do this request",
                    content = @Content),
    })

    @PutMapping("/patch/{id}") //Revisar la respuesta
    public PatchDetails editDetails(@AuthenticationPrincipal User logguedUser,
                                     @PathVariable Long id,
                                    @RequestPart("file") MultipartFile file,
                                     @Valid @RequestPart("editPatch") CreatePatchCultivation editPatch) {
        Patch patch=patchService.findById(id);
        Patch edited = patchService.edit(patch, editPatch,logguedUser, file);

        return PatchDetails.fromPatch(edited);

    }

    @Operation(summary = "This method delete a patch.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "No content ",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed to do this request",
                    content = @Content),
    })
    @DeleteMapping("/patch/{id}")//Hay que hacer todavia las politicas de borrado
    public ResponseEntity<?> deletePatch(@AuthenticationPrincipal User user, @PathVariable Long id) {
        if(userService.checkUserLogedInPatch(user.getId(), id))
            patchService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "This method divide a patch creating a new one with the same cultivation history ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado una nueva Parcela y Cultivo",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PatchDetails.class)),
                            examples = @ExampleObject(value = """
                                     {
                                           "name": "newPatch",
                                           "id": 51,
                                           "cultivation": {
                                               "id": 101,
                                               "name": "newCultivation",
                                               "variety": "tomates negros",
                                               "plantDate": null,
                                               "harvestDate": null,
                                               "daysLeft": 0,
                                               "daysPlanted": 0,
                                               "cultivationImg": "Imagen de WhatsApp 2023-04-17 a las 10.23.12_461604.jpg",
                                               "notes": []
                                           },
                                           "gardenName": "Brickellia incana A. Gray"
                                       }
                                    """)) }),
            @ApiResponse(responseCode = "400",
                    description = "The data submited have a wrong format",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "The user account was not found",
                    content = @Content),
    })
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

    @Operation(summary = "This method harvest a patch, adding the cultivation to the historic and creating a new one")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The operation was succesfully",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PatchDetails.class)),
                            examples = @ExampleObject(value = """
                                    {
                                        "name": "editedPatch",
                                        "id": 7,
                                        "cultivation": {
                                            "id": 7,
                                            "name": "tomates",
                                            "variety": "a",
                                            "plantDate": "2023-05-24",
                                            "harvestDate": "2023-08-24",
                                            "daysLeft": 70,
                                            "daysPlanted": 22,
                                            "cultivationImg": "juicy-mandarine-on-a-white-background-fruit-contour-drawing-icon-illustration-vector.jpg",
                                            "notes": [
                                                {
                                                    "title": "Lithium Carbonate",
                                                    "id": 7
                                                }
                                            ]
                                        },
                                        "gardenName": "Brickellia incana A. Gray"
                                    }
                                    """)) }),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed to do this request",
                    content = @Content),
    })
    @PutMapping("/patch/harvest/{id}") //Revisar la respuesta
    public PatchDetails harvest(@AuthenticationPrincipal User logguedUser,
                                    @PathVariable Long id,
                                    @RequestPart("file") MultipartFile file,
                                    @Valid @RequestPart("editPatch") CreatePatchCultivation editPatch) {

        Patch patch=patchService.findById(id);
        Patch edited = patchService.harvest(patch, logguedUser, file, editPatch);

        return PatchDetails.fromPatch(edited);

    }

}
