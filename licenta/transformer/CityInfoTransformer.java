package com.licenta.transformer;

import com.licenta.dto.IntrebareDTO;
import com.licenta.model.Judet;
import com.licenta.model.Locatie;
import com.licenta.model.Oras;
import com.licenta.repository.JudetRepository;
import com.licenta.repository.LocatieRepository;
import com.licenta.repository.OrasRepository;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class CityInfoTransformer implements GptTransformer {

    private final OrasRepository orasRepository;
    private final JudetRepository judetRepository;
    private final LocatieRepository locatieRepository;


    public CityInfoTransformer(OrasRepository orasRepository, JudetRepository judetRepository, LocatieRepository locatieRepository) {
        this.orasRepository = orasRepository;
        this.judetRepository = judetRepository;
        this.locatieRepository = locatieRepository;
    }

    @Override
    public double score(IntrebareDTO dto) {
        String intrebare = dto.getIntrebare().toLowerCase();

        // Dacă întrebarea conține aceste cuvinte atunci ea va recunoaste ce vrea utilizatorul
        if (intrebare.contains("hotel") || intrebare.contains("restaurant") ||
                intrebare.contains("cazare") || intrebare.contains("mâncare") ||
                intrebare.contains("locație") || intrebare.contains("traseu") ||
                intrebare.contains("mancare") || intrebare.contains("atracție")) {
            return 0.0;
        }

        // Căutăm dacă întrebarea conține un nume de oraș sau județ
        boolean orasGasit = orasRepository.findAll().stream()
                .anyMatch(o -> intrebare.contains(o.getNume().toLowerCase()));
        boolean judetGasit = judetRepository.findAll().stream()
                .anyMatch(j -> intrebare.contains(j.getNume().toLowerCase()));

        if (orasGasit || judetGasit) {
            return 0.85;
        }
        return 0.0;
    }

    @Override
    public String handle(IntrebareDTO dto) {
        StringBuilder raspuns = new StringBuilder();
        String intrebare = dto.getIntrebare().toLowerCase();

        // 1. Căutăm locații turistice
        Locatie locatieGasita = locatieRepository.findAll().stream()
                .filter(l -> dto.getIntrebare().toLowerCase().contains(l.getNume().toLowerCase()))
                .findFirst()
                .orElse(null);

        if (locatieGasita != null) {
            raspuns.append(" Locație Turistică: ").append(locatieGasita.getNume()).append("\n");
            String descriere = locatieGasita.getDescriere();
            if (descriere == null || descriere.length() < 100) {
                try {
                    String query = URLEncoder.encode(locatieGasita.getNume(), StandardCharsets.UTF_8.toString());
                    raspuns.append("\n\n Pentru o descriere completă, poți consulta: https://en.wikipedia.org/wiki/").append(query);
                } catch (Exception ignored) {}
            } else {
                raspuns.append("Descriere: ").append(descriere).append("\n");
            }
            if (locatieGasita.getRating() != null) {
                raspuns.append(" Rating: ").append(locatieGasita.getRating()).append("\n");
            }
            try {
                String query = URLEncoder.encode(locatieGasita.getNume(), StandardCharsets.UTF_8.toString());
                raspuns.append(" Mai multe imagini: https://www.google.com/search?tbm=isch&q=").append(query);
            } catch (Exception ignored) {}
            return raspuns.toString();
        }

        // Caută întâi oraș
        Oras orasGasit = orasRepository.findAll().stream()
                .filter(o -> intrebare.contains(o.getNume().toLowerCase()))
                .findFirst()
                .orElse(null);

        if (orasGasit != null) {
            raspuns.append("Oraș: ").append(orasGasit.getNume()).append("\n");
            if (orasGasit.getDescriere() != null) {
                raspuns.append(" Descriere: ").append(orasGasit.getDescriere()).append("\n");
            }
            if (orasGasit.getLatitudine() != null && orasGasit.getLongitudine() != null) {
                raspuns.append("Coordonate: ").append(orasGasit.getLatitudine())
                        .append(", ").append(orasGasit.getLongitudine()).append("\n");
            }
            try {
                String query = URLEncoder.encode(orasGasit.getNume() + "", StandardCharsets.UTF_8.toString());
                raspuns.append(" Mai multe detalii: ").append("https://en.wikipedia.org/wiki/Special:Search/").append(query);
            } catch (Exception ignored) {}
            return raspuns.toString();
        }

        // Dacă nu găsim oraș, căutăm județ
        Judet judetGasit = judetRepository.findAll().stream()
                .filter(j -> intrebare.contains(j.getNume().toLowerCase()))
                .findFirst()
                .orElse(null);

        if (judetGasit != null) {
            raspuns.append(" Județ: ").append(judetGasit.getNume()).append("\n");
            if (judetGasit.getDescriere() != null) {
                raspuns.append(" Descriere: ").append(judetGasit.getDescriere()).append("\n");
            }
            if (judetGasit.getLatitudine() != null && judetGasit.getLongitudine() != null) {
                raspuns.append(" Coordonate: ").append(judetGasit.getLatitudine())
                        .append(", ").append(judetGasit.getLongitudine()).append("\n");
            }
            try {
                String query = URLEncoder.encode(judetGasit.getNume() + " ", StandardCharsets.UTF_8.toString());
                raspuns.append(" Mai multe detalii: ").append("https://en.wikipedia.org/wiki/Special:Search/").append(query);
            } catch (Exception ignored) {}
            return raspuns.toString();
        }

        return "Nu am găsit informații despre acest oraș sau județ.";
    }
}
