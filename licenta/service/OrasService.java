package com.licenta.service;

import com.licenta.model.Oras;
import com.licenta.repository.OrasRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrasService {

    private final OrasRepository repository;

    public OrasService(OrasRepository repository) {
        this.repository = repository;
    }

    public List<Oras> getAll() {
        return repository.findAll();
    }

    public Optional<Oras> getById(Long id) {
        return repository.findById(id);
    }

    public Oras save(Oras oras) {
        return repository.save(oras);
    }

    public Optional<Oras> update(Long id, Oras orasNou) {
        return repository.findById(id).map(existing -> {
            existing.setNume(orasNou.getNume());
            existing.setDescriere(orasNou.getDescriere());
            existing.setLatitudine(orasNou.getLatitudine());
            existing.setLongitudine(orasNou.getLongitudine());
            existing.setJudet(orasNou.getJudet());
            return repository.save(existing);
        });
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
