package com.example.barmaster.models;

public class Post {
    private String idCreator;
    private String rutinaName;
    private Integer duration;
    private String comment;
    private String fotoPostUrl;
    private String likes;      //Para la obtencionde de este dato se hara una cuneta sobre la tabla Likes
                                //que contendra todos los liques por foto con un identificador

    public Post(String idCreator, String rutinaName, Integer duration, String comment, String fotoPostUrl,String likes){
        this.idCreator = idCreator;
        this.rutinaName = rutinaName;
        this.duration = duration;
        this.comment = comment;
        this.fotoPostUrl = fotoPostUrl;
        this.likes = likes;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
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

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }
}
