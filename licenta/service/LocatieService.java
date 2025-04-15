package com.licenta.service;

import com.licenta.model.Locatie;
import com.licenta.repository.LocatieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocatieService {
    private final LocatieRepository repository;

    public LocatieService(LocatieRepository repository) {
        this.repository = repository;
    }

    public List<Locatie> getAll() {
        return repository.findAll();
    }

    public Locatie save(Locatie locatie) {
        return repository.save(locatie);
    }
    public void stergeLocatiiGoale() {
        repository.deleteLocatiiGoale();
    }
    public Locatie getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Locatie nu a fost găsită cu id: " + id));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
