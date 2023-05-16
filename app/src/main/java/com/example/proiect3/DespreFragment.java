package com.example.proiect3;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DespreFragment extends Fragment {

    TextView tv_contact;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_despre, container, false);
        tv_contact=view.findViewById(R.id.tv_contact);
        DownloadContactManager.getInstance().getDespre(new IContactResult() {
            @Override
            public void onSuccess(ArrayList<String> list) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_contact.setText(list.toString());
                    }
                });

//

            }

            @Override
            public void onFailure(Throwable throwable) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(view.getContext(),throwable.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }
                });

            }


        });
        return view;
    }
}
