package com.triana.salesianos.HazTuHuertoAPI.controller;

import com.triana.salesianos.HazTuHuertoAPI.model.*;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.note.CreateNote;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.note.NoteResponse;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.patch.CreatePatch;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.patch.EditPatchCultivation;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.patch.PatchDetails;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.patch.PatchSimplify;
import com.triana.salesianos.HazTuHuertoAPI.repository.CultivationRepository;
import com.triana.salesianos.HazTuHuertoAPI.repository.UserRepository;
import com.triana.salesianos.HazTuHuertoAPI.repository.VegetableGardenRepository;
import com.triana.salesianos.HazTuHuertoAPI.service.CultivationService;
import com.triana.salesianos.HazTuHuertoAPI.service.NoteService;
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

@RestController
@AllArgsConstructor
@OpenAPIDefinition(info = @Info(title ="Haz-Tu-Huerto"))
@Tag(name = "Note", description = "This class implements Restcontrollers for the Entity Note")
@CrossOrigin("http://localhost:3000")
public class NoteController {

    private final NoteService noteService;
    private final CultivationService cultivationService;
    private final CultivationRepository cultivationRepository;
    private final UserService userService;
    private final UserRepository userRepository;


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


    @PostMapping("/note/{id}")//El id es de cultivation
    public ResponseEntity<NoteResponse> register(@Valid @RequestBody CreateNote newNote,
                                                    @PathVariable Long id,
                                                    @AuthenticationPrincipal User user) {

        Cultivation cultivation = cultivationService.findById(id);
        Note created = noteService.save(newNote,cultivation, user);
        cultivation.addNote(created);
        cultivationRepository.save(cultivation);
        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity
                .created(createdURI)
                .body(NoteResponse.fromNote(created));


    }
    @PutMapping("/note/{id}") //Revisar la respuesta
    public NoteResponse editDetails(@AuthenticationPrincipal User logguedUser,
                                     @PathVariable Long id,
                                     @Valid @RequestBody CreateNote edit) {
        Note note=noteService.findById(id);
        Note edited = noteService.edit(note, edit,logguedUser);

        return NoteResponse.fromNote(edited);

    }

    //No se que cojones pasa que este delete no lo pilla, no hay erroreres pero tampoco devuelve 204 ni nada
    //raro
    @DeleteMapping("/note/{id}")//Hay que hacer todavia las politicas de borrado
    public ResponseEntity<?> deleteNote(@AuthenticationPrincipal User user, @PathVariable Long id) {
        Note note =noteService.findById(id);
        if(userService.checkUserLogedInPatch(user.getId(), note.getCultivation().getPatch().getId()))
            noteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }



}
