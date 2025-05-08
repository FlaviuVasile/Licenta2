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

    @Operation(summary = "Returneaza toate judetele")
    @GetMapping
    public List<Judet> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Returneaza un judet după ID")
    @GetMapping("/{id}")
    public ResponseEntity<Judet> getById(
            @Parameter(description = "ID-ul judetului") @PathVariable Long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @Operation(summary = "Creeaza un judet nou")
    @PostMapping
    public Judet create(
            @Parameter(description = "Judet creat")@Valid @RequestBody Judet judet) {
        return service.save(judet);
    }

    @Operation(summary = "Actualizeaza un judet existent")
    @PutMapping("/{id}")
    public ResponseEntity<Judet> update(
            @Parameter(description = "ID-ul judetului de actualizat") @PathVariable("id") Long id,
            @Parameter(description = "Datele noi pentru judet")@Valid @RequestBody Judet judetNou) {
        return ResponseEntity.of(service.update(id, judetNou));
    }

    @Operation(summary = "Sterge un judet dupa ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID-ul judetului de sters") @PathVariable("id") Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
