package com.example.barmaster.ui.comunidad;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barmaster.R;
import com.example.barmaster.models.Post;
import com.example.barmaster.sharedData.MyAppDataControler;

import java.util.ArrayList;

public class ComunidadFragment extends Fragment {

    private ComunidadViewModel mViewModel;

    private ArrayList<Post> myRecyListCardsCM;
    private RecyclerView myRecyclerViewCM;
    private RecyViwAdapterCM myAdapterCM;
    private RecyclerView.LayoutManager myLayoutManagerCM;

    public static ComunidadFragment newInstance() {
        return new ComunidadFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.comunidad_fragment, container, false);

        MyAppDataControler controler = new MyAppDataControler(getContext());
        if (controler.exsitenDatos() == false){
            // Si ha iniciado sesion debe aparecer el boton flotante para agregar nuevo post


            myRecyListCardsCM = new ArrayList<>();
            myRecyListCardsCM.add(null);// setear un row por defecto creadndo pos a null maybe

            myRecyclerViewCM = root.findViewById(R.id.lista_grupos);
            myRecyclerViewCM.setHasFixedSize(true);
            myLayoutManagerCM = new LinearLayoutManager(getActivity());
            myAdapterCM = new RecyViwAdapterCM(myRecyListCardsCM);

            myRecyclerViewCM.setLayoutManager(myLayoutManagerCM);
            myRecyclerViewCM.setAdapter(myAdapterCM);

            myAdapterCM.setOnItemClickListener(new RecyViwAdapterCM.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    //Crear un Star activity segun la posicion de la que nos de
                }
            });

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
