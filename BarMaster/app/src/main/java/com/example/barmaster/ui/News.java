package com.example.barmaster.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.barmaster.Controlador_Activity;
import com.example.barmaster.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class News extends AppCompatActivity {
    private final String tableName = "News";
    private TextView textoNoticia;
    private Button skipBt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        textoNoticia = findViewById(R.id.notica_news);
        setNoticia();
        skipBt = findViewById(R.id.skipini_news);
        skipBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip();
            }
        });
    }

    public void setNoticia(){
        ParseQuery<ParseObject> misPostLikes = new ParseQuery(tableName);
        misPostLikes.orderByDescending("_created_at").getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                textoNoticia.setText(object.get("textoNoti").toString());
            }
        });
    }

    public void skip(){
        startActivity(new Intent(this, Controlador_Activity.class));
    }

}
