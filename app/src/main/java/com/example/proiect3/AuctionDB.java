package com.example.proiect3;

import android.content.Context;
import android.content.res.Resources;

import androidx.room.Room;

import com.example.proiect3.Auction;
import com.example.proiect3.UserDAO;
import com.example.proiect3.auctionDatabase;

public class AuctionDB {
    //clasa asta ne da acces la baza de date
    private static AuctionDB instance;
    private auctionDatabase database;

    private AuctionDB(Context context){
        //initializare baza de date
        database= Room.databaseBuilder(context,auctionDatabase.class,"table_users").allowMainThreadQueries().build();
    }

    public  static AuctionDB getInstance(Context context){
        if(instance == null)
        {
            instance=new AuctionDB(context);
        }
        return instance;
    }

    public UserDAO userDAO(){
        return database.userDAO(); //avem acces la toate metodele de acces la baza de date
    }

    public AuctionDAO auctionDAO(){
        return database.auctionDAO();
    }

    public void insertUser(final User user,final IDbCallback callback){
        new Thread((Runnable)()->{
            userDAO().insertUser(user);
            callback.onSuccess();
        }).start();
    }

    public void updateUser (final User user,final IDbCallback callback){
        new Thread((Runnable)()->{
            userDAO().updateUser(user);
            callback.onSuccess();
        }).start();
    }

    public void deleteUser(final User user,final IDbCallback callback){
        new Thread((Runnable)()->{
            userDAO().deleteUser(user);
            callback.onSuccess();
        }).start();
    }

    public void insertLicitatie(final Auction auction,final IDbCallback callback){
        new Thread((Runnable)()->{
            auctionDAO().insertAuction(auction);
            callback.onSuccess();
        }).start();
    }

    public void updateLicitatie(final Auction auction,final IDbCallback callback){
        new Thread((Runnable)()->{
            auctionDAO().updateAuction(auction);
            callback.onSuccess();
        }).start();
    }

    public void selectLicitatieDupaId(final long id,final IDbCallback callback){
        new Thread((Runnable)()->{
            auctionDAO().getAuctionById(id);
            callback.onSuccess();
        }).start();
    }

    public void deleteLicitatie(final Auction auction,final IDbCallback callback){
        new Thread((Runnable)()->{
            auctionDAO().deleteAuction(auction);
            callback.onSuccess();
        }).start();
    }
}
