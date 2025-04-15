package com.licenta.controller;

import com.licenta.model.Hotel;
import com.licenta.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hoteluri")
public class HotelController {

    private final HotelService service;

    public HotelController(HotelService service) {
        this.service = service;
    }

    @Operation(summary = "Returnează toate hotelurile")
    @GetMapping
    public List<Hotel> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Caută hotel după ID")
    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getById(@Parameter(description = "ID-ul hotelului")@PathVariable Long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @Operation(summary = "Creează un hotel nou")
    @PostMapping
    public Hotel create(@Parameter(description = "Obiectul hotel de creat")@Valid @RequestBody Hotel hotel) {
        return service.save(hotel);
    }

    @Operation(summary = "Actualizează un hotel existent după ID")
    @PutMapping("/{id}")
    public ResponseEntity<Hotel> update(
            @Parameter(description = "ID-ul hotelului de actualizat") @PathVariable Long id,
            @Parameter(description = "Datele noi pentru hotel")@Valid  @RequestBody Hotel hotel)  {
        return ResponseEntity.of(service.update(id, hotel));
    }

    @Operation(summary = "Șterge un hotel după ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "ID-ul hotelului de șters")@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
