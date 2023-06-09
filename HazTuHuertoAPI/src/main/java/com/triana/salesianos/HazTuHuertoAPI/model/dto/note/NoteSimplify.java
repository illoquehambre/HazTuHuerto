package com.triana.salesianos.HazTuHuertoAPI.model.dto.note;

import com.triana.salesianos.HazTuHuertoAPI.model.Note;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteSimplify {

    private String title;
    private Long id;


    public static NoteSimplify fromNote(Note note) {

        return NoteSimplify.builder()
                .title(note.getTitle())
                .id(note.getId())
                .build();
    }
}
