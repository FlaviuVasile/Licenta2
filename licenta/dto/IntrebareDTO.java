package com.licenta.dto;

public class IntrebareDTO {
    private String intrebare;
    private String tipLocatie;
    private Integer ratingMinim;
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
