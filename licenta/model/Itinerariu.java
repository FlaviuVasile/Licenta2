package com.licenta.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class Itinerariu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Numele itinerariului este obligatoriu")
    @JsonProperty
    private String nume;

    @JsonProperty
    private double lungimea; // în km

    @JsonProperty
    @Min(value = 0, message = "Inaltimea trebuie sa fie pozitiva")
    private int inaltime; // în metri

    @JsonProperty
    @NotBlank(message = "Durata este obligatorie")
    private String durata; // ex: "3h 20min"

    @NotBlank(message = "Dificultatea este obligatorie")
    @JsonProperty
    private String dificultate; // ex: "uşor", "mediu", "greu"

    @ElementCollection
    private List<String> echipament;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User utilizator;

    @ManyToOne
    @JoinColumn(name = "oras_id")
    @JsonProperty
    private Oras oras;

    public Itinerariu() {}

    public Itinerariu(String nume, double lungimea, int inaltime, String durata,
                      String dificultate, List<String> echipament, User utilizator, Oras oras) {
        this.nume = nume;
        this.lungimea = lungimea;
        this.inaltime = inaltime;
        this.durata = durata;
        this.dificultate = dificultate;
        this.echipament = echipament;
        this.utilizator = utilizator;
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

    public double getLungimea() {
        return lungimea;
    }

    public void setLungimea(double lungimea) {
        this.lungimea = lungimea;
    }

    public int getInaltime() {
        return inaltime;
    }

    public void setInaltime(int inaltime) {
        this.inaltime = inaltime;
    }

    public String getDurata() {
        return durata;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }

    public String getDificultate() {
        return dificultate;
    }

    public void setDificultate(String dificultate) {
        this.dificultate = dificultate;
    }

    public List<String> getEchipament() {
        return echipament;
    }

    public void setEchipament(List<String> echipament) {
        this.echipament = echipament;
    }

    public User getUtilizator() {
        return utilizator;
    }

    public void setUtilizator(User utilizator) {
        this.utilizator = utilizator;
    }

    public Oras getOras() {
        return oras;
    }

    public void setOras(Oras oras) {
        this.oras = oras;
    }
}
