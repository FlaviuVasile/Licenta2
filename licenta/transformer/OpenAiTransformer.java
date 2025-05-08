package com.licenta.transformer;

import com.licenta.dto.IntrebareDTO;
import com.licenta.service.OpenAiService;
import org.springframework.stereotype.Service;

@Service
public class OpenAiTransformer implements GptTransformer {

    private final OpenAiService openAiService;

    public OpenAiTransformer(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    @Override
    public double score(IntrebareDTO dto) {
        // scor foarte mic, doar fallback când nimeni altcineva nu se potrivește
        return 0.1;
    }

    @Override
    public String handle(IntrebareDTO dto) {
        return openAiService.getChatCompletion(dto.getIntrebare());
    }
}
