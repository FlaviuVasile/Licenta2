package com.licenta.transformer;


import com.licenta.dto.IntrebareDTO;
import com.licenta.model.Locatie;
import com.licenta.model.Oras;
import com.licenta.repository.LocatieRepository;
import com.licenta.repository.OrasRepository;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class LocatieTransformer implements GptTransformer {

    private final LocatieRepository locatieRepository;
    private final OrasRepository orasRepository;

    public LocatieTransformer(LocatieRepository locatieRepository, OrasRepository orasRepository) {
        this.locatieRepository = locatieRepository;
        this.orasRepository = orasRepository;
    }

    @Override
    public double score(IntrebareDTO dto) {
        String intrebare = dto.getIntrebare().toLowerCase();
        if (intrebare.contains("loc") || intrebare.contains("locație") || intrebare.contains("locatie") ||
                intrebare.contains("obiectiv") || intrebare.contains("atracție") || intrebare.contains("atractie")) {
            return 0.9;
        }
        return 0.0;
    }

    @Override
    public String handle(IntrebareDTO dto) {
        StringBuilder raspuns = new StringBuilder(" Locații recomandate:\n\n");

        List<Oras> orase = orasRepository.findAll();
        List<Locatie> locatii = locatieRepository.findAll();

        Oras orasGasit = orase.stream()
                .filter(o -> dto.getIntrebare().toLowerCase().contains(o.getNume().toLowerCase()))
                .findFirst()
                .orElse(null);

        if (orasGasit == null) {
            raspuns.append("Nu am găsit un oraș menționat în întrebare.");
            return raspuns.toString();
        }

        locatii.stream()
                .filter(l -> l.getOras() != null && l.getOras().getId().equals(orasGasit.getId()))
                .filter(l -> dto.getRatingMinim() == null || l.getRating() >= dto.getRatingMinim())
                .forEach(l -> {
                    raspuns.append(" - ").append(l.getNume())
                            .append("  ").append(l.getRating())
                            .append("\n    ").append(l.getDescriere() != null ? l.getDescriere() : "Fără descriere.")
                            .append("\n");
                    try {
                        String cautare = URLEncoder.encode(l.getNume(), StandardCharsets.UTF_8.toString());
                        raspuns.append("     Imagine: https://www.google.com/search?tbm=isch&q=")
                                .append(cautare).append("\n\n");
                    } catch (Exception ignored) {}
                });

        if (raspuns.toString().equals("🗺 Locații recomandate:\n\n")) {
            raspuns.append("Nu am găsit locații pentru orașul ").append(orasGasit.getNume()).append(".");
        }

        return raspuns.toString();
    }
}

