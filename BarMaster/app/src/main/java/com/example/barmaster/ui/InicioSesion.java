package com.example.barmaster.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.barmaster.Controlador_Activity;
import com.example.barmaster.R;
import com.example.barmaster.sharedData.MyAppDataControler;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class InicioSesion extends AppCompatActivity implements View.OnClickListener{
    private Button confirmarBt;
    private Button registrarseBt;
    private Button forgotPassBt;
    private Button skipBt;

    private EditText usuraioInTx;
    private EditText passwordInTx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);


        confirmarBt = findViewById(R.id.siginini);
        registrarseBt = findViewById(R.id.creataccountini);
        forgotPassBt = findViewById(R.id.forgotpassini);
        skipBt = findViewById(R.id.skipini);

        usuraioInTx = findViewById(R.id.nicknameini);
        passwordInTx = findViewById(R.id.passwordini);

        confirmarBt.setOnClickListener(this);
        registrarseBt.setOnClickListener(this);
        forgotPassBt.setOnClickListener(this);
        skipBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId() /*to get clicked view id**/) {
            case R.id.siginini:
                //Validar usuario
                sigIn();
                break;
            case R.id.creataccountini:
                //Crear cuenta
                createAccount();
                break;
            case R.id.forgotpassini:
                //CAmbiar contraseña e enviar nueva al correo
                forgotPassword();
                break;
            case R.id.skipini:
                //Saltar log ventan e ir el inicio
                skip();
                break;
            default:
                break;

        }
    }

    // Validar usuario
    public void sigIn(){
        String usuarioTx = usuraioInTx.getText().toString();
        final String contrasenaTx = passwordInTx.getText().toString();

        ParseUser.logInInBackground(usuarioTx, contrasenaTx, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Toast.makeText(getApplicationContext(), "Datos incorrectos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    // Hooray! The user is logged in.
                    Toast.makeText(getApplicationContext(), "Sesion correcta", Toast.LENGTH_SHORT).show();

                    //Acutalizacion de los datos si el usuario ha creado nueva cuenta
                    MyAppDataControler controler = new MyAppDataControler(getApplicationContext());
                    controler.saveMyDataByParse(user,contrasenaTx);
                    skip();
                }
            }
        });
    }

    //Creacion de nueva cuenta
    public void createAccount(){
        startActivity(new Intent(this, CreratAccount.class));
    }

    //Crear ventana de rest password
    public void forgotPassword(){

    }

    public void skip(){
        startActivity(new Intent(this, Controlador_Activity.class));
    }
}
