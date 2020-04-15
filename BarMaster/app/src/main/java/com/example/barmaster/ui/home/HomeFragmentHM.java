package com.example.barmaster.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barmaster.R;
import com.example.barmaster.models.Ejercicio;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import cdflynn.android.library.turn.TurnLayoutManager;

public class HomeFragmentHM extends Fragment {
    private final String tableName = "Ejercicios";
    private final Integer DownBuffer = 3;
    private boolean isLoading;

    private HomeViewModel mViewModel;

    private ArrayList<Ejercicio> myRecyListCardsHM;
    private RecyclerView myRecyclerViewHM;
    private RecyViewAdapterHM myAdapterHM;
    private RecyclerView.LayoutManager myLayoutManagerHM;

    private Integer radius = 1400;
    private Integer peek = 110;
    private boolean shouldRotate = false;

    public static HomeFragmentHM newInstance() {
        return new HomeFragmentHM();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootOut = inflater.inflate(R.layout.home_fragment, container, false);
        isLoading = false;

        myRecyListCardsHM = new ArrayList<>();
        myRecyListCardsHM.add(null);

        myRecyclerViewHM = rootOut.findViewById(R.id.lista_eje_home);
        myRecyclerViewHM.setHasFixedSize(true);
        myLayoutManagerHM = new TurnLayoutManager(getContext(),              // provide a context
                TurnLayoutManager.Gravity.END,        // from which direction should the list items orbit?
                TurnLayoutManager.Orientation.VERTICAL, // Is this a vertical or horizontal scroll?
                radius,               // The radius of the item rotation
                peek,                 // Extra offset distance
                shouldRotate);
        myAdapterHM = new RecyViewAdapterHM(myRecyListCardsHM);

        myRecyclerViewHM.setLayoutManager(myLayoutManagerHM);
        myRecyclerViewHM.setAdapter(myAdapterHM);

        myAdapterHM.setOnItemClickListener(new RecyViewAdapterHM.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Crear una vista en la que se uetre el ejercio con su descripcion
            }
        });

        return rootOut;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
        getElemntCardBBDDAllFastONE();
    }

    public void getElemntCardBBDDAllFastONE (){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(tableName);
        if (myRecyListCardsHM.size() <= DownBuffer)
            query.orderByAscending("id_ej").setLimit(DownBuffer);
        else
            query.orderByAscending("id_ej").setLimit(DownBuffer).setSkip(myRecyListCardsHM.size());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                myRecyListCardsHM.remove(myRecyListCardsHM.size()-1);
                myAdapterHM.notifyItemRemoved(myRecyListCardsHM.size());
                for (ParseObject ob: objects){
                    myRecyListCardsHM.add(new Ejercicio(ob.get("id_ej").toString(), ob.get("Nombre").toString(),ob.get("nivel").toString(),
                            ob.get("musculos").toString(),ob.get("descripcion").toString(),ob.get("idFoto").toString()));
                    myAdapterHM.notifyItemInserted(myRecyListCardsHM.size()-1);
                }
                isLoading = false;
                if (myRecyListCardsHM.size() <= DownBuffer){
                    addListinerScroll();
                }
            }
        });
    }

    public void addListinerScroll(){
        myRecyclerViewHM.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                TurnLayoutManager LayoutManager = ( TurnLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading){
                    if (LayoutManager != null && LayoutManager.findLastCompletelyVisibleItemPosition() == myRecyListCardsHM.size()-1){
                        myRecyListCardsHM.add(null);
                        myAdapterHM.notifyItemInserted(myRecyListCardsHM.size() - 1);
                        isLoading = true;
                        getElemntCardBBDDAllFastONE();
                    }
                }
            }
        });
    }
}
