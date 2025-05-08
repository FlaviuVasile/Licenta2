package com.licenta.transformer;

import com.licenta.dto.IntrebareDTO;
import com.licenta.model.*;
import com.licenta.repository.*;
import com.licenta.service.OpenAiService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurismGeneralTransformer implements GptTransformer {

    private final ItinerariuRepository itinerariuRepository;
    private final HotelRepository hotelRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrasRepository orasRepository;
    private final JudetRepository judetRepository;
    private final LocatieRepository locatieRepository;
    private final OpenAiService openAiService;

    public TurismGeneralTransformer(ItinerariuRepository itinerariuRepository,
                                    HotelRepository hotelRepository,
                                    RestaurantRepository restaurantRepository,
                                    OrasRepository orasRepository,
                                    JudetRepository judetRepository,
                                    LocatieRepository locatieRepository,
                                    OpenAiService openAiService) {
        this.itinerariuRepository = itinerariuRepository;
        this.hotelRepository = hotelRepository;
        this.restaurantRepository = restaurantRepository;
        this.orasRepository = orasRepository;
        this.judetRepository = judetRepository;
        this.locatieRepository = locatieRepository;
        this.openAiService = openAiService;
    }

    @Override
    public double score(IntrebareDTO dto) {
        // Dăm un scor mare ca să fie selectat ușor când vrei date turistice generale
        String intrebare = dto.getIntrebare().toLowerCase();
        if (intrebare.contains("turism") || intrebare.contains("informații generale") ||
                intrebare.contains("atracții") || intrebare.contains("ce pot vizita") ||
                intrebare.contains("locuri de vizitat") || intrebare.contains("vizitare")) {
            return 0.95;
        }
        return 0.0;
    }

    @Override
    public String handle(IntrebareDTO dto) {
        List<String> trasee = itinerariuRepository.findAll().stream().map(Itinerariu::getNume).toList();
        List<String> hoteluri = hotelRepository.findAll().stream().map(Hotel::getNume).toList();
        List<String> restaurante = restaurantRepository.findAll().stream().map(Restaurant::getNume).toList();
        List<String> orase = orasRepository.findAll().stream().map(Oras::getNume).toList();
        List<String> judete = judetRepository.findAll().stream().map(Judet::getNume).toList();
        List<String> locatii = locatieRepository.findAll().stream().map(Locatie::getNume).toList();

        String prompt = construiestePrompt(trasee, hoteluri, restaurante, orase, judete, locatii);

        return openAiService.getNaturalLanguageResponse(prompt);
    }

    private String construiestePrompt(List<String> trasee, List<String> hoteluri, List<String> restaurante,
                                      List<String> orase, List<String> judete, List<String> locatii) {

        StringBuilder sb = new StringBuilder("Salut! Am găsit următoarele informații turistice:\n\n");

        if (!trasee.isEmpty()) {
            sb.append("Trasee turistice: ").append(String.join(", ", trasee)).append(".\n");
        }
        if (!hoteluri.isEmpty()) {
            sb.append(" Hoteluri disponibile: ").append(String.join(", ", hoteluri)).append(".\n");
        }
        if (!restaurante.isEmpty()) {
            sb.append(" Restaurante: ").append(String.join(", ", restaurante)).append(".\n");
        }
        if (!locatii.isEmpty()) {
            sb.append(" Locații turistice: ").append(String.join(", ", locatii)).append(".\n");
        }
        if (!orase.isEmpty()) {
            sb.append(" Orașe: ").append(String.join(", ", orase)).append(".\n");
        }
        if (!judete.isEmpty()) {
            sb.append(" Județe: ").append(String.join(", ", judete)).append(".\n");
        }

        sb.append("\nTe rog să creezi un mesaj prietenos pentru un utilizator care vrea să descopere România. 😄");

        return sb.toString();
    }
}
