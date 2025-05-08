package com.licenta.transformer;

import com.licenta.dto.IntrebareDTO;
import com.licenta.model.Sugestie;
import com.licenta.repository.SugestieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class SugestieTransformer implements GptTransformer {

    private final SugestieRepository sugestieRepository;
    private final Random random = new Random();

    public SugestieTransformer(SugestieRepository sugestieRepository) {
        this.sugestieRepository = sugestieRepository;
    }

    @Override
    public double score(IntrebareDTO dto) {
        String intrebare = dto.getIntrebare().toLowerCase();
        if (intrebare.contains("sugerează") || intrebare.contains("propune") ||
                intrebare.contains("recomandă") || intrebare.contains("idee de loc") ||
                intrebare.contains("o idee de vacanță")) {
            return 0.9;
        }
        return 0.0;
    }

    @Override
    public String handle(IntrebareDTO dto) {
        List<Sugestie> sugestii = sugestieRepository.findAll();

        if (sugestii.isEmpty()) {
            return "Momentan nu am sugestii disponibile.";
        }

        Sugestie sugestieRandom = sugestii.get(random.nextInt(sugestii.size()));

        return " Sugestie de loc: \n" +
                sugestieRandom.getText() + "\n" +
                "(Generat la data: " + sugestieRandom.getDataGenerare() + ")";
    }
}

