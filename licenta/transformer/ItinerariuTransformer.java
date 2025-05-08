package com.licenta.transformer;

import com.licenta.dto.IntrebareDTO;
import com.licenta.model.Itinerariu;
import com.licenta.model.Oras;
import com.licenta.repository.ItinerariuRepository;
import com.licenta.repository.OrasRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItinerariuTransformer implements GptTransformer {

    private final ItinerariuRepository itinerariuRepository;
    private final OrasRepository orasRepository;

    public ItinerariuTransformer(ItinerariuRepository itinerariuRepository, OrasRepository orasRepository) {
        this.itinerariuRepository = itinerariuRepository;
        this.orasRepository = orasRepository;
    }

    @Override
    public double score(IntrebareDTO dto) {
        String intrebare = dto.getIntrebare().toLowerCase();
        if (intrebare.contains("traseu") || intrebare.contains("drumeție") || intrebare.contains("drumetie") ||
                intrebare.contains("itinerariu") || intrebare.contains("expeditie") || intrebare.contains("expediție")) {
            return 0.95;
        }
        return 0.0;
    }

    @Override
    public String handle(IntrebareDTO dto) {
        StringBuilder raspuns = new StringBuilder(" Trasee de drumeție recomandate:\n\n");

        List<Oras> orase = orasRepository.findAll();
        List<Itinerariu> itinerarii = itinerariuRepository.findAll();

        Oras orasGasit = orase.stream()
                .filter(o -> dto.getIntrebare().toLowerCase().contains(o.getNume().toLowerCase()))
                .findFirst()
                .orElse(null);

        if (orasGasit == null) {
            raspuns.append("Nu am găsit un oraș sau zonă specificată în întrebare.\n");
            return raspuns.toString();
        }

        itinerarii.stream()
                .filter(i -> i.getOras() != null && i.getOras().getId().equals(orasGasit.getId()))
                .forEach(i -> {
                    raspuns.append(" - ").append(i.getNume()).append("\n")
                            .append("    Durata: ").append(i.getDurata()).append("\n")
                            .append("    Dificultate: ").append(i.getDificultate()).append("\n")
                            .append("    Lungime: ").append(i.getLungimea()).append(" km\n")
                            .append("    Înălțime: ").append(i.getInaltime()).append(" m\n")
                            .append("    Echipament necesar: ").append(String.join(", ", i.getEchipament())).append("\n\n");
                });

        if (raspuns.toString().equals(" Trasee de drumeție recomandate:\n\n")) {
            raspuns.append("Nu am găsit trasee pentru orașul ").append(orasGasit.getNume()).append(".");
        }

        return raspuns.toString();
    }

}

