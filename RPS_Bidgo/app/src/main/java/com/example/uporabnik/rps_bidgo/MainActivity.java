package com.example.uporabnik.rps_bidgo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    LinearLayout mainWindow;

    public static final String KEY_EMAIL="ePosta";
    public static final String KEY_PASSWORD="geslo";
    public static final String SERVER_IP ="164.8.223.27";

    public static final String LOGIN_URL = "http://164.8.223.27/BidGo/login.php";
    public static final String REGISTRATION_URL = "http://164.8.223.27/BidGo/register.php";
    public static final String DRZAVE_URL = "http://164.8.223.27/BidGo/drzave.php";

    String drzavePB;
    String kategorijePB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainWindow = (LinearLayout) findViewById(R.id.mainWindow);
        loadLogin();
        getDrzave();


    }

    /**Koda za prikaz gradnikov za login*/
    public void loadLogin(){
        mainWindow.removeAllViews();

        /**********************************************/
        LinearLayout vmesni = new LinearLayout(this);
        vmesni.setOrientation(LinearLayout.VERTICAL);
        vmesni.setGravity(Gravity.CENTER_HORIZONTAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(700,130);
        params.setMargins(10,10,10,10);
        LinearLayout.LayoutParams paramsBtn = new LinearLayout.LayoutParams(500,130);
        params.setMargins(10,10,10,10);

        final EditText username = new EditText(this);
        username.setLayoutParams(params);
        username.setHint("Uporabniško ime");

        final EditText password = new EditText(this);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        password.setLayoutParams(params);
        password.setHint("Geslo");

        Button btnLogin = new Button(this);
        btnLogin.setText("Prijava");
        btnLogin.setLayoutParams(paramsBtn);
        btnLogin.setTextColor(Color.WHITE);
        btnLogin.setBackgroundResource(R.drawable.themeback);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!username.getText().toString().trim().equals("") && !password.getText().toString().trim().equals("")) {
                        userLogin(username.getText().toString().trim(), password.getText().toString().trim());
                    } else {
                        Toast.makeText(getApplicationContext(), "Izpolnite vsa polja!", Toast.LENGTH_LONG).show();
                    }
                }catch(Exception ex){
                    Toast.makeText(getApplicationContext(), "Napaka pri prijavi", Toast.LENGTH_LONG).show();
                }
            }
        });

        TextView registrationText = new TextView(this);
        registrationText.setText("Še niste uporabnik? Registrirajte se tukaj!");
        registrationText.setTextSize(20);
        registrationText.setTextColor(Color.BLACK);
        registrationText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        registrationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadRegistracija();
            }
        });
        /**********************************************/

        vmesni.addView(username);
        vmesni.addView(password);
        vmesni.addView(btnLogin);
        vmesni.addView(registrationText);

        mainWindow.addView(vmesni);

    }

    private void userLogin(final String email,final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.trim().equals("Nepravilna pošta ali geslo")){
                            openProfile(response);
                        }else{
                            Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"Ni povezave do interneta.", Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_EMAIL,email);
                map.put(KEY_PASSWORD,password);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void userRegistration(final String ime,final String priimek, final String tel,final String ePosta,final String geslo,final String drzava,final String posta,final String kraj,final String ulica,final String hisna_st ) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTRATION_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("Registracija uspesna!")){
                            /**registracija uspešna*/
                            Toast.makeText(MainActivity.this, "Registracija uspešna", Toast.LENGTH_LONG).show();
                            loadLogin();
                        }else{
                            Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"Ni povezave do interneta.", Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("ime",ime);
                map.put("priimek",priimek);
                map.put("tel",tel);
                map.put("ePosta",ePosta);
                map.put("geslo",geslo);
                map.put("drzava",drzava);
                map.put("posta",posta);
                map.put("kraj",kraj);
                map.put("ulica",ulica);
                map.put("hisna_st",hisna_st);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void openProfile(String params){
        Intent intent = new Intent(MainActivity.this,Raziskovanje.class);
        intent.putExtra("userData", params);
        startActivity(intent);
    }

    /**Koda za prikaz gradnikov za registracijo*/
    public void loadRegistracija(){
        mainWindow.removeAllViews();

        /**********************************************/
        LinearLayout vmesni = new LinearLayout(this);
        vmesni.setOrientation(LinearLayout.VERTICAL);
        vmesni.setGravity(Gravity.CENTER_HORIZONTAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(700,130);
        params.setMargins(10,10,10,10);
        LinearLayout.LayoutParams paramsBtn = new LinearLayout.LayoutParams(500,130);
        params.setMargins(10,10,10,10);

        final EditText ime = new EditText(this);
        ime.setLayoutParams(params);
        ime.setInputType(InputType.TYPE_CLASS_TEXT);
        ime.setHint("Ime");

        final EditText priimek = new EditText(this);
        priimek.setLayoutParams(params);
        priimek.setInputType(InputType.TYPE_CLASS_TEXT);
        priimek.setHint("Priimek");

        final EditText telefon = new EditText(this);
        telefon.setLayoutParams(params);
        telefon.setInputType(InputType.TYPE_CLASS_PHONE);
        telefon.setHint("Telefon (xxx-xxx-xxx)");

        final EditText email = new EditText(this);
        email.setLayoutParams(params);
        email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        email.setHint("E-mail naslov");

        final EditText password = new EditText(this);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        password.setLayoutParams(params);
        password.setHint("Geslo");

        final Spinner drzave = new Spinner(this);
        String[] drzaveString = drzavePB.trim().split(","); //,"Bosna","Hrvaška","Nemčija","Avstrija"};/**Se preberejo iz apija ob loudu*/
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, drzaveString);
        drzave.setLayoutParams(params);
        drzave.setAdapter(adapter);

        final EditText posta = new EditText(this);
        posta.setLayoutParams(params);
        posta.setInputType(InputType.TYPE_CLASS_NUMBER);
        posta.setHint("Poštna številka");

        final EditText kraj = new EditText(this);
        kraj.setLayoutParams(params);
        kraj.setInputType(InputType.TYPE_CLASS_TEXT);
        kraj.setHint("Kraj");

        final EditText ulica = new EditText(this);
        ulica.setLayoutParams(params);
        ulica.setInputType(InputType.TYPE_CLASS_TEXT);
        ulica.setHint("Ulica");

        final EditText hisna_st = new EditText(this);
        hisna_st.setLayoutParams(params);
        hisna_st.setInputType(InputType.TYPE_CLASS_NUMBER);
        hisna_st.setHint("Hišna številka");

        Button btnRegistracija = new Button(this);
        btnRegistracija.setText("Registracija");
        btnRegistracija.setLayoutParams(paramsBtn);
        btnRegistracija.setTextColor(Color.WHITE);
        btnRegistracija.setBackgroundResource(R.drawable.themeback);
        btnRegistracija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //drzave.getSelectedItem().toString();
                if(ime.getText().toString().trim().equals("") && priimek.getText().toString().trim().equals("")  && telefon.getText().toString().trim().equals("") && email.getText().toString().trim().equals("") && password.getText().toString().trim().equals("") &&posta.getText().toString().trim().equals("") &&kraj.getText().toString().trim().equals("") &&ulica.getText().toString().trim().equals("") &&hisna_st.getText().toString().trim().equals("")){
                    try {
                        Integer.parseInt(posta.getText().toString().trim());
                        Integer.parseInt(hisna_st.getText().toString().trim());

                        if(validEmail(email.getText().toString().trim())) {
                            if(telefon.getText().toString().trim().matches("[0-9][0-9][0-9]"+"-"+"[0-9][0-9][0-9]"+"-"+"[0-9][0-9][0-9]") &&!(telefon.getText().toString().trim().equals("000-000-000"))) {

                                if(posta.getText().toString().trim().length() ==4 && !(posta.getText().toString().trim().equals("0000"))) {

                                    if(Integer.parseInt(hisna_st.getText().toString().trim()) >0) {
                                        userRegistration(ime.getText().toString().trim(), priimek.getText().toString().trim(), telefon.getText().toString().trim(),
                                                email.getText().toString().trim(), password.getText().toString().trim(), drzave.getSelectedItem().toString(), posta.getText().toString().trim(),
                                                kraj.getText().toString().trim(), ulica.getText().toString().trim(), hisna_st.getText().toString().trim());
                                    }else{
                                        Toast.makeText(MainActivity.this,"Neustrezna hišna številka.", Toast.LENGTH_LONG ).show();
                                    }
                                }else{
                                    Toast.makeText(MainActivity.this,"Neustrezna poštna številka.", Toast.LENGTH_LONG ).show();
                                }
                            }else {
                                Toast.makeText(MainActivity.this,"Neustrezna telefonska številka.", Toast.LENGTH_LONG ).show();
                            }
                        }else {
                            Toast.makeText(MainActivity.this,"Neustrezen E-mail.", Toast.LENGTH_LONG ).show();
                        }
                    }catch (Exception ex){
                        Toast.makeText(MainActivity.this,"Vpisani podatki niso pravilni.", Toast.LENGTH_LONG ).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this,"Za registracijo morate izpolniti vsa polja.", Toast.LENGTH_LONG ).show();
                }

            }
        });

        TextView loginText = new TextView(this);
        loginText.setText("Nazaj na prijavo");
        loginText.setTextSize(20);
        loginText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        loginText.setTextColor(Color.BLACK);
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLogin();
            }
        });
        /**********************************************/

        vmesni.addView(ime);
        vmesni.addView(priimek);
        vmesni.addView(telefon);
        vmesni.addView(email);
        vmesni.addView(password);
        vmesni.addView(drzave);
        vmesni.addView(posta);
        vmesni.addView(kraj);
        vmesni.addView(ulica);
        vmesni.addView(hisna_st);
        vmesni.addView(btnRegistracija);
        vmesni.addView(loginText);

        ScrollView scroller = new ScrollView(this);
        scroller.addView(vmesni);

        mainWindow.addView(scroller);
    }

    public boolean validEmail(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void getDrzave() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DRZAVE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("V podatkovni bazi ni podatkov")){
                            drzavePB="";
                        }else{
                            drzavePB=response;
                        }
                        //Toast.makeText(MainActivity.this,response, Toast.LENGTH_LONG ).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"Ni povezave do interneta.", Toast.LENGTH_LONG ).show();
                    }
                }){
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getKategorije() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DRZAVE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("V podatkovni bazi ni podatkov")){
                            kategorijePB="";
                        }else{
                            kategorijePB=response;
                        }
                        //Toast.makeText(MainActivity.this,response, Toast.LENGTH_LONG ).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"Ni povezave do interneta.", Toast.LENGTH_LONG ).show();
                    }
                }){
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
