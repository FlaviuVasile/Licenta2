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

    @Operation(summary = "Returneaza toate sugestiile")
    @GetMapping
    public List<Sugestie> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Returneaza o sugestie după ID")
    @GetMapping("/{id}")
    public ResponseEntity<Sugestie> getById(
            @Parameter(description = "ID-ul sugestiei") @PathVariable Long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @Operation(summary = "Creeaza o sugestie noua")
    @PostMapping
    public Sugestie create(
            @Parameter(description = "Sugestie de creat")@Valid @RequestBody Sugestie sugestie) {
        return service.save(sugestie);
    }

    @Operation(summary = "Sterge o sugestie dupa ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID-ul sugestiei de sters") @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
