package com.licenta.repository;

import com.licenta.model.Locatie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface LocatieRepository extends JpaRepository<Locatie, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Locatie l WHERE l.nume IS NULL AND l.descriere IS NULL AND l.tara IS NULL AND l.rating IS NULL")
    void deleteLocatiiGoale();
}
