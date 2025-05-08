package com.licenta.transformer;



import com.licenta.dto.IntrebareDTO;

public interface GptTransformer {
    /**
     * Calculează un scor de potrivire între întrebarea primită și domeniul tratat de transformer.
     * @param intrebareDTO obiectul care conține textul întrebării
     * @return scor între 0.0 și 1.0
     */
    double score(IntrebareDTO intrebareDTO);

    /**
     * Generează răspunsul specific bazat pe întrebarea primită.
     * @param intrebareDTO obiectul cu datele întrebării
     * @return răspuns text
     */
    String handle(IntrebareDTO intrebareDTO);
}

