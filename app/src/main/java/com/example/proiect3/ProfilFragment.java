package com.example.proiect3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class ProfilFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        Bundle b2 = getArguments();
        int userId = b2.getInt("user_id4");
        List<Auction> auctions = AuctionDB.getInstance(view.getContext()).auctionDAO().getAuctionsById(userId);
        Log.v("user_id4", String.valueOf(userId));
        User user = AuctionDB.getInstance(view.getContext()).userDAO().getById(userId);
        Log.v("user", user.toString());
        TextView tv_nume = view.findViewById(R.id.tv_username);
        TextView tv_email = view.findViewById(R.id.tv_email);
        TextView tv_numar_licitatii = view.findViewById(R.id.tv_numar_licitatii);
        tv_nume.setText(user.getUsername());
        tv_email.setText(user.getEmail());
        tv_numar_licitatii.setText(String.valueOf(auctions.size()));

        Button btn_delete = view.findViewById(R.id.btn_delete_user);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuctionDB.getInstance(view.getContext()).deleteUser(user, new IDbCallback() {
                    @Override
                    public void onSuccess() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.v("delete", "delete_user");
                                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Throwable error) {
                        Log.v("delete", error.getLocalizedMessage());
                    }
                });
            }
        });

        Button btn_update = view.findViewById(R.id.btn_update_user);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), SignupActivity.class);
                intent.putExtra("id_user", userId);
                startActivity(intent);
            }
        });
        return view;
    }
}
