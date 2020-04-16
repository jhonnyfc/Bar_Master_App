package com.example.barmaster.ui.ejercicioshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.barmaster.R;
import com.example.barmaster.ui.buscadorEjer.BuscadorEjerBE_Fragment;
import com.example.barmaster.ui.home.HomeFragmentHM;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class EjercicioShow extends Fragment {
    private final String tableFotos = "FotosAll";
    private final String tableName = "Ejercicios";

    private TextView nombreEjer;
    private TextView levelEjer;
    private TextView muscleEjer;
    private TextView descripTex;
    private ImageView fotoEjer;
    private FloatingActionButton goBackFloat;

    private String IdEjer;
    private Bundle bundle;

    private EjercicioShowViewModel mViewModel;

    public static EjercicioShow newInstance() {
        return new EjercicioShow();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootVw = inflater.inflate(R.layout.ejercicio_show_fragment, container, false);

        bundle = this.getArguments();
        nombreEjer = rootVw.findViewById(R.id.nombre_rutina_eje);
        levelEjer = rootVw.findViewById(R.id.nivel_tra_eje);
        muscleEjer = rootVw.findViewById(R.id.muscle_eje);
        descripTex = rootVw.findViewById(R.id.descrip_eje);
        fotoEjer = rootVw.findViewById(R.id.foto_eje);
        goBackFloat = rootVw.findViewById(R.id.goback_eje);
        goBackFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        cargarDatos();
        return rootVw;
    }

    public void cargarDatos(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(tableName);
        query.whereEqualTo("id_ej",bundle.getString("idEjer"));
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                nombreEjer.setText(object.get("Nombre").toString());
                levelEjer.setText(object.get("nivel").toString());
                muscleEjer.setText(object.get("musculos").toString());
                descripTex.setText(object.get("descripcion").toString());

                ParseQuery<ParseObject> query = ParseQuery.getQuery(tableFotos);
                query.whereEqualTo("fotoName",object.get("idFoto"));
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        String urlFoto = object.getParseFile("picture").getUrl();

                        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.progresspiner).error(R.drawable.progresspiner);
                        Glide.with(getContext())
                                .load(urlFoto)
                                .apply(requestOptions)
                                .into(fotoEjer);
                    }
                });
            }
        });
    }

    public void goBack(){
        if (bundle.getString("view").equals("hm")){
            Fragment someFragment = new HomeFragmentHM();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, someFragment ); // give your fragment container id in first parameter
            ((FragmentTransaction) transaction).addToBackStack(null);  // if written, this transaction will be added to backstack
            transaction.commit();
        }else{
            Fragment someFragment = new BuscadorEjerBE_Fragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, someFragment ); // give your fragment container id in first parameter
            ((FragmentTransaction) transaction).addToBackStack(null);  // if written, this transaction will be added to backstack
            transaction.commit();
        }

    }
}
