package com.example.barmaster.ui.rutinasGrupos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barmaster.R;
import com.example.barmaster.models.GrupoMuscularRutina;
import com.example.barmaster.sharedData.MyAppDataControler;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class RutinasGRFragment extends Fragment {

    private RutinasGRViewModel mViewModel;

    private ArrayList<GrupoMuscularRutina> myRecyListCardsGR;
    private RecyclerView myRecyclerViewGR;
    private RecyViewAdapterGR myAdapterGR;
    private RecyclerView.LayoutManager myLayoutManagerGR;

    private Integer valUser;

    public static RutinasGRFragment newInstance() {
        return new RutinasGRFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //mViewModel = ViewModelProviders.of(this).get(RutinasViewModel.class);
        View rootOut = inflater.inflate(R.layout.grupos_rutinas_fragment, container, false);

        MyAppDataControler controler = new MyAppDataControler(getContext());
        if (controler.exsitenDatos()){
            valUser = 7;
        }else {
            valUser = 6;
        }
        myRecyListCardsGR = new ArrayList<>();
        myRecyListCardsGR.add(null);

        myRecyclerViewGR = rootOut.findViewById(R.id.lista_grupos);
        myRecyclerViewGR.setHasFixedSize(true);
        myLayoutManagerGR = new LinearLayoutManager(getActivity());
        myAdapterGR = new RecyViewAdapterGR(myRecyListCardsGR);

        myRecyclerViewGR.setLayoutManager(myLayoutManagerGR);
        myRecyclerViewGR.setAdapter(myAdapterGR);

        myAdapterGR.setOnItemClickListener(new RecyViewAdapterGR.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Crear un Star Fragement segun la posicion de la que nos de
            }
        });

        return rootOut;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getElemntCardBBDDAllFastONE("GrupoMuscular");
    }

    public void getElemntCardBBDDAllFastONE (String tableName){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(tableName);
        query.orderByAscending("id_gr");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> ob, ParseException e) {
                myRecyListCardsGR.remove(0);
                myAdapterGR.notifyItemRemoved(0);
                for (int i = 0; i<valUser; i++){
                    myRecyListCardsGR.add(new GrupoMuscularRutina(ob.get(i).get("im_code").toString(), ob.get(i).get("grup_name").toString()));
                    myAdapterGR.notifyItemInserted(myRecyListCardsGR.size()-1);
                }
            }
        });
    }
}
