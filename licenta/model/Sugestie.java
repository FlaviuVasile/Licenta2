package com.licenta.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class Sugestie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty
    @NotBlank(message = "Textul sugestiei este obligatoriu")
    private String text;
    @JsonProperty
    @NotNull(message = "Data generarii este obligatorie")
    private LocalDateTime dataGenerare;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User utilizator;

    public Sugestie() {}

    public Sugestie(String text, LocalDateTime dataGenerare, User utilizator) {
        this.text = text;
        this.dataGenerare = dataGenerare;
        this.utilizator = utilizator;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDataGenerare() {
        return dataGenerare;
    }

    public void setDataGenerare(LocalDateTime dataGenerare) {
        this.dataGenerare = dataGenerare;
    }

    public User getUtilizator() {
        return utilizator;
    }

    public void setUtilizator(User utilizator) {
        this.utilizator = utilizator;
    }
}
