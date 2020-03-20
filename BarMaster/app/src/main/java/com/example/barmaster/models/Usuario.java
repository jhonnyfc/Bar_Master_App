package com.example.barmaster.models;

import com.parse.ParseFile;
import com.parse.ParseUser;

public class Usuario {
    private String nickName;
    private String nombre;
    private String apellido;
    private String correo;
    private String passWord;
    private ParseFile foto;

    private ParseUser userParse2;

    public Usuario (String nickName, String nombre, String apellido, String correo,String passWord,  ParseFile foto){
        this.nickName = nickName;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.passWord = passWord;
        this.foto = foto;
    }

    public String getNickName() {
        return nickName;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public String getPassWord() {
        return passWord;
    }

    public ParseFile getFoto() {
        return foto;
    }

    public ParseUser createParseUser(){
        userParse2 = new ParseUser();

        userParse2.setUsername(nickName);
        userParse2.setPassword(passWord);
        userParse2.setEmail(correo);

        // other fields can be set just like with ParseObject
        userParse2.put("name",nombre);
        userParse2.put("familyname",apellido);
        userParse2.put("foto",foto);

        return userParse2;
    }
}
