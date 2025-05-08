package com.licenta.service;

import com.licenta.dto.IntrebareDTO;
import com.licenta.transformer.GptTransformer;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GptService {

    private final List<GptTransformer> transformers;

    public GptService(List<GptTransformer> transformers) {
        this.transformers = transformers;
    }

    public String raspunde(IntrebareDTO dto) {
        return transformers.stream()
                .max((a, b) -> Double.compare(a.score(dto), b.score(dto)))
                .filter(t -> t.score(dto) > 0.1)
                .map(t -> t.handle(dto))
                .orElse("Îmi pare rău, nu am găsit o recomandare pentru întrebarea ta...");
    }
}
