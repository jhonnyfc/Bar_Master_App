package com.example.barmaster.ui.rutinasGrupos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barmaster.R;
import com.example.barmaster.sharedData.MyAppDataControler;
import com.parse.FindCallback;
import com.parse.ParseException;
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

    private Integer valUser;

    private ProgressBar myProgrssBar;

    private LinearLayout miContainer;

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

        miContainer = rootOut.findViewById(R.id.containergruprut);

        myProgrssBar = rootOut.findViewById(R.id.loadmore_progress);
        myProgrssBar.setVisibility(View.VISIBLE);

        myRecyListCards = new ArrayList<>();
        myRecyListCards.add(new CardRowDataModelGR( null,"Loading..."));
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
        myRecyListCards.remove(0);
        myAdapterGr.notifyItemRemoved(0);
        getElemntCardBBDDAllFastONE("GrupoMuscular");
    }

    public void getElemntCardBBDDAllFastONE (String tableName){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(tableName);
        query.orderByAscending("id_gr");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                miContainer.setVisibility(View.VISIBLE);
                for (int i = 0; i < valUser; i++) {
                    String id_img = objects.get(i).get("im_code").toString();
                    myRecyListCards.add(i,new CardRowDataModelGR(getUrlFromGrupFoto(id_img), objects.get(i).get("grup_name").toString()));
                    myAdapterGr.notifyItemInserted(i);
                }
                myProgrssBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    public static String getUrlFromGrupFoto(String idFile) {
        String tableName = "FotosAll";
        ParseQuery<ParseObject> query = ParseQuery.getQuery(tableName);

        query.whereEqualTo("fotoName",idFile);

        try {
            return query.getFirst().getParseFile("picture").getUrl();

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
