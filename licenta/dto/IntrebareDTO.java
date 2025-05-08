package com.licenta.dto;
import  io.swagger.v3.oas.annotations.media.Schema;
public class IntrebareDTO {
    private String intrebare;
    @Schema(hidden = true)
    private String tipLocatie;
    @Schema(hidden = true)
    private Integer ratingMinim;
    @Schema(hidden = true)
    private String durata;


    public String getIntrebare() {
        return intrebare;
    }

    public void setIntrebare(String intrebare) {
        this.intrebare = intrebare;
    }

    public String getTipLocatie() {
        return tipLocatie;
    }

    public void setTipLocatie(String tipLocatie) {
        this.tipLocatie = tipLocatie;
    }

    public Integer getRatingMinim() {
        return ratingMinim;
    }

    public void setRatingMinim(Integer ratingMinim) {
        this.ratingMinim = ratingMinim;
    }

    public String getDurata() {
        return durata;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }

}
