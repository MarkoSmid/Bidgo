package com.example.uporabnik.rps_bidgo;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Chat extends AppCompatActivity {

    RelativeLayout chatView;
    Bundle extras;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        chatView =(RelativeLayout) findViewById(R.id.chatView);

        extras =getIntent().getExtras();
        username=extras.getString("username");
        String gov1;
        String gov2;
        Oglas g;
        Parcelable[] sporocila;
        try {
            gov1 = extras.getString("user");
            gov2 = extras.getString("pgov2");
            g = extras.getParcelable("oglas");

            sporocila = extras.getParcelableArray("sporocila");
            //Sporocilo sp=(Sporocilo)sporocila[0];

            if(sporocila.length>0) {
                for (int i = 0; i < sporocila.length; i++) {
                    RelativeLayout.LayoutParams textViewLayoutParams = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                    TextView textSporocilo = new TextView(this);
                    textSporocilo.setTextColor(Color.BLACK);
                    textSporocilo.setTextSize(20);
                    Sporocilo s =(Sporocilo)sporocila[i];
                    textViewLayoutParams.setMargins(0,100*i,0,0);
                    if(s.getPosiljatelj().equals(username)) {
                        textViewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        textSporocilo.setBackgroundColor(Color.CYAN);
                    }else {
                        textViewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                        textSporocilo.setBackgroundColor(Color.LTGRAY);
                    }
                    textSporocilo.setLayoutParams(textViewLayoutParams);
                    textSporocilo.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    textSporocilo.setText(s.getVsebina());

                    chatView.addView(textSporocilo);
                }
            }
        }
        catch (Exception ex){
            Toast.makeText(this,ex.toString(),Toast.LENGTH_LONG).show();
        }


    }

}
