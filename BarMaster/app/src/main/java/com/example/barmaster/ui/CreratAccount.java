package com.example.barmaster.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.barmaster.Controlador_Activity;
import com.example.barmaster.R;
import com.example.barmaster.models.Usuario;
import com.example.barmaster.sharedData.MyAppDataControler;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;

public class CreratAccount extends AppCompatActivity implements View.OnClickListener{
    private Button confirmarBt;
    private Button skipBt;

    private EditText nickInTx;
    private EditText nameInTx;
    private EditText faminameInTx;
    private EditText passwordInTx;
    private EditText correoInTx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crerat_account);

        confirmarBt = findViewById(R.id.confirmarreg);
        skipBt = findViewById(R.id.skipreg);

        nickInTx = findViewById(R.id.nicknamereg);
        nameInTx = findViewById(R.id.namereg);
        faminameInTx = findViewById(R.id.familynamereg);
        correoInTx = findViewById(R.id.correoreg);
        passwordInTx = findViewById(R.id.passwordreg);

        confirmarBt.setOnClickListener(this);
        skipBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId() /*to get clicked view id**/) {
            case R.id.confirmarreg:
                //Validar usuario
                confirmar();
                break;
            case R.id.skipreg:
                //Saltar log ventan e ir el inicio
                skip();
//                finish();
                break;
            default:
                break;

        }
    }

    public void confirmar (){

        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.dfaultperfilph);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);// can use something 70 in case u want to compress the image

        byte[] scaledData = stream.toByteArray();

        ParseFile photoFile = new ParseFile("perfilFoto.jpg", scaledData);

        try {
            photoFile.save();

            final Usuario nuevoUser = new Usuario(nickInTx.getText().toString(),
                                        nameInTx.getText().toString(),
                                        faminameInTx.getText().toString(),
                                        correoInTx.getText().toString(),
                                        passwordInTx.getText().toString(),
                                        photoFile);

            ParseUser newUser = nuevoUser.createParseUser();
            newUser.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(getApplicationContext(), "Operacion Correcta", Toast.LENGTH_SHORT).show();

                        //Acutalizacion de los datos si el usuario ha creado nueva cuenta
                        MyAppDataControler controler = new MyAppDataControler(getApplicationContext());
                        controler.saveMyData(nuevoUser);

                        skip();
//                        finish();
                    } else {

                        // Sign up didn't succeed. Look at the ParseException
                        // to figure out what went wrong
                        Toast.makeText(getApplicationContext(), "$: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void skip(){
        startActivity(new Intent(this, Controlador_Activity.class));
    }
}

