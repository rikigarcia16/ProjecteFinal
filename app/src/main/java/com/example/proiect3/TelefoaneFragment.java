package com.example.proiect3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class TelefoaneFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_telefoane, container, false);
        Bundle b = this.getArguments();
        int id_user_app = b.getInt("user_id_app",0);
        Log.v("id_user_app", String.valueOf(id_user_app));
        List<Auction> auctions = AuctionDB.getInstance(view.getContext()).auctionDAO().getAuctionsByCategory("Telefoane/Tablete/PC");
        ListView lv_myAuctions = view.findViewById(R.id.lv_licitatii_telefoane);
        Log.v("my_auctions", auctions.toString());
        LicitatieAdapter licitatieAdapter = new LicitatieAdapter(auctions,this.getContext());
        lv_myAuctions.setAdapter(licitatieAdapter);
        lv_myAuctions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(view.getContext(),Bid.class);
                Auction auction=auctions.get(position);
                intent.putExtra("auction_id",auction.getId());
                intent.putExtra("auction_user_id", auction.getUserId());
                intent.putExtra("user_id_app", id_user_app);
                startActivity(intent);
            }
        });
        return view;
    }
}
