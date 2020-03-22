package com.example.barmaster.models;

public class Post {
    private String idCreator;
    private String rutinaName;
    private Integer duration;
    private String comment;
    private String fotoPostUrl;
    private Integer likes;      //Para la obtencionde de este dato se hara una cuneta sobre la tabla Likes
                                //que contendra todos los liques por foto con un identificador

    public Post(String idCreator, String rutinaName, Integer duration, String comment, String fotoPostUrl){
        this.idCreator = idCreator;
        this.rutinaName = rutinaName;
        this.duration = duration;
        this.comment = comment;
        this.fotoPostUrl = fotoPostUrl;
        this.likes = likes;
    }
}
