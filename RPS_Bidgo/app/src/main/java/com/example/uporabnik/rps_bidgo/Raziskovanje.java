package com.example.uporabnik.rps_bidgo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

public class Raziskovanje extends AppCompatActivity {

    LinearLayout oglasiView;
    String userData;
    Intent intent;
    ArrayList<Oglas> vsiOglasi;

    String JsonString;
    JSONArray jsonArray;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raziskovanje);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        intent = getIntent();
        userData = intent.getStringExtra("userData");

        oglasiView=(LinearLayout) findViewById(R.id.oglasiView);
        vsiOglasi = new ArrayList<>();
        napolniArrayListOglase();
        prikaziDrazbe(false,"");

        Toast.makeText(getApplicationContext(),userData,Toast.LENGTH_LONG).show();

        final EditText filterString =(EditText) findViewById(R.id.filterSearch);
        filterString.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                prikaziDrazbe(true,filterString.getText().toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
            Intent intent = new Intent(Raziskovanje.this, MainActivity.class);
            startActivity(intent);
        }else if(id==R.id.osvezi){
            prikaziDrazbe(false,"");
        }

        return super.onOptionsItemSelected(item);
    }
    public void napolniArrayListOglase(){
        for(int i=0;i<30;i++){
            vsiOglasi.add(new Oglas("ime","pridajam banane","dolgi Opis",new byte[10],15,15, Calendar.getInstance(),"random user"));
        }
        vsiOglasi.add(new Oglas("Wifi adapter","pridajam wifi adapter","Čisto novi wifi adapter, prodajam zaradi neuporabe",new byte[10],15,15, Calendar.getInstance(),"random user"));
    }

    public void prikaziDrazbe(boolean filtriraj,String filter) {
        oglasiView.removeAllViews();
        for (int i = 0; i < vsiOglasi.size(); i++) {
            if (!userData.trim().toLowerCase().equals(vsiOglasi.get(i).getObjavitelj())) {
                final int j = i;
                LinearLayout vmesni = new LinearLayout(this);
                vmesni.setOrientation(LinearLayout.HORIZONTAL);
                vmesni.setId(j);
                vmesni.setBackgroundColor(Color.CYAN);
                vmesni.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300));
                vmesni.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String method ="prikazi_drazbo";
                        Raziskovanje.prikaziDrazboZId backgroundTask = new Raziskovanje.prikaziDrazboZId(Raziskovanje.this);
                        backgroundTask.execute(method, "1");

                    }
                });


                ImageView img = new ImageView(this);
                img.setImageResource(R.drawable.cat);
                img.setLayoutParams(new LinearLayoutCompat.LayoutParams(250, 250));

                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(20, 0, 0, 0);
                layout.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300));

                TextView textIme = new TextView(this);
                textIme.setTextColor(Color.BLACK);
                textIme.setTextSize(20);
                textIme.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                textIme.setText(vsiOglasi.get(i).getIme());

                TextView textCena = new TextView(this);
                textCena.setTextColor(Color.BLACK);
                textCena.setTextSize(15);
                textCena.setId(j);
                textCena.setText("Trenutna cena: " + vsiOglasi.get(i).getTrenutnaCena() + "€");

                TextView textKratekOpis = new TextView(this);
                textKratekOpis.setTextColor(Color.BLACK);
                textKratekOpis.setTextSize(15);
                textKratekOpis.setText(vsiOglasi.get(i).getKratekOpis());

                vmesni.addView(img);
                layout.addView(textIme);
                layout.addView(textCena);
                layout.addView(textKratekOpis);

                vmesni.addView(layout);
                if (filtriraj) {
                    if (vsiOglasi.get(i).getIme().toLowerCase().contains(filter.toLowerCase())) {
                        oglasiView.addView(vmesni);
                    } else {
                        Toast.makeText(this, "Noben oglas ne ustreza iskani besedni zvezi", Toast.LENGTH_LONG).show();
                    }
                } else {
                    oglasiView.addView(vmesni);
                }
            }
        }

    }

    public void filtrirajOglasePoImenu(View view){
        Toast.makeText(this,"Filtriram podatkee", Toast.LENGTH_LONG).show();
        //oglasiView.removeAllViews();
    }

    public void raziskovanje(View view){
        Intent intent = new Intent(Raziskovanje.this, Raziskovanje.class);
        intent.putExtra("username",userData);
        startActivity(intent);
    }

    public void klepet(View view){
        Intent intent = new Intent(Raziskovanje.this, Klepet.class);
        intent.putExtra("username",userData);
        startActivity(intent);
    }
    public void objavi(View view){
        Intent intent = new Intent(Raziskovanje.this, ObjaviOglas.class);
        intent.putExtra("userData",userData);
        startActivity(intent);
    }
    public void moji_oglasi(View view){
        Intent intent = new Intent(Raziskovanje.this, mojRacun.class);
        intent.putExtra("username",userData);
        intent.putExtra("tip_dogodka","moji_oglasi");
        startActivity(intent);

    }
    public void moj_racun(View view){
        Intent intent = new Intent(Raziskovanje.this, mojRacun.class);
        intent.putExtra("username",userData);
        intent.putExtra("tip_dogodka","moji_podatki");
        startActivity(intent);
    }

    /**Prikaz podrobnosti dražbe*/
    public class prikaziDrazboZId extends AsyncTask<String,Void,String> {
        AlertDialog alertDialog;
        Context ctx;
        String show_data_url;
        String Json_string;
        String ID_D;

        prikaziDrazboZId(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            show_data_url = "http://164.8.223.27/BidGo/prikaz_izdelka.php";
        }


        @Override
        protected String doInBackground(String... params) {
            String method = params[0];
            if (method.equals("prikazi_drazbo")) {

                ID_D = params[1];

                URL url;
                try {
                    url = new URL(show_data_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS));
                    String data = URLEncoder.encode("ID_D", "UTF-8") + "=" + URLEncoder.encode(ID_D, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();
                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((Json_string = bufferedReader.readLine()) != null) {
                        stringBuilder.append(Json_string + "\n");
                    }

                    bufferedReader.close();
                    IS.close();
                    httpURLConnection.disconnect();
                    return stringBuilder.toString().trim();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... params) {
            super.onProgressUpdate();
        }

        @Override
        protected void onPostExecute(String result) {
            JsonString = result;
            Toast.makeText(ctx, "Executed", Toast.LENGTH_LONG).show();
            if (JsonString.trim().equals("Neveljaven id")) {
                Toast.makeText(ctx, "Te dražbe ni mogoče pokazati.", Toast.LENGTH_LONG).show();
            } else {
                /**Uspešno dobljeni podatki*/
                prikaziDrazbo();
            }
        }
    }

    public void prikaziDrazbo(){
        try {
            jsonObject = new JSONObject(JsonString);
            jsonArray = jsonObject.getJSONArray("drazba");

            int count = 0;
            while (count < jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);
                final String idDrazba = JO.getString("idDrazba");
                String naziv = JO.getString("naziv");
                String opis = JO.getString("opis");
                String lokacija = JO.getString("lokacija");
                final String idUporabnik = JO.getString("idUporabnik");
                String ime_upo = JO.getString("ime_upo");
                String priimek_upo = JO.getString("priimek_upo");
                String zacetek = JO.getString("zacetek");
                String konec = JO.getString("konec");

                Intent intent = new Intent(Raziskovanje.this, PrikaziOglas.class);
                intent.putExtra("idDrazba", idDrazba);
                intent.putExtra("naziv", naziv);
                intent.putExtra("opis", opis);
                intent.putExtra("lokacija", lokacija);
                intent.putExtra("idUporabnik", idUporabnik);
                intent.putExtra("ime_upo", ime_upo);
                intent.putExtra("priimek_upo", priimek_upo);
                intent.putExtra("zacetek", zacetek);
                intent.putExtra("konec", konec);
                startActivity(intent);
                count++;
            }

        } catch (Exception ex) {
            //Toast.makeText(getApplicationContext(), "Napaka pri nalaganju podatkov za izbrano dražbo", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    /**Prikaz vseh dražb, filtrirane */
    public class prikazVsehDrazb extends AsyncTask<String,Void,String> {
        AlertDialog alertDialog;
        Context ctx;
        String show_data_url;
        String Json_string;
        String filtri;

        prikazVsehDrazb(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            show_data_url = "http://164.8.223.27/BidGo/prikaz_vsehDrazb.php";
        }


        @Override
        protected String doInBackground(String... params) {
            String method = params[0];
            if (method.equals("prikazi_vse_drazbe")) {

                filtri = params[1];

                URL url;
                try {
                    url = new URL(show_data_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS));
                    String data = URLEncoder.encode("filter", "UTF-8") + "=" + URLEncoder.encode(filtri, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();
                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((Json_string = bufferedReader.readLine()) != null) {
                        stringBuilder.append(Json_string + "\n");
                    }

                    bufferedReader.close();
                    IS.close();
                    httpURLConnection.disconnect();
                    return stringBuilder.toString().trim();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... params) {
            super.onProgressUpdate();
        }

        @Override
        protected void onPostExecute(String result) {
            JsonString = result;
            Toast.makeText(ctx, "Executed", Toast.LENGTH_LONG).show();
            if (JsonString.trim().equals("Ni podatkov")) {
                Toast.makeText(ctx, "Ni podatkov.", Toast.LENGTH_LONG).show();
            } else {
                /**Uspešno dobljeni podatki*/
                prikaziVseDrazbe();
            }
        }
    }

    public void prikaziVseDrazbe(){
        try {
            oglasiView.removeAllViews();
            jsonObject = new JSONObject(JsonString);
            jsonArray = jsonObject.getJSONArray("drazbe");

            int count = 0;
            while (count < jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);
                final String idDrazba = JO.getString("idDrazba");
                String naziv = JO.getString("naziv");
                String kratekOpis = JO.getString("opis");
                String trenutnaCena = JO.getString("trenutna_cena");
                String konec_drazbe = JO.getString("konec");
                String slika =JO.getString("slika");

                LinearLayout vmesni = new LinearLayout(this);
                vmesni.setOrientation(LinearLayout.HORIZONTAL);
                vmesni.setId(Integer.parseInt(idDrazba));
                vmesni.setBackgroundColor(Color.CYAN);
                vmesni.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300));
                vmesni.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String method ="prikazi_drazbo";
                        Raziskovanje.prikaziDrazboZId backgroundTask = new Raziskovanje.prikaziDrazboZId(Raziskovanje.this);
                        backgroundTask.execute(method, idDrazba);

                    }
                });

                ImageView img = new ImageView(this);
                img.setImageResource(R.drawable.cat);
                img.setLayoutParams(new LinearLayoutCompat.LayoutParams(250, 250));

                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(20, 0, 0, 0);
                layout.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300));

                TextView textIme = new TextView(this);
                textIme.setTextColor(Color.BLACK);
                textIme.setTextSize(20);
                textIme.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                textIme.setText(naziv);

                TextView textCena = new TextView(this);
                textCena.setTextColor(Color.BLACK);
                textCena.setTextSize(15);
                textCena.setText("Trenutna cena: " + trenutnaCena + "€");

                TextView textKratekOpis = new TextView(this);
                textKratekOpis.setTextColor(Color.BLACK);
                textKratekOpis.setTextSize(15);
                textKratekOpis.setText(kratekOpis);

                TextView textDatum_izteka = new TextView(this);
                textDatum_izteka.setTextColor(Color.BLACK);
                textDatum_izteka.setTextSize(15);
                textDatum_izteka.setText("Konec dražbe: "+konec_drazbe);

                vmesni.addView(img);
                layout.addView(textIme);
                layout.addView(textCena);
                layout.addView(textKratekOpis);
                layout.addView(textDatum_izteka);

                vmesni.addView(layout);

                oglasiView.addView(vmesni);
                count++;
            }

        } catch (Exception ex) {
            //Toast.makeText(getApplicationContext(), "Napaka pri nalaganju podatkov za izbrano dražbo", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }




}
