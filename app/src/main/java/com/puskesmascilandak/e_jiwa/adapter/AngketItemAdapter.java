package com.puskesmascilandak.e_jiwa.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.puskesmascilandak.e_jiwa.R;
import com.puskesmascilandak.e_jiwa.model.Angket;
import com.puskesmascilandak.e_jiwa.model.DetailCheckUp;

public class AngketItemAdapter extends ArrayAdapter<DetailCheckUp> {


    public AngketItemAdapter(@NonNull Context context) {
        super(context, R.layout.angket_item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.angket_item, null);
        }

        TextView numberTextView = view.findViewById(R.id.number_textview);
        TextView questionTextView = view.findViewById(R.id.question_textview);
        RadioButton yesRb = view.findViewById(R.id.yes_rb);
        RadioButton noRb = view.findViewById(R.id.no_rb);

        final DetailCheckUp detailCheckUp = getItem(position);

        if (detailCheckUp != null) {
            final Angket angket = detailCheckUp.getAngket();

            numberTextView.setText(String.valueOf(angket.getId()));
            questionTextView.setText(angket.getQuestion());

            yesRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        detailCheckUp.setAnswer("Ya");
                        Log.e(String.valueOf(angket.getId()), "onCheckedChanged: "+detailCheckUp.getAnswer() );
                    }
                }
            });

            noRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        detailCheckUp.setAnswer("Tidak");
                        Log.e(String.valueOf(angket.getId()), "onCheckedChanged: "+detailCheckUp.getAnswer() );
                    }
                }
            });
        }

        return view;
    }
}
