package com.example.proiect3;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AuctionDAO {

    @Insert
    void insertAuction(Auction auction);

    @Update
    void updateAuction(Auction auction);

    @Delete
    void deleteAuction(Auction auction);

    @Query("SELECT * FROM auction")
    List<Auction> getAllAuctions();

    @Query("SELECT * FROM auction WHERE userId=:id")
    List<Auction> getAuctionsById(int id);

    @Query("SELECT * FROM auction WHERE id=:id")
    Auction getAuctionById(long id);

    @Query("SELECT * FROM auction WHERE categorie=:categorie")
    List<Auction> getAuctionsByCategory(String categorie);
}
