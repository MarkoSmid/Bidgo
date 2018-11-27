package com.example.uporabnik.rps_bidgo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Uporabnik on 27/10/2018.
 */

public class Sporocilo implements Parcelable {

    private String posiljatelj;
    private String prejemnik;
    private String vsebina;

    public Sporocilo(String po, String pr,String vs){
        posiljatelj=po;
        prejemnik=pr;
        vsebina=vs;
    }


    private Sporocilo(Parcel in){
        posiljatelj=in.readString();
        prejemnik=in.readString();
        vsebina=in.readString();

    }


    public String getPosiljatelj() {
        return posiljatelj;
    }

    public String getPrejemnik() {
        return prejemnik;
    }

    public String getVsebina() {
        return vsebina;
    }

    public void setPosiljatelj(String posiljatelj) {
        this.posiljatelj = posiljatelj;
    }

    public void setPrejemnik(String prejemnik) {
        this.prejemnik = prejemnik;
    }

    public void setVsebina(String vsebina) {
        this.vsebina = vsebina;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posiljatelj);
        dest.writeString(prejemnik);
        dest.writeString(vsebina);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Sporocilo> CREATOR = new Parcelable.Creator<Sporocilo>() {
        @Override
        public Sporocilo createFromParcel(Parcel source) {
            return new Sporocilo(source);
        }

        @Override
        public Sporocilo[] newArray(int size) {
            return new Sporocilo[size];
        }
    };


}
