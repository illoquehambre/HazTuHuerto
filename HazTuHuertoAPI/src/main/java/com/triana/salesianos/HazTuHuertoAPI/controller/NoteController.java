package com.triana.salesianos.HazTuHuertoAPI.controller;

import com.triana.salesianos.HazTuHuertoAPI.model.*;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.note.CreateNote;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.note.NoteResponse;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.patch.PatchDetails;
import com.triana.salesianos.HazTuHuertoAPI.repository.NoteRepository;
import com.triana.salesianos.HazTuHuertoAPI.repository.PatchRepository;
import com.triana.salesianos.HazTuHuertoAPI.repository.UserRepository;
import com.triana.salesianos.HazTuHuertoAPI.service.NoteService;
import com.triana.salesianos.HazTuHuertoAPI.service.PatchService;
import com.triana.salesianos.HazTuHuertoAPI.service.UserService;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@AllArgsConstructor
@OpenAPIDefinition(info = @Info(title ="Haz-Tu-Huerto"))
@Tag(name = "Note", description = "This class implements Restcontrollers for the Entity Note")
@CrossOrigin("http://localhost:3000")
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PatchService patchService;
    private final NoteRepository noteRepository;


    @Operation(summary = "This method find a note by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "the note was successfully found",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = NoteResponse.class)),
                            examples = @ExampleObject(value = """
                                    {
                                         "title": "Lithium Carbonate",
                                         "id": 7,
                                         "content": "error: Field 'fake_sentence' not found",
                                         "lastModifedDate": "2022-05-29",
                                         "cultivationName": "Aleutian Brome"
                                     }
                                    """)) }),
            @ApiResponse(responseCode = "404",
                    description = "The patch was not found",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed to do this request",
                    content = @Content),
    })
    @GetMapping("/note/{id}")
    public NoteResponse findById(@AuthenticationPrincipal User user,
                                 @PathVariable Long id) {
        Note note = noteService.findById(id);
        if(userService.checkUserLogedInPatch(user.getId(), note.getCultivation().getPatch().getId()))
            return NoteResponse.fromNote(note);
            //sin embargo al hacer la conversión si que coge de forma adecuada los datos de garden
        else
            throw new SecurityException("Access Denied");

    }


    @Operation(summary = "This method create a new note on a cultivation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado una nueva Nota en su Cultivo",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PatchDetails.class)),
                            examples = @ExampleObject(value = """
                                     {
                                         "title": "Lithium Carbonate",
                                         "id": 7,
                                         "content": "error: Field 'fake_sentence' not found",
                                         "lastModifedDate": "2022-05-29",
                                         "cultivationName": "Aleutian Brome"
                                     }
                                    """)) }),
            @ApiResponse(responseCode = "400",
                    description = "The data submited have a wrong format",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "The user account was not found",
                    content = @Content),
    })
    @PostMapping("/note/{id}")//El id es de cultivation
    public ResponseEntity<NoteResponse> register(@Valid @RequestBody CreateNote newNote,
                                                    @PathVariable Long id,
                                                    @AuthenticationPrincipal User user) {

        Cultivation cultivation = patchService.findCultivationById(id);
        Note created = noteService.save(newNote,cultivation, user);
        cultivation.addNote(created);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity
                .created(createdURI)
                .body(NoteResponse.fromNote(noteRepository.save(created)));


    }
    @Operation(summary = "This method edit a note ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The operation was succesfully",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = NoteResponse.class)),
                            examples = @ExampleObject(value = """
                                    {
                                         "title": "Lithium Carbonate",
                                         "id": 7,
                                         "content": "error: Field 'fake_sentence' not found",
                                         "lastModifedDate": "2022-05-29",
                                         "cultivationName": "Aleutian Brome"
                                     }
                                    """)) }),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed to do this request",
                    content = @Content),
    })


    @PutMapping("/note/{id}") //Revisar la respuesta
    public NoteResponse editDetails(@AuthenticationPrincipal User logguedUser,
                                     @PathVariable Long id,
                                     @Valid @RequestBody CreateNote edit) {
        Note note=noteService.findById(id);
        Note edited = noteService.edit(note, edit,logguedUser);

        return NoteResponse.fromNote(edited);

    }

    @Operation(summary = "This method delete a note.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "No content ",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed to do this request",
                    content = @Content),
    })
    @DeleteMapping("/note/{id}")//Hay que hacer todavia las politicas de borrado
    public ResponseEntity<?> deleteNote(@AuthenticationPrincipal User user, @PathVariable Long id) {
            Note note =noteService.findById(id);//Da problemas
        if(userService.checkUserLogedInPatch(user.getId(), note.getCultivation().getPatch().getId()))
            noteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }



}
