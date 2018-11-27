package com.example.uporabnik.rps_bidgo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class Klepet extends AppCompatActivity {

    LinearLayout klepetiView;
    ArrayList<Pogovor> pogovori;
    Bundle extras;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klepet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        extras=getIntent().getExtras();
        username =extras.getString("username");

        klepetiView =(LinearLayout) findViewById(R.id.klepetiView);

        pogovori= new ArrayList<>();
        naloziKlepeteUporabnika();

    }


    public void naloziKlepeteUporabnika(){
        /**Iz baze pridobimo podatke o klepetih*/
        for(int i=0;i<3;i++){
            pogovori.add(i,new Pogovor(username,"govorec2",new Sporocilo[]{new Sporocilo("posiljatelj",username,"Neka vsebina dolga vsebina"),new Sporocilo("posiljatelj",username,"Neka vsebina dolga vsebina"),new Sporocilo(username,"posiljatelj","Neka vsebina dolga vsebina")},
                    new Oglas("Wifi adapter","pridajam wifi adapter","Čisto novi wifi adapter, prodajam zaradi neuporabe",new byte[10],15,15, Calendar.getInstance(),"random user")));

        }

        for(int i=0;i<3;i++){
            pogovori.add(i,new Pogovor("govorec2",username,new Sporocilo[]{new Sporocilo(username,"posiljatelj","Neka vsebina dolga vsebina"),new Sporocilo("posiljatelj",username,"Neka vsebina dolga vsebina")},
                    new Oglas("Wifi adapter","pridajam wifi adapter","Čisto novi wifi adapter, prodajam zaradi neuporabe",new byte[10],15,15, Calendar.getInstance(),"random user")));

        }

        for(int i=0;i<pogovori.size();i++) {
            final int j = i;
            LinearLayout vmesni = new LinearLayout(this);
            vmesni.setOrientation(LinearLayout.HORIZONTAL);
            vmesni.setId(j);
            if(pogovori.get(j).getGovorec1().toLowerCase().trim().equals(username.toLowerCase().trim()))
                vmesni.setBackgroundColor(Color.CYAN);
            else
                vmesni.setBackgroundColor(Color.LTGRAY);
            vmesni.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300));
            vmesni.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Klepet.this, Chat.class);
                    intent.putExtra("username",username);
                    intent.putExtra("user",pogovori.get(j).getGovorec1());
                    intent.putExtra("pgov2", pogovori.get(j).getGovorec2());
                    intent.putExtra("oglas", pogovori.get(j).getOglas());
                    intent.putExtra("sporocila", pogovori.get(j).getSporocila());
                    startActivity(intent);
                }
            });

            ImageView img = new ImageView(this);
            img.setImageResource(R.drawable.cat);
            img.setLayoutParams(new LinearLayoutCompat.LayoutParams(250, 250));

            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(20, 0, 0, 0);
            layout.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300));

            TextView textOglas = new TextView(this);
            textOglas.setTextColor(Color.BLACK);
            textOglas.setTextSize(20);
            textOglas.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            textOglas.setText(pogovori.get(i).getOglas().getIme());

            TextView textObjavitelj = new TextView(this);
            textObjavitelj.setTextColor(Color.BLACK);
            textObjavitelj.setTextSize(15);
            textObjavitelj.setId(j);
            textObjavitelj.setText("Objavitelj: " + pogovori.get(i).getGovorec2());

            TextView textKratekOpis = new TextView(this);
            textKratekOpis.setTextColor(Color.BLACK);
            textKratekOpis.setTextSize(15);
            textKratekOpis.setText(pogovori.get(i).getOglas().getKratekOpis());

            layout.addView(textObjavitelj);
            layout.addView(textOglas);
            layout.addView(textKratekOpis);

            vmesni.addView(img);
            vmesni.addView(layout);
            klepetiView.addView(vmesni);

        }

    }

    public void klepet(View view){
        Intent intent = new Intent(Klepet.this, Klepet.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }
}
