package com.licenta.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty
    @NotBlank(message = "Numele hotelului este obligatoriu")
    private String nume;
    @JsonProperty
    @NotBlank(message = "Adresa este obligatorie")
    private String adresa;

    @Size(max = 500, message = "Descrierea poate avea maxim 500 de caractere")
    @JsonProperty
    private String descriere;
    @DecimalMin(value = "0.0", inclusive = true, message = "Rating-ul nu poate fi negativ")
    @DecimalMax(value = "5.0", inclusive = true, message = "Rating-ul maxim este 5")
    @JsonProperty
    private Double rating;

    @ManyToOne
    @JoinColumn(name = "oras_id")
    @JsonProperty
    private Oras oras;


    public Hotel() {}

    public Hotel(String nume, String adresa, String descriere, Double rating, Oras oras) {
        this.nume = nume;
        this.adresa = adresa;
        this.descriere = descriere;
        this.rating = rating;
        this.oras = oras;
    }

    // Getteri și setteri
    public Long getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Oras getOras() {
        return oras;
    }

    public void setOras(Oras oras) {
        this.oras = oras;
    }
}
