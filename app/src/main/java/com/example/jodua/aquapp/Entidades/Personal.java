package com.example.jodua.aquapp.Entidades;

import java.io.Serializable;

public class Personal implements Serializable {

    private Integer per_cod;
    private String per_nom;
    private String per_ape;
    private String per_ci;
    private String per_dir;
    private String usu_casanro;
    private String per_log;
    private String per_pass;
    private String per_estado;
    public static String per_codigo="1";

    public Personal(Integer per_cod, String per_nom, String per_ape, String per_ci, String per_dir,
                    String usu_casanro, String per_log, String per_pass, String per_estado) {
        this.per_cod = per_cod;
        this.per_nom = per_nom;
        this.per_ape = per_ape;
        this.per_ci = per_ci;
        this.per_dir = per_dir;
        this.usu_casanro = usu_casanro;
        this.per_log = per_log;
        this.per_pass = per_pass;
        this.per_estado = per_estado;
    }

    public Integer getPer_cod() {
        return per_cod;
    }

    public void setPer_cod(Integer per_cod) {
        this.per_cod = per_cod;
    }

    public String getPer_nom() {
        return per_nom;
    }

    public void setPer_nom(String per_nom) {
        this.per_nom = per_nom;
    }

    public String getPer_ape() {
        return per_ape;
    }

    public void setPer_ape(String per_ape) {
        this.per_ape = per_ape;
    }

    public String getPer_ci() {
        return per_ci;
    }

    public void setPer_ci(String per_ci) {
        this.per_ci = per_ci;
    }

    public String getPer_dir() {
        return per_dir;
    }

    public void setPer_dir(String per_dir) {
        this.per_dir = per_dir;
    }

    public String getUsu_casanro() {
        return usu_casanro;
    }

    public void setUsu_casanro(String usu_casanro) {
        this.usu_casanro = usu_casanro;
    }

    public String getPer_log() {
        return per_log;
    }

    public void setPer_log(String per_log) {
        this.per_log = per_log;
    }

    public String getPer_pass() {
        return per_pass;
    }

    public void setPer_pass(String per_pass) {
        this.per_pass = per_pass;
    }

    public String getPer_estado() {
        return per_estado;
    }

    public void setPer_estado(String per_estado) {
        this.per_estado = per_estado;
    }
}
