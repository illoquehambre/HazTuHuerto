package com.triana.salesianos.HazTuHuertoAPI.files.controller;


import com.triana.salesianos.HazTuHuertoAPI.files.dto.FileResponse;
import com.triana.salesianos.HazTuHuertoAPI.files.service.StorageService;
import com.triana.salesianos.HazTuHuertoAPI.files.utils.MediaTypeUrlResource;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.answer.AnswerResponse;
import com.triana.salesianos.HazTuHuertoAPI.model.dto.question.QuestionDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final StorageService storageService;


    //sube varios archivos
    @Operation(summary = "This method creates upload more than one file at once")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "The files were successfully uploaded"),
            @ApiResponse(responseCode = "400",
                    description = "The data submited have a wrong format",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed no do this request",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "You are not allowed no do this request",
                    content = @Content),
    })
    @PostMapping("/upload/files")
    public ResponseEntity<?> upload(@RequestPart("files") MultipartFile[] files) {

        //FileResponse response = uploadFile(file);

        List<FileResponse> result = Arrays.stream(files)
                .map(this::uploadFile)
                .toList();

        return ResponseEntity
                //.created(URI.create(response.getUri()))
                .status(HttpStatus.CREATED)
                .body(result);
    }

    //sube un archivo
    @Operation(summary = "This method upload one file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "The files were successfully uploaded"),
            @ApiResponse(responseCode = "400",
                    description = "The data submited have a wrong format",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed no do this request",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "You are not allowed no do this request",
                    content = @Content),
    })
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestPart("file") MultipartFile file) {

        FileResponse response = uploadFile(file);

        return ResponseEntity.created(URI.create(response.getUri())).body(response);
    }

    private FileResponse uploadFile(MultipartFile file) {
        String name = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(name)
                .toUriString();

        return FileResponse.builder()
                .name(name)
                .size(file.getSize())
                .type(file.getContentType())
                .uri(uri)
                .build();
    }

    //pa descargar
    @Operation(summary = "This method download a Resource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The files were successfully downloaded",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Resource.class))
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "The data submited have a wrong format",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "The fileName was not found",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "You are not allowed no do this request",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "You are not allowed no do this request",
                    content = @Content),
    })
    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename){
        MediaTypeUrlResource resource =
                (MediaTypeUrlResource) storageService.loadAsResource(filename);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", resource.getType())
                .body(resource);
    }

}
