package com.example.proiect3;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "auction",
        foreignKeys = @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "userId",onDelete = CASCADE)) //hola
public class Auction {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String numeProdus;;
    private String descriereProdus;;
    //private Bitmap imagine;
    private String categorie;;
    private String pretPornire;
    private String numarTelefon;
    private String oras;
    private int userId;


    public Auction() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNumeProdus() {
        return numeProdus;
    }

    public String getDescriereProdus() {
        return descriereProdus;
    }

//    public Bitmap getImagine() {
//        return imagine;
//    }

    public String getCategorie() {
        return categorie;
    }

    public String getPretPornire() {
        return pretPornire;
    }

    public String getNumarTelefon() {
        return numarTelefon;
    }

    public String getOras() {
        return oras;
    }

    public void setNumeProdus(String numeProdus) {
        this.numeProdus = numeProdus;
    }

    public void setDescriereProdus(String descriereProdus) {
        this.descriereProdus = descriereProdus;
    }

//    public void setImagine(Bitmap imagine) {
//        this.imagine = imagine;
//    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setPretPornire(String pretPornire) {
        this.pretPornire = pretPornire;
    }

    public void setNumarTelefon(String numarTelefon) {
        this.numarTelefon = numarTelefon;
    }

    public void setOras(String oras) {
        this.oras = oras;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Auction(String numeProdus, String descriereProdus,  String categorie, String pretPornire, String numarTelefon, String oras, int userId) {
        this.numeProdus = numeProdus;
        this.descriereProdus = descriereProdus;
        //this.imagine = imagine;
        this.categorie = categorie;
        this.pretPornire = pretPornire;
        this.numarTelefon = numarTelefon;
        this.oras = oras;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Auction{" +
                "id=" + id +
                ", numeProdus='" + numeProdus + '\'' +
                ", descriereProdus='" + descriereProdus + '\'' +
                //", imagine=" + imagine +
                ", categorie='" + categorie + '\'' +
                ", pretPornire='" + pretPornire + '\'' +
                ", numarTelefon='" + numarTelefon + '\'' +
                ", oras='" + oras + '\'' +
                ", userId=" + userId +
                '}';
    }
}
