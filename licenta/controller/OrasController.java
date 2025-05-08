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

    @Operation(summary = "Returneaza toate orasele")
    @GetMapping
    public List<Oras> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Returneaza un oras dupa ID")
    @GetMapping("/{id}")
    public ResponseEntity<Oras> getById(
            @Parameter(description = "ID-ul orașului") @PathVariable Long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @Operation(summary = "Creeaza un oras nou")
    @PostMapping
    public Oras create(
            @Parameter(description = "Oras de creat")@Valid @RequestBody Oras oras) {
        return service.save(oras);
    }

    @Operation(summary = "Actualizeaza un oras existent")
    @PutMapping("/{id}")
    public ResponseEntity<Oras> update(
            @Parameter(description = "ID-ul orașului de actualizat") @PathVariable("id") Long id,
            @Parameter(description = "Datele noi pentru oras")@Valid @RequestBody Oras orasNou) {
        return ResponseEntity.of(service.update(id, orasNou));
    }

    @Operation(summary = "Sterge un oras dupa ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID-ul orasului de sters") @PathVariable("id") Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
