package com.licenta.controller;

import com.licenta.model.Restaurant;
import com.licenta.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurante")
public class RestaurantController {

    private final RestaurantService service;

    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @Operation(summary = "Returnează toate restaurantele")
    @GetMapping
    public List<Restaurant> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Returnează un restaurant după ID")
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getById(
            @Parameter(description = "ID-ul restaurantului") @PathVariable Long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @Operation(summary = "Creează un restaurant nou")
    @PostMapping
    public Restaurant create(
            @Parameter(description = "Obiectul restaurant de creat") @Valid @RequestBody Restaurant restaurant) {
        return service.save(restaurant);
    }

    @Operation(summary = "Actualizează un restaurant existent")
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> update(
            @Parameter(description = "ID-ul restaurantului de actualizat")  @PathVariable Long id,
            @Parameter(description = "Datele noi pentru restaurant")@Valid @RequestBody Restaurant updated) {
        return ResponseEntity.of(service.update(id, updated));
    }

    @Operation(summary = "Șterge un restaurant după ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID-ul restaurantului de șters") @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
