package com.example.proiect3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class LicitatieAdapter extends BaseAdapter {

    private List<Auction> licitatii;
    private Context context;
    private LayoutInflater inflater;

    public LicitatieAdapter(List<Auction> licitatii, Context context) {
        this.licitatii = licitatii;
        this.context = context;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return licitatii.size();
    }

    @Override
    public Object getItem(int position) {
        return licitatii.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View licitatieView = inflater.inflate(R.layout.item_licitatie,parent,false);
        TextView tvNume=licitatieView.findViewById(R.id.tv_nume);
        TextView tvPret=licitatieView.findViewById(R.id.tv_pret);
        TextView tvOras=licitatieView.findViewById(R.id.tv_oras);

        Auction licitatie=licitatii.get(position);
        tvNume.setText(licitatie.getNumeProdus());
        tvPret.setText(licitatie.getPretPornire());
        tvOras.setText(licitatie.getOras());

        return licitatieView;
    }

    public void removeElement(int position) {
        licitatii.remove(position);
        notifyDataSetChanged();
    }
}
