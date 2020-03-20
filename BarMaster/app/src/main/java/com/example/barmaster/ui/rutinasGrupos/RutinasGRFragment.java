package com.example.barmaster.ui.rutinasGrupos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.barmaster.sharedData.MyAppDataControler;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class RutinasGRFragment extends Fragment {

    private RutinasGRViewModel mViewModel;

    private ArrayList<CardRowDataModelGR> myRecyListCards;
    private RecyclerView myRecyclerView;
    private RecyViwAdapterGR myAdapterGr;
    private RecyclerView.LayoutManager myLayoutManager;


    private List<ParseObject> miLista;
    private Integer valUser;

    public static RutinasGRFragment newInstance() {
        return new RutinasGRFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //mViewModel = ViewModelProviders.of(this).get(RutinasViewModel.class);
        View rootOut = inflater.inflate(R.layout.rutinas_fragment, container, false);

        MyAppDataControler controler = new MyAppDataControler(getContext());
        if (controler.exsitenDatos()){
            valUser = 7;
        }else {
            valUser = 6;
        }

        myRecyListCards = new ArrayList<>();
        myRecyListCards.add(0,getElemntCardBBDD(0,"GrupoMuscular"));

        myRecyclerView = rootOut.findViewById(R.id.lista_grupos);
        myRecyclerView.setHasFixedSize(true);
        myLayoutManager = new LinearLayoutManager(getActivity());
        myAdapterGr = new RecyViwAdapterGR(myRecyListCards);

        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setAdapter(myAdapterGr);

        myAdapterGr.setOnItemClickListener(new RecyViwAdapterGR.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Crear un Star activity segun la posicion de la que nos de
            }
        });

        return rootOut;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = ViewModelProviders.of(this).get(RutinasViewModel.class);
        // TODO: Use the ViewModel
//        if (myRecyListCards.size() == 1)
//            insertAll("GrupoMuscular");
//        actualizaRows();
    }

    @Override
    public void onStart() {
        super.onStart();
        insertAll("GrupoMuscular");
    }

    public void insertAll(String tableName){
        for (int i = 1; i < valUser; i++) {//Añadir de las tarjeta uno a uno
            myRecyListCards.add(i,getElemntCardBBDD(i,tableName));
            myAdapterGr.notifyItemInserted(i);
        }
    }

    public CardRowDataModelGR getElemntCardBBDD (Integer i,String tableName){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(tableName);
        query.whereEqualTo("id_gr",i.toString());

        ParseObject miCardData = null;
        try {
            miCardData = query.getFirst();

            String id_img = miCardData.get("im_code").toString();
            return new CardRowDataModelGR(getBitmapFromUser(id_img),miCardData.get("grup_name").toString());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Bitmap getBitmapFromUser(String idFile) {
        String tableName = "FotosAll";
        ParseQuery<ParseObject> query = ParseQuery.getQuery(tableName);

        query.whereEqualTo("fotoName",idFile);

        try {
            ParseObject object = query.getFirst();

            ParseFile mifile = object.getParseFile("picture");
            byte [] blob = mifile.getData();

            return BitmapFactory.decodeByteArray(blob, 0, blob.length);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

//    public void actualizaRows(){
//        MyAppDataControler controler = new MyAppDataControler(getContext());
//        if (controler.exsitenDatos()){
//            if (valUser != 7){
//                valUser = 7;
//                myRecyListCards.add(valUser-1,getElemntCardBBDD(valUser-1,"grup_name"));
//                myAdapterGr.notifyItemInserted(valUser-1);
//            }
//        }else {
//            if (valUser != 6){
//                valUser = 6;
//                myRecyListCards.remove(valUser);
//                myAdapterGr.notifyItemRemoved(valUser);
//            }
//        }
//    }
}
