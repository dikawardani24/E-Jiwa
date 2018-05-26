package com.puskesmascilandak.e_jiwa.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.puskesmascilandak.e_jiwa.R;
import com.puskesmascilandak.e_jiwa.adapter.AngketItemAdapter;
import com.puskesmascilandak.e_jiwa.model.Angket;
import com.puskesmascilandak.e_jiwa.model.CheckUp;
import com.puskesmascilandak.e_jiwa.model.DetailCheckUp;
import com.puskesmascilandak.e_jiwa.model.Pasien;
import com.puskesmascilandak.e_jiwa.model.Petugas;
import com.puskesmascilandak.e_jiwa.service.AngketDbService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CheckUpActivity extends AppCompatActivity {
    private CheckUp checkUp;
    private List<DetailCheckUp> detailCheckUps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_up);

        AngketDbService angketDbService = new AngketDbService(this);
        List<Angket> angkets = angketDbService.getAll();
        detailCheckUps = new ArrayList<>();

        checkUp = new CheckUp();
        Serializable pasienSerialized = getIntent().getSerializableExtra("pasien");
        if (pasienSerialized != null) {
            Pasien pasien = (Pasien) pasienSerialized;
            checkUp.setPasien(pasien);
        }

        Serializable petugasSerialized = getIntent().getSerializableExtra("petugas");
        if (petugasSerialized != null) {
            Petugas petugas = (Petugas) petugasSerialized;
            checkUp.setPetugas(petugas);
        }

        checkUp.setTglCheckUp(getDateInString());


        for (Angket angket : angkets) {
            DetailCheckUp detailCheckUp = new DetailCheckUp();

            detailCheckUp.setCheckUp(checkUp);
            detailCheckUp.setAngket(angket);
            detailCheckUps.add(detailCheckUp);
        }

        AngketItemAdapter adapter = new AngketItemAdapter(this);
        adapter.addAll(detailCheckUps);
        adapter.notifyDataSetChanged();

        ListView listView = findViewById(R.id.questions_listview);
        listView.setAdapter(adapter);
    }

    private String getDateInString() {
        Calendar calendar = Calendar.getInstance();

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        return day + "/" + (month + 1) + "/" + year;
    }

    private void simpan() {


    }
}
