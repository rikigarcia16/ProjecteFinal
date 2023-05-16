package com.example.proiect3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Bid extends AppCompatActivity {
    private TextView tv_nume;
    private TextView tv_descriere;
    private TextView tv_categorie;
    private TextView tv_pret;
    private TextView tv_oras;
    private EditText et_pretBid;
    private Button btn_bid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bid_activity);
        initViews();
        int userId=getIntent().getIntExtra("auction_user_id",0); //id ul userului de la licitatie
        Log.v("user_bid", String.valueOf(userId));
        int auctionId=getIntent().getIntExtra("auction_id",0);
        Auction auction = AuctionDB.getInstance(this).auctionDAO().getAuctionById(auctionId);
        tv_nume.setText(auction.getNumeProdus());
        tv_descriere.setText(auction.getDescriereProdus());
        tv_categorie.setText(auction.getCategorie());
        tv_pret.setText(auction.getPretPornire());
        tv_oras.setText(auction.getOras());
        int id_user = getIntent().getIntExtra("user_id_app", 0); //id-ul userului conectat
        Log.v("user_bid", String.valueOf(id_user));
        if(id_user == userId) {
            btn_bid.setEnabled(false);
            Toast.makeText(this, "Nu puteti licita pentru propriile postari!", Toast.LENGTH_SHORT).show();
        }
        btn_bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_pretBid.getText().toString().isEmpty()){
                    Toast.makeText(v.getContext(),"Completati pretul dumneavoastra",Toast.LENGTH_LONG).show();
                }
                else if (Integer.parseInt(et_pretBid.getText().toString()) <= Integer.parseInt(tv_pret.getText().toString())) {
                    Toast.makeText(v.getContext(),"Introduceti un pret mai mare",Toast.LENGTH_LONG).show();
                }
                else {
                    auction.setPretPornire(et_pretBid.getText().toString());
                    AuctionDB.getInstance(v.getContext()).updateLicitatie(auction, new IDbCallback() {
                        @Override
                        public void onSuccess() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                                    intent.putExtra("categorie", auction.getCategorie());
                                    intent.putExtra("user_id", id_user);
                                    startActivity(intent);

                                }
                            });
                        }

                        @Override
                        public void onFailure(Throwable error) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.v("update",error.getLocalizedMessage());
                                }
                            });
                        }
                    });
                }
            }
        });

    }

    private void initViews() {
        tv_nume=findViewById(R.id.tv_numeBid);
        tv_descriere=findViewById(R.id.tv_descriereBid);
        tv_categorie=findViewById(R.id.tv_categorieBid);
        tv_pret=findViewById(R.id.tv_pretBid);
        tv_oras=findViewById(R.id.tv_orasBid);
        et_pretBid=findViewById(R.id.et_pretBid);
        btn_bid=findViewById(R.id.btn_bid);
    }
}
