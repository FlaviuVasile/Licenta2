package com.licenta.controller;

import com.licenta.model.Oras;
import com.licenta.service.OrasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orase")
public class OrasController {

    private final OrasService service;

    public OrasController(OrasService service) {
        this.service = service;
    }

    @Operation(summary = "Returnează toate orașele")
    @GetMapping
    public List<Oras> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Returnează un oraș după ID")
    @GetMapping("/{id}")
    public ResponseEntity<Oras> getById(
            @Parameter(description = "ID-ul orașului") @PathVariable Long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @Operation(summary = "Creează un oraș nou")
    @PostMapping
    public Oras create(
            @Parameter(description = "Obiectul oraș de creat")@Valid @RequestBody Oras oras) {
        return service.save(oras);
    }

    @Operation(summary = "Actualizează un oraș existent")
    @PutMapping("/{id}")
    public ResponseEntity<Oras> update(
            @Parameter(description = "ID-ul orașului de actualizat") @PathVariable Long id,
            @Parameter(description = "Datele noi pentru oraș")@Valid @RequestBody Oras orasNou) {
        return ResponseEntity.of(service.update(id, orasNou));
    }

    @Operation(summary = "Șterge un oraș după ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID-ul orașului de șters") @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
