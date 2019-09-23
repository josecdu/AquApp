package com.example.jodua.aquapp.Entidades;

import java.io.Serializable;

public class Lectura implements Serializable {

    private Integer lec_cod;
    private String flec_act;
    private Integer lec_act;
    private String flec_ant;
    private Integer lec_ant;
    private String lec_estado;
    private String lec_foto;
    private Integer per_cod;
    private Integer med_cod;
    private Integer cic_cod;

    public Lectura() {
        this.lec_cod = lec_cod;
        this.flec_act = flec_act;
        this.lec_act = lec_act;
        this.flec_ant = flec_ant;
        this.lec_ant = lec_ant;
        this.lec_estado = lec_estado;
        this.lec_foto = lec_foto;
        this.per_cod = per_cod;
        this.med_cod = med_cod;
        this.cic_cod = cic_cod;
    }

    public Integer getLec_cod() {
        return lec_cod;
    }

    public void setLec_cod(Integer lec_cod) {
        this.lec_cod = lec_cod;
    }

    public String getFlec_act() {
        return flec_act;
    }

    public void setFlec_act(String flec_act) {
        this.flec_act = flec_act;
    }

    public Integer getLec_act() {
        return lec_act;
    }

    public void setLec_act(Integer lec_act) {
        this.lec_act = lec_act;
    }

    public String getFlec_ant() {
        return flec_ant;
    }

    public void setFlec_ant(String flec_ant) {
        this.flec_ant = flec_ant;
    }

    public Integer getLec_ant() {
        return lec_ant;
    }

    public void setLec_ant(Integer lec_ant) {
        this.lec_ant = lec_ant;
    }

    public String getLec_estado() {
        return lec_estado;
    }

    public void setLec_estado(String lec_estado) {
        this.lec_estado = lec_estado;
    }

    public String getLec_foto() {
        return lec_foto;
    }

    public void setLec_foto(String lec_foto) {
        this.lec_foto = lec_foto;
    }

    public Integer getPer_cod() {
        return per_cod;
    }

    public void setPer_cod(Integer per_cod) {
        this.per_cod = per_cod;
    }

    public Integer getMed_cod() {
        return med_cod;
    }

    public void setMed_cod(Integer med_cod) {
        this.med_cod = med_cod;
    }

    public Integer getCic_cod() {
        return cic_cod;
    }

    public void setCic_cod(Integer cic_cod) {
        this.cic_cod = cic_cod;
    }
}
