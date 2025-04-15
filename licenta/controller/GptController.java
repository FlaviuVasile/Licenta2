package com.licenta.controller;
import com.licenta.dto.IntrebareDTO;
import com.licenta.dto.RaspunsDTO;
import com.licenta.service.GptService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gpt")
public class GptController {

    private final GptService gptService;

    public GptController(GptService gptService) {
        this.gptService = gptService;
    }

    @PostMapping("/intreaba")
    @Operation(summary = "Primește o întrebare și returnează răspunsul generat de GPT cu date din aplicație")
    public ResponseEntity<RaspunsDTO> intreabaGpt(@Valid @RequestBody IntrebareDTO intrebareDTO) {
        String raspuns = gptService.raspunde(intrebareDTO);
        return ResponseEntity.ok(new RaspunsDTO(raspuns));
    }
}
