package com.licenta.controller;

import com.licenta.model.Sugestie;
import com.licenta.service.SugestieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sugestii")
public class SugestieController {

    private final SugestieService service;

    public SugestieController(SugestieService service) {
        this.service = service;
    }

    @Operation(summary = "Returnează toate sugestiile")
    @GetMapping
    public List<Sugestie> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Returnează o sugestie după ID")
    @GetMapping("/{id}")
    public ResponseEntity<Sugestie> getById(
            @Parameter(description = "ID-ul sugestiei") @PathVariable Long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @Operation(summary = "Creează o sugestie nouă")
    @PostMapping
    public Sugestie create(
            @Parameter(description = "Obiectul sugestie de creat")@Valid @RequestBody Sugestie sugestie) {
        return service.save(sugestie);
    }

    @Operation(summary = "Șterge o sugestie după ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID-ul sugestiei de șters") @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
