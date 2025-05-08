package com.licenta.transformer;



import com.licenta.dto.IntrebareDTO;
import com.licenta.model.Oras;
import com.licenta.repository.OrasRepository;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class OrasTransformer implements GptTransformer {

    private final OrasRepository orasRepository;

    public OrasTransformer(OrasRepository orasRepository) {
        this.orasRepository = orasRepository;
    }

    @Override
    public double score(IntrebareDTO dto) {
        String intrebare = dto.getIntrebare().toLowerCase();
        if (intrebare.contains("unde este") || intrebare.contains("coordonate") ||
                intrebare.contains("localizare") || intrebare.contains("unde se află") ||
                intrebare.contains("pozitie") || intrebare.contains("poziție")) {
            return 0.95;
        }
        return 0.0;
    }

    @Override
    public String handle(IntrebareDTO dto) {
        StringBuilder raspuns = new StringBuilder(" Informații despre locație:\n\n");

        List<Oras> orase = orasRepository.findAll();

        Oras orasGasit = orase.stream()
                .filter(o -> dto.getIntrebare().toLowerCase().contains(o.getNume().toLowerCase()))
                .findFirst()
                .orElse(null);

        if (orasGasit == null) {
            raspuns.append("Nu am găsit un oraș menționat în întrebare.");
            return raspuns.toString();
        }

        raspuns.append("Oraș: ").append(orasGasit.getNume()).append("\n");

        if (orasGasit.getLatitudine() != null && orasGasit.getLongitudine() != null) {
            raspuns.append("Coordonate: ").append(orasGasit.getLatitudine())
                    .append(", ").append(orasGasit.getLongitudine()).append("\n");
            try {
                String query = URLEncoder.encode(orasGasit.getNume(), StandardCharsets.UTF_8.toString());
                String mapLink = "https://www.google.com/maps/search/?api=1&query=" + query;
                raspuns.append(" Vezi pe hartă: ").append(mapLink);
            } catch (Exception ignored) {}
        } else {
            raspuns.append("Coordonatele nu sunt disponibile pentru acest oraș.");
        }

        return raspuns.toString();
    }
}

