package com.example.barmaster;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.barmaster.sharedData.MyAppDataControler;
import com.example.barmaster.ui.InicioSesion;
import com.example.barmaster.ui.News;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class Controlador_Activity extends AppCompatActivity {
    private final String tableName = "News";
    private MyAppDataControler myPrefrences;

    @Override
    protected void onStart() {
        super.onStart();

        myPrefrences = new MyAppDataControler(this);
        if (!myPrefrences.previouslyStarted()){
            //Si es la primera vez que abre la app se le mandara a la pagina de registrar/iniicar sesion
            startActivity(new Intent(this, InicioSesion.class));
        } else if (!myPrefrences.previouslyStartedDayNews()){
            ParseQuery<ParseObject> misPostLikes = new ParseQuery(tableName);
            try {
                Integer numbre = misPostLikes.count();
                if (numbre > 0)
                    startActivity(new Intent(this, News.class));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configuracion de la barra
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                        R.id.navigation_home, R.id.navigation_rutinas, R.id.navigation_comunidad, R.id.navigation_perfil)
                    .build();

        // Creacion de la vista tipo frgamento creada
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.saveState();

        // Asigcacion de los titulos a las barras se ha quitado dado que es anticuado
        // si se quiere volver a poner modificar nambien en el estilo
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Creacion de la vista del boton
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Creacion de la barra de navegacion
        // Asociacion de la barra de navegacion con las vistas de cada boton
        NavigationUI.setupWithNavController(navView, navController);
    }

}
