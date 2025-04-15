package com.licenta.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

    @NotBlank(message = "Username-ul este obligatoriu")
    @Size(min = 3, max = 50)
    @JsonProperty
    private String username;

    @NotBlank(message = "Emailul este obligatoriu")
    @Email(message = "Emailul nu este valid")
    @JsonProperty
    private String email;

    @NotBlank(message = "Parola este obligatorie")
    @Size(min = 6)
    @JsonProperty
    private String parola;

    // constructori, getteri, setteri
}
