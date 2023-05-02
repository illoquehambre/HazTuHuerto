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
public class NoteResponse {

    private String title;
    private Long id;
    private String content;
    private LocalDate lastModifedDate;
    private String cultivationName;

    public static NoteResponse fromNote(Note note) {

        return NoteResponse.builder()
                .title(note.getTitle())
                .cultivationName(note.getCultivation().getName())
                .id(note.getId())
                .content(note.getContent())
                .lastModifedDate(note.getLastModifiedDate())
                .build();
    }
}
