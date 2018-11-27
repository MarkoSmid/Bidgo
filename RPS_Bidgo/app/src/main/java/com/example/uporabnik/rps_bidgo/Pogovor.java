package com.example.uporabnik.rps_bidgo;

/**
 * Created by Uporabnik on 27/10/2018.
 */

public class Pogovor  {
    private String govorec1;
    private String govorec2;
    private Sporocilo[] sporocila;
    private Oglas oglas;

    public Pogovor(String go1, String go2,Sporocilo[] spo,Oglas o){
        govorec1=go1;
        govorec2=go2;
        sporocila=spo;
        oglas=o;
    }

    public Pogovor(){

    }

    public String getGovorec1() {
        return govorec1;
    }

    public String getGovorec2() {
        return govorec2;
    }

    public Oglas getOglas() {
        return oglas;
    }

    public Sporocilo[] getSporocila() {
        return sporocila;
    }

    public void setSporocila(Sporocilo[] sporocila) {
        this.sporocila = sporocila;
    }

    public void setOglas(Oglas oglas) {
        this.oglas = oglas;
    }

    public void setGovorec1(String govorec1) {
        this.govorec1 = govorec1;
    }

    public void setGovorec2(String govorec2) {
        this.govorec2 = govorec2;
    }
}
