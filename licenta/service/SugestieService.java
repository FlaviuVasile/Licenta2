package com.licenta.service;

import com.licenta.model.Sugestie;
import com.licenta.repository.SugestieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SugestieService {

    private final SugestieRepository repository;

    public SugestieService(SugestieRepository repository) {
        this.repository = repository;
    }

    public List<Sugestie> getAll() {
        return repository.findAll();
    }

    public Optional<Sugestie> getById(Long id) {
        return repository.findById(id);
    }

    public Sugestie save(Sugestie sugestie) {
        return repository.save(sugestie);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
