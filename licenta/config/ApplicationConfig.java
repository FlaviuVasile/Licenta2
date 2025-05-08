package com.licenta.config;

import com.licenta.transformer.GptTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ApplicationConfig {

    @Bean
    public List<GptTransformer> gptTransformers(List<GptTransformer> transformers) {
        // Returnăm direct lista de Transformere detectate automat de Spring
        return transformers;
    }
}
