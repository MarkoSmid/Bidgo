package com.example.uporabnik.rps_bidgo;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class ObjaviOglas extends AppCompatActivity {

    String userData;
    LinearLayout mainObjaviOglas;
    ScrollView scrollViewMain;

    LinearLayout galerija;
    HorizontalScrollView galerijaScroll;

    String naziv;
    String kategorija;
    ArrayList<Bitmap> slike;
    String daljsiOpis;
    Calendar zacetekDrazbe ;//=Calendar.getInstance(); v loudu
    Calendar konecDrazbe;
    int zacetnaCena;
    String podatki[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objavi_oglas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainObjaviOglas =(LinearLayout) findViewById(R.id.mainObjaviOglas);
        scrollViewMain = new ScrollView(this);
        mainObjaviOglas.addView(scrollViewMain);

        galerijaScroll = new HorizontalScrollView(this);

        galerija = new LinearLayout(this);
        galerija.setOrientation(LinearLayout.HORIZONTAL);

        Bundle extras = getIntent().getExtras();
        userData =extras.getString("userData");

        podatki = userData.split(",");
        Toast.makeText(this,podatki[2], Toast.LENGTH_LONG).show();
        slike = new ArrayList<>();
        konecDrazbe =Calendar.getInstance();
        ObjavaOglasa();
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
            Intent intent = new Intent(ObjaviOglas.this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void ObjavaOglasa(){
        LinearLayout vmesni = new LinearLayout(this);
        vmesni.setOrientation(LinearLayout.VERTICAL);
        vmesni.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));

        EditText imeIzdelka = new EditText(this);
        imeIzdelka.setHint("Kaj prodajate?");

        Spinner kategorija = new Spinner(this);
        String[] kategorije = new String[]{"Avto/Moto","Elektronike","Dom in vrt","Gospodinjstvo","Kmetijstvo","ostale storitve"};/**Se preberejo iz apija ob loudu*/
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, kategorije);
        kategorija.setAdapter(adapter);

        ImageView imgNaslovna = new ImageView(this);
        imgNaslovna.setImageResource(R.drawable.cat);
        imgNaslovna.setLayoutParams(new LinearLayoutCompat.LayoutParams(300,300));
        imgNaslovna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

        EditText opisIzdelka = new EditText(this);
        opisIzdelka.setHint("Daljši opis");

        EditText lokacijaProdaje = new EditText(this);
        lokacijaProdaje.setHint("Lokacija");

        EditText zacetnaCena = new EditText(this);
        zacetnaCena.setHint("Zacetna cena");

        Button btnKonecDrazbe = new Button(this);
        btnKonecDrazbe.setText("konec dražbe");
        btnKonecDrazbe.setLayoutParams(new LinearLayoutCompat.LayoutParams(400,150));
        btnKonecDrazbe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setKonecDrazbe();
            }
        });

        Button bntDodajDrazbo = new Button(this);
        bntDodajDrazbo.setText("Dodaj dražbo");
        bntDodajDrazbo.setLayoutParams(new LinearLayoutCompat.LayoutParams(400,150));
        bntDodajDrazbo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**Preverimo če so vpisani podatki pravilno vnešeni ter dodamo oglas preko APIja*/
            }
        });


        vmesni.addView(imeIzdelka);
        vmesni.addView(kategorija);
        galerija.addView(imgNaslovna);
        galerijaScroll.addView(galerija);
        vmesni.addView(galerijaScroll);
        vmesni.addView(opisIzdelka);
        vmesni.addView(lokacijaProdaje);
        vmesni.addView(btnKonecDrazbe);
        vmesni.addView(zacetnaCena);
        vmesni.addView(bntDodajDrazbo);
        //datum objave se dobi ko se pošlje objava


        scrollViewMain.addView(vmesni);
    }

    public void setKonecDrazbe(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this,android.R.style.Theme_Material_Dialog_Alert);
        builder.setTitle("Nastavi konec dražbe");

        // Set up the input
        ScrollView scrollView = new ScrollView(this);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final DatePicker datePicker = new DatePicker(this);
        final TimePicker timePicker = new TimePicker(this);

        layout.addView(datePicker);
        layout.addView(timePicker);

        scrollView.addView(layout);

        builder.setView(scrollView);

        builder.setPositiveButton("Nastavi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //setKonec dražbe
                //preverjaš če je datum večji kot 2 dni ter ni manjši kot 2 ure
                konecDrazbe.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),timePicker.getHour(), timePicker.getMinute(), 0);

            }
        });
        builder.setNegativeButton("Prekliči", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Izberi");
        String[] pictureDialogItems = {
                "Izberi iz galerije",
                "Zajemi novo fotografijo" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, 1);
    }
    private void takePhotoFromCamera() {/**Še ne dela*/
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Bitmap thumbnail = (Bitmap) imageReturnedIntent.getExtras().get("data");
                    ImageView img = new ImageView(this);
                    img.setImageResource(R.drawable.cat);
                    //img.setScaleType(ImageView.ScaleType.FIT_XY);
                    img.setLayoutParams(new LinearLayoutCompat.LayoutParams(300,300));
                    img.setImageBitmap(thumbnail);
                    slike.add(thumbnail);

                    galerija.addView(img);
                }
                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    ImageView img = new ImageView(this);
                    img.setImageResource(R.drawable.cat);
                    //img.setScaleType(ImageView.ScaleType.FIT_XY);
                    img.setLayoutParams(new LinearLayoutCompat.LayoutParams(300,300));
                    img.setImageURI(selectedImage);
                    slike.add(((BitmapDrawable)img.getDrawable()).getBitmap());

                    galerija.addView(img);
                }
                break;
        }
    }

}
