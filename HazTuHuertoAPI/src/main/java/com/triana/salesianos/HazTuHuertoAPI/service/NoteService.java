package com.triana.salesianos.HazTuHuertoAPI.service;

import com.triana.salesianos.HazTuHuertoAPI.files.service.StorageService;
import com.triana.salesianos.HazTuHuertoAPI.model.*;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.note.CreateNote;
import com.triana.salesianos.HazTuHuertoAPI.repository.NoteRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserService userService;
    private final StorageService storageService;


    public List<Note> findAll() {

        List<Note> result = noteRepository.findAll();

        if (result.isEmpty())
            throw new EntityNotFoundException("No notes with this search criteria");

        return noteRepository.findAll();
    }

    public Note findById(Long id) {

        return noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No note with id: " + id));

    }
    /*public List<Note> findByGarden(VegetableGarden garden, User user) {
        if(userService.checkUserLogedInGarden(user.getId(), garden.getId())) {
            List<Note> result = noteRepository.findAllByGarden(garden);

            if (result.isEmpty())
                throw new EntityNotFoundException("No hay huertos pa este usuario");

            return noteRepository.findAll();
        }else
            throw new SecurityException("Access Denied");

    }*/

    public Note save(CreateNote newNote, Cultivation cultivation, User user) {
        //Hacer comprobaciones de usuario
        if(userService.checkUserLogedInPatch(user.getId(), cultivation.getPatch().getId())) {
            Note note=Note.builder()
                    .title(newNote.getTitle())
                    .content(newNote.getContent())
                    .cultivation(cultivation)
                    .lastModifiedDate(LocalDate.now())
                    .build();
           return noteRepository.save(note);


        }else
            throw new SecurityException("Access Denied.");

    }

    public void deleteById(Long id) {
        //Esto es una gitanada, perdón
        if (noteRepository.existsById(id)){
            noteRepository.deleteById(id);
        }

    }

    public Note edit(Note note, CreateNote edit, User user) {
        //Yo no se si esto está bien, habria qwu preguntarle al señor lusimi, parese chapuza
        if(userService.checkUserLogedInPatch(user.getId(), note.getCultivation().getPatch().getId())) {

            note.setTitle(edit.getTitle());
            note.setContent(edit.getContent());
            note.setLastModifiedDate(LocalDate.now());
        }
        return noteRepository.save(note);
    }

}
