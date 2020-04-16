package com.example.barmaster.sharedData;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.barmaster.models.Usuario;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MyAppDataControler {
    private final String DATA_FNAME = "credenciales";
    private final String PREFS_NAME = "MyPrefsFile";

    private Context myContext;
    private SharedPreferences prefs;
    private Usuario myData;

    public MyAppDataControler(Context myContext){
        this.myContext = myContext;
    }

    public boolean exsitenDatos(){
        prefs = myContext.getSharedPreferences(DATA_FNAME,myContext.MODE_PRIVATE);

        if (prefs.getString("nickName",null) == null){
            return false;
        }else
            return true;
    }

    public boolean previouslyStarted(){
        prefs = myContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        if (prefs.getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");

            // first time task
            // record the fact that the app has been started at least once
            prefs.edit().putBoolean("my_first_time", false).commit();
            return false;
        }else{
            return true;
        }
    }

    public boolean previouslyStartedDayNews() {
        try {
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String strDateNow = dateFormat.format(date);

            prefs = myContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            String strDataPrev = prefs.getString("ulti_vista_news",null);
            if (strDataPrev == null){
                prefs.edit().putString("ulti_vista_news",strDateNow).commit();
                return false;
            }else{
                Integer diff = Math.round( (dateFormat.parse(strDateNow).getTime() - dateFormat.parse(strDataPrev).getTime())/(60*1000) );
                if (diff < 35){//si ha pasado menos de estos minutos no se muestra la alerta y no se actualiza la hora
                    return true;
                }else{
                    prefs.edit().putString("ulti_vista_news",strDateNow).commit();
                    return false;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return true;
        }
    }

    public Usuario getMyData(){
        prefs = myContext.getSharedPreferences(DATA_FNAME,myContext.MODE_PRIVATE);

        myData = new Usuario(prefs.getString("nickName",null)
                            ,prefs.getString("name",null)
                            ,prefs.getString("familyname",null)
                            ,prefs.getString("correo",null)
                            ,prefs.getString("passWord",null)
                            ,null);
        return myData;
    }

    public boolean saveMyData(Usuario misNuevosDatos){
        prefs = myContext.getSharedPreferences(DATA_FNAME,myContext.MODE_PRIVATE);

        SharedPreferences.Editor putData = prefs.edit();
        putData.putString("nickName",misNuevosDatos.getNickName());
        putData.putString("name",misNuevosDatos.getNombre());
        putData.putString("familyname",misNuevosDatos.getApellido());
        putData.putString("correo",misNuevosDatos.getCorreo());
        putData.putString("passWord",misNuevosDatos.getPassWord());

        return putData.commit();
    }

    public boolean saveMyDataByParse(ParseUser newUser, String newKey){
        prefs = myContext.getSharedPreferences(DATA_FNAME,myContext.MODE_PRIVATE);

        SharedPreferences.Editor putData = prefs.edit();
        putData.putString("nickName",newUser.getUsername());
        putData.putString("name",newUser.get("name").toString());
        putData.putString("familyname",newUser.get("familyname").toString());
        putData.putString("correo",newUser.getEmail());
        if (!newKey.equals("")){
            putData.putString("passWord",newKey);
        }

        return putData.commit();
    }

    public boolean cleardata() {
        prefs = myContext.getSharedPreferences(DATA_FNAME,myContext.MODE_PRIVATE);

        SharedPreferences.Editor putData = prefs.edit();
        putData.putString("nickName",null);
        putData.putString("name",null);
        putData.putString("familyname",null);
        putData.putString("correo",null);
        putData.putString("passWord",null);
        return putData.commit();
    }
}
