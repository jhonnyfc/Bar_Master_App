package com.example.barmaster.models;

public class GrupoMuscularRutina {
    private  final String TableName = "GrupoMuscular";
    private  final String id = "id_gr";
    private  final String nameGr = "grup_name";
    private  final String foto  = "im_code";

    private String imageId;
    private String titleId;//This is a number between 0 - 6
    // 0: FullBody, 1: ChestTriceps, 2: Legs, 3:BackBiceps, 4:Core, 5: Shoulders, 6: Personalizado

    public GrupoMuscularRutina(String imageId, String titleId) {
        this.imageId = imageId;
        this.titleId = titleId;
    }

    public String getImageId() {
        return imageId;
    }

    public String getTitle() {
        return titleId;
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
//
//    public ParseObject getData(Integer idQue){//Hacer una query para extraer los datos de una rutina
//        ParseObject rutGrup = new ParseObject(TableName);
//
//        rutGrup.put(id,idQue);
//        rutGrup.put(nameGr,nombreRutGrup);
//        rutGrup.put(foto,IDfoto);
//
//        return rutGrup;
//    }
}
