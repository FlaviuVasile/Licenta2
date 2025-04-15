package com.licenta.controller;

import com.licenta.model.Locatie;
import com.licenta.service.LocatieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locatii")
public class LocatieController {

    private final LocatieService service;

    public LocatieController(LocatieService service) {
        this.service = service;
    }

    @Operation(summary = "Returnează toate locațiile")
    @GetMapping
    public List<Locatie> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Creează o locație nouă")
    @PostMapping
    public Locatie create(
            @Parameter(description = "Obiectul locație de creat")@Valid @RequestBody Locatie locatie) {
        return service.save(locatie);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Șterge o locație după ID")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Returnează o locație după ID")
    @GetMapping("/{id}")
    public Locatie getById(
            @Parameter(description = "ID-ul locației") @PathVariable Long id) {
        return service.getById(id);
    }
}
