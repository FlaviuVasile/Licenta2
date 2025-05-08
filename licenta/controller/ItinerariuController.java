package com.licenta.controller;

import com.licenta.model.Itinerariu;
import com.licenta.service.ItinerariuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itinerarii")
public class ItinerariuController {

    private final ItinerariuService service;

    public ItinerariuController(ItinerariuService service) {
        this.service = service;
    }

    @Operation(summary = "Returneaza toate itinerariile")
    @GetMapping
    public List<Itinerariu> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Returneaza un itinerariu după ID")
    @GetMapping("/{id}")
    public ResponseEntity<Itinerariu> getById(
            @Parameter(description = "ID-ul itinerariului") @PathVariable Long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @Operation(summary = "Creeaza un itinerariu nou")
    @PostMapping
    public Itinerariu create(
            @Parameter(description = "Itinerariu de creat")@Valid @RequestBody Itinerariu itinerariu) {
        return service.save(itinerariu);
    }

    @Operation(summary = "Actualizeaza un itinerariu existent după ID")
    @PutMapping("/{id}")
    public ResponseEntity<Itinerariu> update(
            @Parameter(description = "ID-ul itinerariului de actualizat") @PathVariable("id") Long id,
            @Parameter(description = "Datele noi pentru itinerariu")@Valid @RequestBody Itinerariu updated) {
        return ResponseEntity.of(service.update(id, updated));
    }

    @Operation(summary = "Sterge un itinerariu după ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID-ul itinerariului de sters") @PathVariable("id") Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
