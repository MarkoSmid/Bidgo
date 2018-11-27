package com.example.uporabnik.rps_bidgo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.ParseException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.Locale;

public class PrikaziOglas extends AppCompatActivity {

    Bundle extras;
    LinearLayout mainWindowPo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prikazi_oglas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainWindowPo = (LinearLayout) findViewById(R.id.mainWindowPo);
        extras =getIntent().getExtras();
        try {
            /* o = extras.getParcelable("oglas");
            o.setTrenutnaCena(extras.getInt("int"));
            o.setZacetnaCena(extras.getInt("int"));*/
            final String idDrazba = extras.getString("idDrazba");
            String naziv = extras.getString("naziv");
            String opis = extras.getString("opis");
            String lokacija = extras.getString("lokacija");
            final String idUporabnik = extras.getString("idUporabnik");
            String ime_upo = extras.getString("ime_upo");
            String priimek_upo = extras.getString("priimek_upo");
            String zacetek = extras.getString("zacetek");
            String konec = extras.getString("konec");


            TextView textIme = new TextView(this);
            textIme.setTextColor(Color.BLACK);
            textIme.setTextSize(20);
            textIme.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            textIme.setText("Ime oglasa: "+naziv);

            TextView textCena = new TextView(this);
            textIme.setTextColor(Color.BLACK);
            textCena.setTextSize(20);
            textCena.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            textCena.setText("Trenutna cena: "+25+"€");

            TextView textKratekOpis = new TextView(this);
            textKratekOpis.setTextColor(Color.BLACK);
            textKratekOpis.setTextSize(20);
            textKratekOpis.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            textKratekOpis.setText("Kratek opis: "+opis);

            TextView textZCena = new TextView(this);
            textZCena.setTextColor(Color.BLACK);
            textZCena.setTextSize(20);
            textZCena.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            textZCena.setText("Začetna cena: "+15+"€");

            TextView textOpis = new TextView(this);
            textOpis.setTextColor(Color.BLACK);
            textOpis.setTextSize(20);
            textOpis.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            textOpis.setText("Opis: "+opis);

            TextView textDatumObjave = new TextView(this);
            textDatumObjave.setTextColor(Color.BLACK);
            textDatumObjave.setTextSize(20);
            textDatumObjave.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

            TextView textDatumKonca = new TextView(this);
            textDatumKonca.setTextColor(Color.BLACK);
            textDatumKonca.setTextSize(20);
            textDatumKonca.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            try {
                textDatumKonca.setText("Datum izteka: "+konec);
                textDatumObjave.setText("Datum objave: "+zacetek);
                /*Calendar calZac = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                calZac.setTime(sdf.parse(zacetek));

                String leto = Integer.toString(calZac.get(java.util.Calendar.YEAR));
                String mesec = Integer.toString(calZac.get(java.util.Calendar.MONTH)+1);
                String dan = Integer.toString(calZac.get(java.util.Calendar.DAY_OF_MONTH));
                String h = Integer.toString(calZac.get(java.util.Calendar.HOUR_OF_DAY));
                String m = Integer.toString(calZac.get(java.util.Calendar.MINUTE));
                String s = Integer.toString(calZac.get(java.util.Calendar.SECOND));
                textDatumObjave.setText("Datum objave: " + dan + "." + mesec + "." + leto+", "+h+":"+m+":"+s);

                Calendar calKon = Calendar.getInstance();
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                calKon.setTime(sdf2.parse(konec));

                leto = Integer.toString(calKon.get(java.util.Calendar.YEAR));
                mesec = Integer.toString(calKon.get(java.util.Calendar.MONTH)+1);
                dan = Integer.toString(calKon.get(java.util.Calendar.DAY_OF_MONTH));
                h = Integer.toString(calKon.get(java.util.Calendar.HOUR_OF_DAY));
                m = Integer.toString(calKon.get(java.util.Calendar.MINUTE));
                s = Integer.toString(calKon.get(java.util.Calendar.SECOND));
                textDatumKonca.setText("Konec dražbe: " + dan + "." + mesec + "." + leto+", "+h+":"+m+":"+s);*/
            }catch (Exception ex){
                Toast.makeText(this,"Napaka pri pretvorbi datumov", Toast.LENGTH_LONG).show();
            }

            TextView textObjavil = new TextView(this);
            textObjavil.setTextColor(Color.BLACK);
            textObjavil.setTextSize(20);
            textObjavil.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            textObjavil.setText("Objavil: "+ime_upo+" "+ priimek_upo);

            Button drazi = new Button(this);
            drazi.setTextSize(15);
            drazi.setTextColor(Color.BLACK);
            drazi.setText("Dodaj ponudbo");
            drazi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /**Odpri popup window kjer boš lahko ponudil ponudbo...ponudba se upošteva samo če bo večja od sedajšnje!!!*/
                }
            });
            drazi.setLayoutParams(new LinearLayoutCompat.LayoutParams(450,130));


            mainWindowPo.addView(textIme);
            mainWindowPo.addView(textZCena);
            mainWindowPo.addView(textCena);
            mainWindowPo.addView(textKratekOpis);
            mainWindowPo.addView(textOpis);
            mainWindowPo.addView(textDatumObjave);
            mainWindowPo.addView(textDatumKonca);
            mainWindowPo.addView(textObjavil);
            mainWindowPo.addView(drazi);

        }
        catch (Exception ex){
            Toast.makeText(this,"pri nalaganju je prišlo do napake", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.odjava) {
            /**izvedi odjavo*/
            Intent intent = new Intent(PrikaziOglas.this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
