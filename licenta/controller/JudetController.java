package com.licenta.controller;

import com.licenta.model.Judet;
import com.licenta.service.JudetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/judete")
public class JudetController {

    private final JudetService service;

    public JudetController(JudetService service) {
        this.service = service;
    }

    @Operation(summary = "Returnează toate județele")
    @GetMapping
    public List<Judet> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Returnează un județ după ID")
    @GetMapping("/{id}")
    public ResponseEntity<Judet> getById(
            @Parameter(description = "ID-ul județului") @PathVariable Long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @Operation(summary = "Creează un județ nou")
    @PostMapping
    public Judet create(
            @Parameter(description = "Obiectul județ de creat")@Valid @RequestBody Judet judet) {
        return service.save(judet);
    }

    @Operation(summary = "Actualizează un județ existent")
    @PutMapping("/{id}")
    public ResponseEntity<Judet> update(
            @Parameter(description = "ID-ul județului de actualizat") @PathVariable Long id,
            @Parameter(description = "Datele noi pentru județ")@Valid @RequestBody Judet judetNou) {
        return ResponseEntity.of(service.update(id, judetNou));
    }

    @Operation(summary = "Șterge un județ după ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID-ul județului de șters") @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
