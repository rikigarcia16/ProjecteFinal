package com.example.proiect3;

import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {User.class,Auction.class},version = 1,exportSchema = false)
public abstract class auctionDatabase extends RoomDatabase {
    //legatura intre baza de date si aplicatia noastra
    public abstract UserDAO userDAO ();
    public abstract AuctionDAO auctionDAO();
}
