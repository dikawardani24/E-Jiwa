package com.puskesmascilandak.e_jiwa.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.puskesmascilandak.e_jiwa.R;
import com.puskesmascilandak.e_jiwa.model.CheckUp;
import com.puskesmascilandak.e_jiwa.model.Pasien;

public class CheckUpItemAdapter extends ArrayAdapter<CheckUp> {
    public CheckUpItemAdapter(@NonNull Context context) {
        super(context, R.layout.check_up_item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.check_up_item, null);
        }

        TextView namaTxt = view.findViewById(R.id.nama_txt);
        TextView scoreTxt = view.findViewById(R.id.score_txt);
        TextView noHpTxt = view.findViewById(R.id.no_hp_txt);
        TextView tglCheckUpTxt = view.findViewById(R.id.tgl_check_up_txt);

        CheckUp checkUp = getItem(position);
        if (checkUp != null) {
            Pasien pasien = checkUp.getPasien();
            namaTxt.setText(pasien.getNama());
            scoreTxt.setText(String.valueOf(checkUp.getScore()));
            noHpTxt.setText(pasien.getNoTelp());
            tglCheckUpTxt.setText(checkUp.getTglCheckUp());
        }

        return view;
    }
}
