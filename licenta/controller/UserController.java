package com.licenta.controller;

import com.licenta.model.User;
import com.licenta.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @Operation(summary = "Returnează toți utilizatorii")
    @GetMapping
    public List<User> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Returnează un utilizator după ID")
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(
            @Parameter(description = "ID-ul utilizatorului") @PathVariable Long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @Operation(summary = "Creează un utilizator nou")
    @PostMapping
    public User create(
            @Parameter(description = "Obiectul utilizator de creat")@Valid @RequestBody User user) {
        return service.save(user);
    }
}
