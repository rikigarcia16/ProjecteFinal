package com.example.proiect3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.nio.BufferUnderflowException;
import java.util.List;

public class LicitatiileMeleFragment  extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_licitatiile_mele, container, false);
        Bundle b2 = getArguments();
        int userId = b2.getInt("user_id");
        List<Auction> auctions = AuctionDB.getInstance(view.getContext()).auctionDAO().getAuctionsById(userId);
        Log.v("user_id2", String.valueOf(userId));
        ListView lv_myAuctions = view.findViewById(R.id.lv_licitatiile_mele);
        Log.v("my_auctions", auctions.toString());
        LicitatieAdapter licitatieAdapter = new LicitatieAdapter(auctions,this.getContext());
        lv_myAuctions.setAdapter(licitatieAdapter);


        lv_myAuctions.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                AuctionDB.getInstance(view.getContext()).auctionDAO().deleteAuction(auctions.get(position));
                selectOption(view.getContext(), auctions, position, licitatieAdapter, id);

                return true;
            }
        });
        return view;
    }

    private void selectOption(Context context, List<Auction> auctions, int position, LicitatieAdapter adapter, long id) {
        final CharSequence[] options = { "Delete", "Update", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Alege o optiune");


        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Delete")) {
                    AuctionDB.getInstance(context).deleteLicitatie(auctions.get(position), new IDbCallback() {
                        @Override
                        public void onSuccess() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.v("delete_licitatie", "ok");
                                }
                            });
                        }

                        @Override
                        public void onFailure(Throwable error) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.v("licitatie",error.getLocalizedMessage());
                                }
                            });
                        }
                    });
                    adapter.removeElement(position);


                } else if (options[item].equals("Update")) {
                    Intent intent = new Intent(context, LicitatieNoua.class);
//                    intent.putExtra("nume", )
                    int idLic=auctions.get(position).getId();
                    int userId=auctions.get(position).getUserId();
                    intent.putExtra("id_lic",idLic);
                    intent.putExtra("user_id3",userId);
                    startActivity(intent);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


}
