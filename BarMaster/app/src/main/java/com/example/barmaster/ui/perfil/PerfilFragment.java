package com.example.barmaster.ui.perfil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.barmaster.R;
import com.example.barmaster.sharedData.MyAppDataControler;
import com.example.barmaster.ui.InicioSesion;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.ArrayList;

public class PerfilFragment extends Fragment implements View.OnClickListener {
    private MyAppDataControler myPrefrences;

    private PerfilViewModel miViewModelPerfil;
    private LinearLayout containerData;
    private com.getbase.floatingactionbutton.FloatingActionButton logOutExt;
    private com.getbase.floatingactionbutton.FloatingActionButton configData;

    public static PerfilFragment newInstance() {
        return new PerfilFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //miViewModelPerfil = ViewModelProviders.of(this).get(PerfilViewModel.class);
        View root = inflater.inflate(R.layout.perfil_fragment, container, false);

        MyAppDataControler controler = new MyAppDataControler(getContext());
        if (controler.exsitenDatos() == false){
            logIn();
            return root;
        }else {
            miViewModelPerfil = new PerfilViewModel();

            final ImageView perfilView = root.findViewById(R.id.mifotoperf);
            final TextView txtlViewNick = root.findViewById(R.id.nicknameperf);
            final TextView txtlViewName = root.findViewById(R.id.nameperf);
            final TextView txtlViewCorreo = root.findViewById(R.id.correoperf);
            containerData = root.findViewById(R.id.contentdataperf);
            containerData.setVisibility(View.INVISIBLE);

            miViewModelPerfil.getMiFoto().observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
                @Override
                public void onChanged(Bitmap im) {
                    perfilView.setImageBitmap(im);
                }
            });

            miViewModelPerfil.getMyData().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
                @Override
                public void onChanged(ArrayList<String> data) {
                    txtlViewNick.setText(data.get(0));
                    txtlViewName.setText(data.get(1));
                    txtlViewCorreo.setText(data.get(2));
                }
            });

            return root;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (ParseUser.getCurrentUser() == null){
            logIn();
        }else {
            RelativeLayout miRtw = getActivity().findViewById(R.id.acctionbtperf);
            miRtw.bringToFront();

            containerData.setVisibility(View.VISIBLE);
            logOutExt = getActivity().findViewById(R.id.logoutbtft);
            logOutExt.setOnClickListener(this);

            configData = getActivity().findViewById(R.id.setingbtft);
            configData.setOnClickListener(this);

            miViewModelPerfil.setMiFoto(getBitmapFromUser(ParseUser.getCurrentUser()));
            miViewModelPerfil.setMistr(getData(ParseUser.getCurrentUser()));
        }
    }

    private void logIn() {
        startActivity(new Intent(getContext(), InicioSesion.class));
    }


    public Bitmap getBitmapFromUser(ParseUser userCc) {
        try {
            ParseFile mifile = userCc.getParseFile("foto");
            byte [] blob = mifile.getData();

            Bitmap  myBitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);

            System.out.println("lolok");
            return myBitmap;
        } catch (ParseException e) {
            // Log exception
            return null;
        }

    }

    public void onClickLogout(){
        ParseUser.logOut();
        containerData.setVisibility(View.INVISIBLE);
        MyAppDataControler controler = new MyAppDataControler(getContext());
        if (controler.cleardata()){
            Toast.makeText(getContext(), "Cierre de cession correcto", Toast.LENGTH_SHORT).show();
            logIn();
        }else{
            Toast.makeText(getContext(), "Error al cerrar cesion", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logoutbtft:
                onClickLogout();
                break;
            case R.id.setingbtft://Pantalla de configuracion de datos
                modificarData();
                break;
            default:
                break;

        }
    }

    public ArrayList<String> getData (ParseUser currenUser){
        ArrayList<String> myData =  new ArrayList<>();
        myData.add(0,currenUser.getUsername());
        myData.add(1,currenUser.get("name").toString());
        myData.add(2,currenUser.getEmail());
        return myData;
    }

    public void modificarData(){
        startActivity(new Intent(getActivity(), EditProfile.class));
    }
}
