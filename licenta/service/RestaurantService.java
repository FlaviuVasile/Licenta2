package com.licenta.service;

import com.licenta.model.Restaurant;
import com.licenta.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public List<Restaurant> getAll() {
        return repository.findAll();
    }

    public Optional<Restaurant> getById(Long id) {
        return repository.findById(id);
    }

    public Restaurant save(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    public Optional<Restaurant> update(Long id, Restaurant updated) {
        return repository.findById(id).map(existing -> {
            existing.setNume(updated.getNume());
            existing.setAdresa(updated.getAdresa());
            existing.setTipBucatarie(updated.getTipBucatarie());
            existing.setDescriere(updated.getDescriere());
            existing.setRating(updated.getRating());
            existing.setOras(updated.getOras());
            return repository.save(existing);
        });
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
