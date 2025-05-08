package com.licenta.transformer;


import com.licenta.dto.IntrebareDTO;
import com.licenta.model.Hotel;
import com.licenta.model.Oras;
import com.licenta.repository.HotelRepository;
import com.licenta.repository.OrasRepository;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class HotelTransformer implements GptTransformer {

    private final HotelRepository hotelRepository;
    private final OrasRepository orasRepository;

    public HotelTransformer(HotelRepository hotelRepository, OrasRepository orasRepository) {
        this.hotelRepository = hotelRepository;
        this.orasRepository = orasRepository;
    }

    @Override
    public double score(IntrebareDTO dto) {
        String intrebare = dto.getIntrebare().toLowerCase();
        if (intrebare.contains("hotel") || intrebare.contains("cazare")) {
            return 0.95;
        }
        return 0.0;
    }

    @Override
    public String handle(IntrebareDTO dto) {
        StringBuilder raspuns = new StringBuilder(" Recomandări de hoteluri:\n\n");

        List<Oras> orase = orasRepository.findAll();
        List<Hotel> hoteluri = hotelRepository.findAll();

        Oras orasGasit = orase.stream()
                .filter(o -> dto.getIntrebare().toLowerCase().contains(o.getNume().toLowerCase()))
                .findFirst()
                .orElse(null);

        if (orasGasit == null) {
            raspuns.append("Nu am găsit un oraș specificat în întrebare.\n");
            return raspuns.toString();
        }

        hoteluri.stream()
                .filter(h -> h.getOras() != null && h.getOras().getId().equals(orasGasit.getId()))
                .filter(h -> dto.getRatingMinim() == null || h.getRating() >= dto.getRatingMinim())
                .forEach(h -> {
                    raspuns.append(" - ").append(h.getNume())
                            .append(" ").append(h.getRating())
                            .append("\n    ").append(h.getAdresa())
                            .append("\n    ").append(h.getDescriere())
                            .append("\n");
                });

        if (raspuns.toString().equals(" Recomandări de hoteluri:\n\n")) {
            raspuns.append("Nu am găsit hoteluri pentru orașul ").append(orasGasit.getNume()).append(".");
        } else {
            try {
                String cautare = URLEncoder.encode(orasGasit.getNume() + " hoteluri", StandardCharsets.UTF_8.toString());
                String bookingLink = "https://www.booking.com/searchresults.ro.html?ss=" + cautare;
                raspuns.append("\n Link rapid: ").append(bookingLink);
            } catch (Exception ignored) {}
        }

        return raspuns.toString();
    }
}

