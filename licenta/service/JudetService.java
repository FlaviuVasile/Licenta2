package com.licenta.service;

import com.licenta.model.Judet;
import com.licenta.repository.JudetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JudetService {

    private final JudetRepository repository;

    public JudetService(JudetRepository repository) {
        this.repository = repository;
    }

    public List<Judet> getAll() {
        return repository.findAll();
    }

    public Optional<Judet> getById(Long id) {
        return repository.findById(id);
    }

    public Judet save(Judet judet) {
        return repository.save(judet);
    }

    public Optional<Judet> update(Long id, Judet judetNou) {
        return repository.findById(id).map(existing -> {
            existing.setNume(judetNou.getNume());
            existing.setDescriere(judetNou.getDescriere());
            existing.setLatitudine(judetNou.getLatitudine());
            existing.setLongitudine(judetNou.getLongitudine());
            return repository.save(existing);
        });
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
