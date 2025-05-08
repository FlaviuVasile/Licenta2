package com.licenta.service;

import com.licenta.model.Itinerariu;
import com.licenta.repository.ItinerariuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItinerariuService {

    private final ItinerariuRepository repository;

    public ItinerariuService(ItinerariuRepository repository) {
        this.repository = repository;
    }

    public List<Itinerariu> getAll() {
        return repository.findAll();
    }

    public Optional<Itinerariu> getById(Long id) {
        return repository.findById(id);
    }

    public Itinerariu save(Itinerariu itinerariu) {
        return repository.save(itinerariu);
    }



    public Optional<Itinerariu> update(Long id, Itinerariu updated) {
        return repository.findById(id).map(existing -> {
            existing.setNume(updated.getNume());
            existing.setLungimea(updated.getLungimea());
            existing.setInaltime(updated.getInaltime());
            existing.setDurata(updated.getDurata());
            existing.setDificultate(updated.getDificultate());
            existing.setEchipament(updated.getEchipament());
            existing.setUtilizator(updated.getUtilizator());
            return repository.save(existing);
        });
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
