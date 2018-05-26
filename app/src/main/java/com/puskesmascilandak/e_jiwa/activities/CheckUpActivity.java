package com.puskesmascilandak.e_jiwa.activities;

import android.database.sqlite.SQLiteException;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.puskesmascilandak.e_jiwa.R;
import com.puskesmascilandak.e_jiwa.adapter.AngketItemAdapter;
import com.puskesmascilandak.e_jiwa.model.Angket;
import com.puskesmascilandak.e_jiwa.model.CheckUp;
import com.puskesmascilandak.e_jiwa.model.DetailCheckUp;
import com.puskesmascilandak.e_jiwa.model.Pasien;
import com.puskesmascilandak.e_jiwa.model.Petugas;
import com.puskesmascilandak.e_jiwa.service.AngketDbService;
import com.puskesmascilandak.e_jiwa.service.CheckUpDbService;
import com.puskesmascilandak.e_jiwa.service.DetailCheckUpDbService;
import com.puskesmascilandak.e_jiwa.service.PasienDbService;
import com.puskesmascilandak.e_jiwa.util.DialogHelper;

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
        initUpNavigation();

        initCheckUp();
        initDetailCheckUp();

        ListView listView = findViewById(R.id.questions_listview);
        listView.setAdapter(buildAdapter());

        Button saveCheckUpBtn = findViewById(R.id.save_check_up_btn);
        saveCheckUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpan();
            }
        });
    }

    private void initUpNavigation() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private AngketItemAdapter buildAdapter() {
        AngketItemAdapter adapter = new AngketItemAdapter(this);
        adapter.addAll(detailCheckUps);
        adapter.notifyDataSetChanged();
        return adapter;
    }

    private void initCheckUp() {
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
        } else {
            DialogHelper.showDialog(this, "Gagal", "Data Petugas Tidak Terkirim");
            return;
        }

        checkUp.setTglCheckUp(getDateInString());

    }

    private void initDetailCheckUp() {
        AngketDbService angketDbService = new AngketDbService(this);
        List<Angket> angkets = angketDbService.getAll();
        detailCheckUps = new ArrayList<>();

        for (Angket angket : angkets) {
            DetailCheckUp detailCheckUp = new DetailCheckUp();

            detailCheckUp.setCheckUp(checkUp);
            detailCheckUp.setAngket(angket);
            detailCheckUps.add(detailCheckUp);
        }
    }

    private String getDateInString() {
        Calendar calendar = Calendar.getInstance();

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        return day + "/" + (month + 1) + "/" + year;
    }

    private void simpan() {
        boolean dataCheckUpSaved = simpanDataCheckUp(checkUp);
        boolean allDetailSaved = false;

        if (dataCheckUpSaved) {
            DetailCheckUpDbService service = new DetailCheckUpDbService(this);

            for (DetailCheckUp detailCheckUp : detailCheckUps) {
                try {
                    service.simpan(detailCheckUp);
                    allDetailSaved = true;
                } catch (SQLiteException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        if (allDetailSaved) {
            finish();
        }
    }

    private int countScore() {
        return 20;
    }

    private boolean simpanDataCheckUp(CheckUp checkUp) {
        checkUp.setScore(countScore());
        CheckUpDbService service = new CheckUpDbService(this);

        try {
            service.simpan(checkUp);
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();

            new PasienDbService(this).delete(checkUp.getPasien());
            return false;
        }
    }
}
