package com.licenta.service;

import com.licenta.dto.IntrebareDTO;
import com.licenta.model.*;
import com.licenta.repository.*;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class GptService {

    private final OrasRepository orasRepository;
    private final HotelRepository hotelRepository;
    private final RestaurantRepository restaurantRepository;
    private final LocatieRepository locatieRepository;
    private final JudetRepository judetRepository;
    private final ItinerariuRepository itinerariuRepository;

    public GptService(OrasRepository orasRepository,
                      HotelRepository hotelRepository,
                      RestaurantRepository restaurantRepository,
                      LocatieRepository locatieRepository,
                      JudetRepository judetRepository,
                      ItinerariuRepository itinerariuRepository) {
        this.orasRepository = orasRepository;
        this.hotelRepository = hotelRepository;
        this.restaurantRepository = restaurantRepository;
        this.locatieRepository = locatieRepository;
        this.judetRepository = judetRepository;
        this.itinerariuRepository = itinerariuRepository;
    }

    public String raspunde(IntrebareDTO dto) {
        String intrebare = dto.getIntrebare().toLowerCase();
        String tipLocatie = dto.getTipLocatie();
        // Dacă userul nu a specificat tipul, îl deducem din întrebare
        if (tipLocatie == null) {
            if (intrebare.contains("traseu") || intrebare.contains("trasee")
                    || intrebare.contains("drumeție") || intrebare.contains("drumetie")
                    || intrebare.contains("itinerariu") || intrebare.contains("itinerarii")
                    || intrebare.contains("expeditie") || intrebare.contains("expediție"))
            {
                tipLocatie = "itinerariu";
            }else if (intrebare.contains("cazare") || intrebare.contains("hotel") || intrebare.contains("hoteluri")) {
                tipLocatie = "hotel";
            }else if (intrebare.contains("restaurant") ||
                    intrebare.contains("mănânc") ||
                    intrebare.contains("mananc") ||
                    intrebare.contains("mancare") ||
                    intrebare.contains("mi-e foame") ||
                    intrebare.contains("foame") ||
                    intrebare.contains("unde pot mânca") ||
                    intrebare.contains("unde pot manca")||
                    intrebare.contains("unde as putea manca")||
                    intrebare.contains("unde aș putea mânca"))
            {
                tipLocatie = "restaurant";
            }

        }

        Integer ratingMin = dto.getRatingMinim();

        List<Oras> orase = orasRepository.findAll();
        List<Judet> judete = judetRepository.findAll();
        if (intrebare.contains("poziție") || intrebare.contains("pozitie")
                || intrebare.contains("locație") || intrebare.contains("locatie")
                || intrebare.contains("unde se află") || intrebare.contains("unde este")
                || intrebare.contains("coordonate")) {

            Oras orasGasit = orase.stream()
                    .filter(o -> intrebare.contains(o.getNume().toLowerCase()))
                    .findFirst()
                    .orElse(null);

            if (orasGasit != null && orasGasit.getLatitudine() != null && orasGasit.getLongitudine() != null) {
                String link = "https://www.google.com/maps/search/?api=1&query=" + orasGasit.getLatitudine() + "," + orasGasit.getLongitudine();
                return " Orașul " + orasGasit.getNume() + " se află la coordonatele:\n" +
                        " " + orasGasit.getLatitudine() + ", " + orasGasit.getLongitudine() + "\n" +
                        "🗺 Vezi pe hartă: " + link;
            }

            Judet judetGasit = judete.stream()
                    .filter(j -> intrebare.contains(j.getNume().toLowerCase()))
                    .findFirst()
                    .orElse(null);

            if (judetGasit != null && judetGasit.getLatitudine() != null && judetGasit.getLongitudine() != null) {
                String link = "https://www.google.com/maps/search/?api=1&query=" + judetGasit.getLatitudine() + "," + judetGasit.getLongitudine();
                return " Județul " + judetGasit.getNume() + " se află aproximativ la:\n" +
                        " " + judetGasit.getLatitudine() + ", " + judetGasit.getLongitudine() + "\n" +
                        "🗺 Vezi pe hartă: " + link;
            }

            return "Am înțeles că vrei poziția unui oraș sau județ, dar nu am găsit coordonatele.";
        }

        List<Oras> oraseGasite = new ArrayList<>();

        // Detectare oraș sau județ
        Oras orasGasit = orase.stream()
                .filter(o -> intrebare.contains(o.getNume().toLowerCase()))
                .findFirst()
                .orElse(null);

        if (orasGasit != null) {
            oraseGasite.add(orasGasit);
        } else {
            Judet judetGasit = judete.stream()
                    .filter(j -> intrebare.contains(j.getNume().toLowerCase()))
                    .findFirst()
                    .orElse(null);

            if (judetGasit != null) {
                oraseGasite = judetGasit.getOrase();
            } else {
                return "Nu am găsit niciun oraș sau județ menționat în întrebare.";
            }
        }

        StringBuilder raspuns = new StringBuilder(" Recomandări pentru zona selectată:\n");

        // Locații
        if (tipLocatie == null || tipLocatie.equalsIgnoreCase("locatie")) {
            List<Oras> finalOraseGasite = oraseGasite;
            List<Locatie> locatii = locatieRepository.findAll().stream()
                    .filter(l ->
                            finalOraseGasite.stream()
                                    .anyMatch(o -> l.getOras() != null && l.getOras().getId().equals(o.getId())))
                    .toList();

            if (!locatii.isEmpty()) {
                raspuns.append("\n📌 Locații:\n");
                locatii.forEach(l -> {
                    String imagineUrl = "https://www.google.com/search?tbm=isch&q=" + l.getNume().replace(" ", "+");
                    raspuns.append(" - ").append(l.getNume())
                            .append(" (").append(l.getOras().getNume()).append(")")
                            .append("  ").append(l.getRating())
                            .append("\n    Imagine: ").append(imagineUrl).append("\n");
                });
            }
        }


        // Restaurante
        if (tipLocatie == null || tipLocatie.equalsIgnoreCase("restaurant")) {
            raspuns.append("\n Restaurante:\n");
            for (Oras o : oraseGasite) {
                List<Restaurant> restaurante = restaurantRepository.findAll().stream()
                        .filter(r -> r.getOras().getId().equals(o.getId()))
                        .filter(r -> ratingMin == null || r.getRating() >= ratingMin)
                        .toList();

                restaurante.forEach(r -> {
                    raspuns.append(" - ").append(r.getNume())
                            .append(" (").append(o.getNume()).append("  ").append(r.getRating()).append(")\n");
                    raspuns.append("    Adresa: ").append(r.getAdresa()).append("\n");
                    raspuns.append("    Descriere: ").append(r.getDescriere()).append("\n");
                    raspuns.append("    Bucătărie: ").append(r.getTipBucatarie()).append("\n\n");});
            }
        }

        // Hoteluri
        if (tipLocatie.equalsIgnoreCase("hotel")) {
            raspuns.append("\n Hoteluri:\n");
            for (Oras o : oraseGasite) {
                List<Hotel> hoteluri = hotelRepository.findAll().stream()
                        .filter(h -> h.getOras().getId().equals(o.getId()))
                        .filter(h -> ratingMin == null || h.getRating() >= ratingMin)
                        .toList();

                hoteluri.forEach(h -> {
                    raspuns.append(" - ").append(h.getNume())
                            .append(" (").append(o.getNume()).append("  ").append(h.getRating()).append(")\n");
                    raspuns.append("    Adresa: ").append(h.getAdresa()).append("\n");
                    raspuns.append("    Descriere: ").append(h.getDescriere()).append("\n\n");
                });

            }

            // 🔗 Link către Booking pentru fiecare oraș
            raspuns.append("\n Linkuri utile pentru cazare:\n");
            for (Oras o : oraseGasite) {
                String orasEncoded;
                String link;
                try {
                    String cautare = o.getNume() + ", Romania";
                    orasEncoded = URLEncoder.encode(cautare, "UTF-8");
                    link = "https://www.booking.com/searchresults.ro.html?ss=" + orasEncoded;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    // fallback în caz de eroare
                    link = "https://www.booking.com";
                }

                raspuns.append(" ").append(o.getNume()).append(": ").append(link).append("\n");
            }

            return raspuns.toString();
        }



        // Itinerarii
        // Doar itinerarii
        if (tipLocatie.equalsIgnoreCase("itinerariu")) {
            raspuns.append("\n Itinerarii:\n");
            for (Oras o : oraseGasite) {
                List<Itinerariu> itinerarii = itinerariuRepository.findAll().stream()
                        .filter(i -> i.getOras() != null && i.getOras().getId().equals(o.getId()))
                        .toList();

                itinerarii.forEach(i -> {
                    raspuns.append(" - ").append(i.getNume()).append(" (").append(o.getNume()).append(")\n");
                    raspuns.append("   • Dificultate: ").append(i.getDificultate()).append("\n");
                    raspuns.append("   • Durată: ").append(i.getDurata()).append("\n");
                    raspuns.append("   • Lungime: ").append(i.getLungimea()).append(" km\n");
                    raspuns.append("   • Altitudine: ").append(i.getInaltime()).append(" m\n");
                    raspuns.append("   • Echipament: ").append(String.join(", ", i.getEchipament())).append("\n\n");
                });
            }

            return raspuns.toString(); // 💥 Important: return direct => nu continuă cu alte secțiuni
        }

        if (intrebare.contains("cazare")) {
            raspuns.append("\n Recomandare suplimentară:\n");
            oraseGasite.forEach(o -> {
                String bookingUrl = "https://www.booking.com/searchresults.ro.html?ss=" + o.getNume().replace(" ", "+");
                raspuns.append(" ").append(o.getNume()).append(": ").append(bookingUrl).append("\n");
            });
        }
        return raspuns.toString();
    }
}
