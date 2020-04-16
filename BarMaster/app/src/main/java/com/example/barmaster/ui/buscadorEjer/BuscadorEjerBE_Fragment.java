package com.example.barmaster.ui.buscadorEjer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barmaster.R;
import com.example.barmaster.models.Ejercicio;
import com.example.barmaster.ui.home.RecyViewAdapterHM;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class BuscadorEjerBE_Fragment extends Fragment {
    private final String tableName = "Ejercicios";
    private final Integer DownBuffer = 4;
    private boolean isLoading;
    private BuscadorEjerBEViewModel mViewModel;

    private ArrayList<Ejercicio> myRecyListCardsBE;
    private RecyclerView myRecyclerViewBE;
    private RecyViewAdapterHM myAdapterBE;
    private RecyclerView.LayoutManager myLayoutManagerBE;

    private ArrayList<String> mySpListFilterBE;
    private Spinner mySpViewBE;
    private ArrayAdapter<String> mySpAdapterBE;
    private Integer musculoFilter;
    private Integer primera = 0;

    private View rootOut;
    public static BuscadorEjerBE_Fragment newInstance() {
        return new BuscadorEjerBE_Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootOut = inflater.inflate(R.layout.buscador_ejer_be_fragment, container, false);
        isLoading = false;

        musculoFilter = 0;
        mySpListFilterBE = new ArrayList<>();
        mySpListFilterBE.add("All");
        mySpListFilterBE.add("Core");
        mySpListFilterBE.add("Espalda,biceps");
        mySpListFilterBE.add("Hombros");
        mySpListFilterBE.add("Pecho,triceps");
        mySpListFilterBE.add("Piernas");
        mySpViewBE = rootOut.findViewById(R.id.spinner_list_busca);
        mySpAdapterBE = new ArrayAdapter<String>(getContext(),R.layout.x_text_sppiner,mySpListFilterBE);
        mySpAdapterBE.setDropDownViewResource(R.layout.x_text_sppiner);
        mySpViewBE.setAdapter(mySpAdapterBE);

        mySpViewBE.setOnItemSelectedListener(new CustomOnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
                if(primera > 0)
                    myRecyclerViewBE.clearOnScrollListeners();
                musculoFilter = position;

                myRecyListCardsBE = new ArrayList<>();
                myRecyListCardsBE.add(null);

                myRecyclerViewBE = rootOut.findViewById(R.id.lista_eje_busca);
                myRecyclerViewBE.setHasFixedSize(true);
                myLayoutManagerBE = new LinearLayoutManager(getActivity());
                myAdapterBE = new RecyViewAdapterHM(myRecyListCardsBE);

                myRecyclerViewBE.setLayoutManager(myLayoutManagerBE);
                myRecyclerViewBE.setAdapter(myAdapterBE);
                myAdapterBE.setOnItemClickListener(new RecyViewAdapterHM.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        //LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL
                        //LLamar a la ventan en la que se muestra la el ejercicio con su descricion
                    }
                });
                getElemntCardBBDDAllFastONE(position);
                primera++;
            }
        });
        return rootOut;
    }

//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//    }

    public void getElemntCardBBDDAllFastONE (Integer musculoId){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(tableName);
        if (musculoId > 0)
            query.whereEqualTo("musculos",mySpListFilterBE.get(musculoId));

        if (myRecyListCardsBE.size() <= DownBuffer)
            query.orderByAscending("id_ej").setLimit(DownBuffer);
        else
            query.orderByAscending("id_ej").setLimit(DownBuffer).setSkip(myRecyListCardsBE.size());

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                System.out.println(myRecyListCardsBE.size());
                myRecyListCardsBE.remove(myRecyListCardsBE.size()-1);
                System.out.println(myRecyListCardsBE.size()+"_______________________________");
                myAdapterBE.notifyItemRemoved(myRecyListCardsBE.size());
                for (ParseObject ob: objects){
                    myRecyListCardsBE.add(new Ejercicio(ob.get("id_ej").toString(), ob.get("Nombre").toString(),ob.get("nivel").toString(),
                            ob.get("musculos").toString(),ob.get("descripcion").toString(),ob.get("idFoto").toString()));
                    myAdapterBE.notifyItemInserted(myRecyListCardsBE.size()-1);
                }
                isLoading = false;
                if (myRecyListCardsBE.size() <= DownBuffer){
                    addListinerScroll();
                }
            }
        });
    }

    public void addListinerScroll(){
        myRecyclerViewBE.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager LayoutManager = ( LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading){
                    if (LayoutManager != null && LayoutManager.findLastCompletelyVisibleItemPosition() == myRecyListCardsBE.size()-1){
                        myRecyListCardsBE.add(null);
                        myAdapterBE.notifyItemInserted(myRecyListCardsBE.size() - 1);
                        isLoading = true;
                        getElemntCardBBDDAllFastONE(musculoFilter);
                    }
                }
            }
        });
    }

    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            Toast.makeText(parent.getContext(), "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }
}
