package com.example.jodua.aquapp.Entidades;

import java.io.Serializable;

public class Usuario implements Serializable {

    private Integer usu_cod;
    private String usu_nom;
    private String usu_ape;
    private String usu_ci;
    private String usu_dir;
    private String usu_casanro;
    private String usu_tel;
    private String usu_correo;

    public Usuario(Integer usu_cod, String usu_nom, String usu_ape, String usu_ci,
                   String usu_dir, String usu_casanro, String usu_tel, String usu_correo) {
        this.usu_cod = usu_cod;
        this.usu_nom = usu_nom;
        this.usu_ape = usu_ape;
        this.usu_ci = usu_ci;
        this.usu_dir = usu_dir;
        this.usu_casanro = usu_casanro;
        this.usu_tel = usu_tel;
        this.usu_correo = usu_correo;
    }

    public Integer getUsu_cod() {
        return usu_cod;
    }

    public void setUsu_cod(Integer usu_cod) {
        this.usu_cod = usu_cod;
    }

    public String getUsu_nom() {
        return usu_nom;
    }

    public void setUsu_nom(String usu_nom) {
        this.usu_nom = usu_nom;
    }

    public String getUsu_ape() {
        return usu_ape;
    }

    public void setUsu_ape(String usu_ape) {
        this.usu_ape = usu_ape;
    }

    public String getUsu_ci() {
        return usu_ci;
    }

    public void setUsu_ci(String usu_ci) {
        this.usu_ci = usu_ci;
    }

    public String getUsu_dir() {
        return usu_dir;
    }

    public void setUsu_dir(String usu_dir) {
        this.usu_dir = usu_dir;
    }

    public String getUsu_casanro() {
        return usu_casanro;
    }

    public void setUsu_casanro(String usu_casanro) {
        this.usu_casanro = usu_casanro;
    }

    public String getUsu_tel() {
        return usu_tel;
    }

    public void setUsu_tel(String usu_tel) {
        this.usu_tel = usu_tel;
    }

    public String getUsu_correo() {
        return usu_correo;
    }

    public void setUsu_correo(String usu_correo) {
        this.usu_correo = usu_correo;
    }
}
