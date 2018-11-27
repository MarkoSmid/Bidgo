package com.example.uporabnik.rps_bidgo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Uporabnik on 25/10/2018.
 */

public class Oglas implements Parcelable {

    private String ime;
    private String kratekOpis;
    private String opis;
    private byte[] slika;
    private int zacetnaCena;
    private int trenutnaCena;
    private Calendar datumObjave;
    private String objavitelj;

    public Oglas (String i,String ko, String o,byte[] s,int zc,int tc,Calendar cal,String ob){
        ime =i;
        kratekOpis=ko;
        opis =o;
        slika=s;
        zacetnaCena=zc;
        trenutnaCena=tc;
        datumObjave=cal;
        objavitelj=ob;
    }

    public Oglas(){

    }

    private Oglas(Parcel in) {
        ime = in.readString();
        kratekOpis = in.readString();
        opis = in.readString();
        //slika = new byte[in.readInt()];
        //in.readByteArray(slika);
        zacetnaCena = in.readInt();
        trenutnaCena = in.readInt();
        datumObjave = (Calendar)in.readSerializable();
        objavitelj=in.readString();
    }

    public String getKratekOpis() {
        return kratekOpis;
    }

    public Calendar getDatumObjave() {
        return datumObjave;
    }

    public int getTrenutnaCena() {
        return trenutnaCena;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ime);
        dest.writeString(kratekOpis);
        //dest.writeInt(slika.length);
        //dest.writeByteArray(slika);
        dest.writeString(opis);
        dest.writeInt(zacetnaCena);
        dest.writeInt(trenutnaCena);
        dest.writeSerializable(datumObjave);
        dest.writeString(objavitelj);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Oglas> CREATOR = new Parcelable.Creator<Oglas>() {
        @Override
        public Oglas createFromParcel(Parcel source) {
            return new Oglas(source);
        }

        @Override
        public Oglas[] newArray(int size) {
            return new Oglas[size];
        }
    };


    public int getZacetnaCena() {
        return zacetnaCena;
    }

    public String getIme() {
        return ime;
    }

    public String getOpis() {
        return opis;
    }

    public byte[] getSlika() {
        return slika;
    }

    public String getObjavitelj() {
        return objavitelj;
    }
    public void setDatumObjave(Calendar datumObjave) {
        this.datumObjave = datumObjave;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public void setSlika(byte[] slika) {
        this.slika = slika;
    }

    public void setTrenutnaCena(int trenutnaCena) {
        this.trenutnaCena = trenutnaCena;
    }

    public void setZacetnaCena(int zacetnaCena) {
        this.zacetnaCena = zacetnaCena;
    }

    public void setKratekOpis(String kratekOpis) {
        this.kratekOpis = kratekOpis;
    }

    public void setObjavitelj(String objavitelj) {
        this.objavitelj = objavitelj;
    }
}
