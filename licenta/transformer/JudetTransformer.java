package com.licenta.transformer;


import com.licenta.dto.IntrebareDTO;
import com.licenta.model.Judet;
import com.licenta.repository.JudetRepository;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class JudetTransformer implements GptTransformer {

    private final JudetRepository judetRepository;

    public JudetTransformer(JudetRepository judetRepository) {
        this.judetRepository = judetRepository;
    }

    @Override
    public double score(IntrebareDTO dto) {
        String intrebare = dto.getIntrebare().toLowerCase();
        if ((intrebare.contains("județ") || intrebare.contains("judet")) &&
                (intrebare.contains("unde") || intrebare.contains("coordonate") || intrebare.contains("localizare"))) {
            return 0.95;
        }
        return 0.0;
    }

    @Override
    public String handle(IntrebareDTO dto) {
        StringBuilder raspuns = new StringBuilder("🗺 Informații despre județ:\n\n");

        List<Judet> judete = judetRepository.findAll();

        Judet judetGasit = judete.stream()
                .filter(j -> dto.getIntrebare().toLowerCase().contains(j.getNume().toLowerCase()))
                .findFirst()
                .orElse(null);

        if (judetGasit == null) {
            raspuns.append("Nu am găsit un județ menționat în întrebare.");
            return raspuns.toString();
        }

        raspuns.append("Județ: ").append(judetGasit.getNume()).append("\n");

        if (judetGasit.getLatitudine() != null && judetGasit.getLongitudine() != null) {
            raspuns.append("Coordonate: ").append(judetGasit.getLatitudine())
                    .append(", ").append(judetGasit.getLongitudine()).append("\n");
            try {
                String query = URLEncoder.encode(judetGasit.getNume() + " Romania", StandardCharsets.UTF_8.toString());
                String mapLink = "https://www.google.com/maps/search/?api=1&query=" + query;
                raspuns.append(" Vezi pe hartă: ").append(mapLink);
            } catch (Exception ignored) {}
        } else {
            raspuns.append("Coordonatele nu sunt disponibile pentru acest județ.");
        }

        return raspuns.toString();
    }
}

