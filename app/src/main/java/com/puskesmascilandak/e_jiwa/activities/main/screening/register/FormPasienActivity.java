package com.puskesmascilandak.e_jiwa.activities.main.screening.register;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.puskesmascilandak.e_jiwa.R;
import com.puskesmascilandak.e_jiwa.Session;
import com.puskesmascilandak.e_jiwa.activities.main.screening.CheckUpActivity;
import com.puskesmascilandak.e_jiwa.model.Pasien;
import com.puskesmascilandak.e_jiwa.model.Petugas;
import com.puskesmascilandak.e_jiwa.model.User;
import com.puskesmascilandak.e_jiwa.service.PasienDbService;

public class FormPasienActivity extends FormPersonActivity {

    public FormPasienActivity() {
        super(R.layout.activity_form_pasien);
    }

    @Override
    protected void initOnCreate() {
        super.initOnCreate();
        setTitle("Input Data Pasien");
        initUpNavigation();

        Button startBtn = findViewById(R.id.start_btn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
    }

    private void initUpNavigation() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected boolean validateNoKtp() {
        if (!super.validateNoKtp()) return false;

        try {
            String noKtp = getValueFrom(inputNoKtp);
            PasienDbService service = new PasienDbService(this);
            Pasien pasien = service.findBy(noKtp);

            if (pasien != null) {
                showDialog("Gagal", "Sudah Ada Data Pasien Dengan No. KTP : "+noKtp);
                return false;
            }

            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            showDialog("Gagal", "Tidak Dapat Mengakses Database");
            return false;
        }
    }

    private void start() {
        if (!validateAllInput()) return;

        Pasien pasien = new Pasien();
        initData(pasien);

        PasienDbService service = new PasienDbService(this);
        try {
            service.simpan(pasien);
            startCheckUpActivity(pasien);
        } catch (SQLiteException e) {
            Toast.makeText(this,"Gagal Menyimpan Data Pasien",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void startCheckUpActivity(Pasien pasien) {
        Session session = new Session(this);
        User user = session.getUser();

        if (user != null) {
            Petugas petugas = user.getPetugas();
            Intent intent = new Intent(this, CheckUpActivity.class);

            intent.putExtra("pasien", pasien);
            intent.putExtra("petugas", petugas);
            startActivity(intent);
            finish();
        }
    }
}
