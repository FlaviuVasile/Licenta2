package com.licenta.transformer;



import com.licenta.dto.IntrebareDTO;
import com.licenta.model.Oras;
import com.licenta.model.Restaurant;
import com.licenta.repository.OrasRepository;
import com.licenta.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantTransformer implements GptTransformer {

    private final RestaurantRepository restaurantRepository;
    private final OrasRepository orasRepository;

    public RestaurantTransformer(RestaurantRepository restaurantRepository, OrasRepository orasRepository) {
        this.restaurantRepository = restaurantRepository;
        this.orasRepository = orasRepository;
    }

    @Override
    public double score(IntrebareDTO dto) {
        String intrebare = dto.getIntrebare().toLowerCase();
        if (intrebare.contains("restaurant") || intrebare.contains("mâncare") || intrebare.contains("mananc") || intrebare.contains("foame")) {
            return 0.9;
        }
        return 0.0;
    }

    @Override
    public String handle(IntrebareDTO dto) {
        StringBuilder raspuns = new StringBuilder("🍽 Restaurante recomandate:\n\n");

        List<Oras> orase = orasRepository.findAll();
        List<Restaurant> restaurante = restaurantRepository.findAll();

        Oras orasGasit = orase.stream()
                .filter(o -> dto.getIntrebare().toLowerCase().contains(o.getNume().toLowerCase()))
                .findFirst()
                .orElse(null);

        if (orasGasit == null) {
            raspuns.append("Nu am găsit un oraș specificat în întrebare.\n");
            return raspuns.toString();
        }

        restaurante.stream()
                .filter(r -> r.getOras() != null && r.getOras().getId().equals(orasGasit.getId()))
                .filter(r -> dto.getRatingMinim() == null || r.getRating() >= dto.getRatingMinim())
                .forEach(r -> {
                    raspuns.append(" - ").append(r.getNume())
                            .append("  ").append(r.getRating())
                            .append("\n    Adresa: ").append(r.getAdresa())
                            .append("\n    Bucătărie: ").append(r.getTipBucatarie())
                            .append("\n    Descriere: ").append(r.getDescriere())
                            .append("\n\n");
                });

        if (raspuns.toString().equals(" Restaurante recomandate:\n\n")) {
            raspuns.append("Nu am găsit restaurante în orașul ").append(orasGasit.getNume()).append(".");
        }

        return raspuns.toString();
    }
}

