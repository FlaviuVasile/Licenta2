package com.licenta.service;

import com.licenta.model.Hotel;
import com.licenta.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService {

    private final HotelRepository repository;

    public HotelService(HotelRepository repository) {
        this.repository = repository;
    }

    public List<Hotel> getAll() {
        return repository.findAll();
    }

    public Optional<Hotel> getById(Long id) {
        return repository.findById(id);
    }

    public Hotel save(Hotel hotel) {
        return repository.save(hotel);
    }

    public Optional<Hotel> update(Long id, Hotel updatedHotel) {
        return repository.findById(id).map(existing -> {
            existing.setNume(updatedHotel.getNume());
            existing.setAdresa(updatedHotel.getAdresa());
            existing.setDescriere(updatedHotel.getDescriere());
            existing.setRating(updatedHotel.getRating());
            existing.setOras(updatedHotel.getOras());
            return repository.save(existing);
        });
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
