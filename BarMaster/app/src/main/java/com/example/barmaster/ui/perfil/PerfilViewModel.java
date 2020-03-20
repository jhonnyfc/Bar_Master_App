package com.example.barmaster.ui.perfil;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class PerfilViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<Bitmap> miFoto;
    private MutableLiveData<ArrayList<String>> miData; //nickName, name, correo,

    public PerfilViewModel() {
        miFoto = new MutableLiveData<>();
        miFoto.setValue(null);
        miData = new MutableLiveData<>();
        ArrayList<String> my = new ArrayList<String>();
        my.add(0,"");
        my.add(1,"");
        my.add(2,"");
        miData.setValue(my);
    }

    public MutableLiveData<Bitmap> getMiFoto() {
        return miFoto;
    }

    public void setMiFoto(Bitmap miFoto) {
        this.miFoto.setValue(miFoto);
    }

    public void setMistr(ArrayList<String> data) {
        miData.setValue(data);
    }

    public MutableLiveData<ArrayList<String>>  getMyData(){
        return miData;
    }
}
