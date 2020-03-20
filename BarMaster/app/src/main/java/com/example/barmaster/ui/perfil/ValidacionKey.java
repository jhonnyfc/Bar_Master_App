package com.example.barmaster.ui.perfil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.barmaster.R;

public class ValidacionKey extends Activity{

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validacion_key);

        intent = getIntent();
    }

    public void pushWord(View view) {
        EditText editText = (EditText) findViewById(R.id.passwordvali);
        String message = editText.getText().toString();

        if (message.compareTo("") != 0) { //0 if is equal
            Bundle bundle = new Bundle();
            bundle.putString("contraseña", message);
            setResult(RESULT_OK,new Intent().putExtras(bundle));
            finish();
        }else{
            Toast.makeText(this, "Introduzca su contraseña", Toast.LENGTH_SHORT).show();
        }
    }

    public void goBack(View v){
        Bundle bundle = new Bundle();
        bundle.putString("contraseña", "");
        setResult(RESULT_CANCELED,new Intent().putExtras(bundle));
        finish();
    }
}
