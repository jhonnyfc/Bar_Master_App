package com.example.barmaster.ui.comunidad;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.barmaster.R;
import com.example.barmaster.sharedData.MyAppDataControler;

public class ComunidadFragment extends Fragment {

    private ComunidadViewModel mViewModel;

    public static ComunidadFragment newInstance() {
        return new ComunidadFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.comunidad_fragment, container, false);

        MyAppDataControler controler = new MyAppDataControler(getContext());
        if (controler.exsitenDatos() == false){
            // Si ha iniciado sesion debe aparecer el boton flotante para agregar nuevo post

            return root;
        }else {
            return root;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = ViewModelProviders.of(this).get(ComunidadViewModel.class);
        // TODO: Use the ViewModel
    }

}
