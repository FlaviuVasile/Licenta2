package com.licenta.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Locatie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty
    @NotBlank(message = "Numele locației este obligatoriu")
    private String nume;

    @JsonProperty
    @Size(max = 500, message = "Descrierea poate avea maxim 500 de caractere")
    private String descriere;

    @JsonProperty
    @NotBlank(message = "Țara este obligatorie")
    private String tara;

    @JsonProperty
    @DecimalMin(value = "0.0", inclusive = true, message = "Rating-ul nu poate fi negativ")
    @DecimalMax(value = "5.0", inclusive = true, message = "Rating-ul maxim este 5")
    private Double rating;

    @ManyToOne
    @JoinColumn(name = "oras_id")
    @JsonProperty
    private Oras oras;

    public Locatie() {}

    public Locatie(String nume, String descriere, String tara, Double rating, Oras oras) {
        this.nume = nume;
        this.descriere = descriere;
        this.tara = tara;
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

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getTara() {
        return tara;
    }

    public void setTara(String tara) {
        this.tara = tara;
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
