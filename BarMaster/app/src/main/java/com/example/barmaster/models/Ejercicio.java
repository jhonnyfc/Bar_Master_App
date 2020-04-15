package com.example.barmaster.models;

public class Ejercicio {
    private String idEjer;
    private String nombreEjercicio;
    private String Difucultad;
    private String partesCuerpo;
    private  String descripcion;
    private String idFoto;

    public Ejercicio(String idEjer, String nombreEjercicio, String difucultad, String partesCuerpo, String descripcion, String idFoto) {
        this.idEjer = idEjer;
        this.nombreEjercicio = nombreEjercicio;
        Difucultad = difucultad;
        this.partesCuerpo = partesCuerpo;
        this.descripcion = descripcion;
        this.idFoto = idFoto;
    }

    public String getIdEjer() {
        return idEjer;
    }

    public void setIdEjer(String idEjer) {
        this.idEjer = idEjer;
    }

    public String getNombreEjercicio() {
        return nombreEjercicio;
    }

    public void setNombreEjercicio(String nombreEjercicio) {
        this.nombreEjercicio = nombreEjercicio;
    }

    public String getDifucultad() {
        return Difucultad;
    }

    public void setDifucultad(String difucultad) {
        Difucultad = difucultad;
    }

    public String getPartesCuerpo() {
        return partesCuerpo;
    }

    public void setPartesCuerpo(String partesCuerpo) {
        this.partesCuerpo = partesCuerpo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(String idFoto) {
        this.idFoto = idFoto;
    }
}
