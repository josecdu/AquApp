package com.example.jodua.aquapp.Entidades;

import java.io.Serializable;

public class Medidor implements Serializable {
    private Integer med_cod;
    private Integer usu_cod;
    private String med_marca;
    private String med_serie;
    private String med_estado;
    private Integer med_ultlec;

    public Medidor(Integer med_cod, Integer usu_cod, String med_marca, String med_serie, String med_estado, Integer med_ultlec) {
        this.med_cod = med_cod;
        this.usu_cod = usu_cod;
        this.med_marca = med_marca;
        this.med_serie = med_serie;
        this.med_estado = med_estado;
        this.med_ultlec = med_ultlec;
    }

    public Medidor(){

    }

    public Integer getMed_cod() {
        return med_cod;
    }

    public Integer getUsu_cod() {
        return usu_cod;
    }

    public String getMed_marca() {
        return med_marca;
    }

    public String getMed_serie() {
        return med_serie;
    }

    public String getMed_estado() {
        return med_estado;
    }

    public Integer getMed_ultlec() {
        return med_ultlec;
    }

    public void setMed_cod(Integer med_cod) {
        this.med_cod = med_cod;
    }

    public void setUsu_cod(Integer usu_cod) {
        this.usu_cod = usu_cod;
    }

    public void setMed_marca(String med_marca) {
        this.med_marca = med_marca;
    }

    public void setMed_serie(String med_serie) {
        this.med_serie = med_serie;
    }

    public void setMed_estado(String med_estado) {
        this.med_estado = med_estado;
    }

    public void setMed_ultlec(Integer med_ultlec) {
        this.med_ultlec = med_ultlec;
    }
}
