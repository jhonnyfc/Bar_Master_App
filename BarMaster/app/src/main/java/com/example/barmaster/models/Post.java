package com.example.barmaster.models;

import java.util.Date;

public class Post {
    private String idObjeto;
    private String idCreator;
    private String rutinaName;
    private Float duration;
    private String comment;
    private String fotoPostUrl;
    private String likesId;      //Para la obtencionde de este dato se hara una cuneta sobre la tabla Likes
                                //que contendra todos los liques por foto con un identificador
    private Date fecha;

    public Post(String idObjeto,String idCreator, String rutinaName, Float duration, String comment, String fotoPostUrl,String likesId, Date fecha){
        this.idObjeto = idObjeto;
        this.idCreator = idCreator;
        this.rutinaName = rutinaName;
        this.duration = duration;
        this.comment = comment;
        this.fotoPostUrl = fotoPostUrl;
        this.likesId = likesId;
        this.fecha = fecha;
    }

    public String getRutinaName() {
        return rutinaName;
    }

    public void setRutinaName(String rutinaName) {
        this.rutinaName = rutinaName;
    }

    public String getIdCreator() {
        return idCreator;
    }

    public void setIdCreator(String idCreator) {
        this.idCreator = idCreator;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFotoPostUrl() {
        return fotoPostUrl;
    }

    public void setFotoPostUrl(String fotoPostUrl) {
        this.fotoPostUrl = fotoPostUrl;
    }

    public String getLikesId() {
        return likesId;
    }

    public void setLikesId(String likesId) {
        this.likesId = likesId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getIdObjeto() {
        return idObjeto;
    }

    public void setIdObjeto(String idObjeto) {
        this.idObjeto = idObjeto;
    }
}
