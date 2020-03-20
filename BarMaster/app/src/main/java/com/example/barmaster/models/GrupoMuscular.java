package com.example.barmaster.models;

import com.parse.ParseObject;

public class GrupoMuscular {
    private  final String TableName = "GrupoMuscular";
    private  final String id = "id_gr";
    private  final String nameGr = "grup_name";
    private  final String foto  = "im_code";

    private String nombreRutGrup;
    private String IDfoto;
    private String seccionCuerpo; //This is a number between 0 - 6
                    // 0: FullBody, 1: ChestTriceps, 2: Legs, 3:BackBiceps, 4:Core, 5: Shoulders, 6: Personalizado

    public GrupoMuscular(){

    }

//
//    public ParseObject createParse(String idGr){
//        ParseObject rutGrup = new ParseObject(TableName);
//
//        rutGrup.put(id,idGr);
//        rutGrup.put(nameGr,nombreRutGrup);
//        rutGrup.put(foto,IDfoto);
//
//        return rutGrup;
//    }

    public ParseObject getData(Integer idQue){//Hacer una query para extraer los datos de una rutina
        ParseObject rutGrup = new ParseObject(TableName);

        rutGrup.put(id,idQue);
        rutGrup.put(nameGr,nombreRutGrup);
        rutGrup.put(foto,IDfoto);

        return rutGrup;
    }
}