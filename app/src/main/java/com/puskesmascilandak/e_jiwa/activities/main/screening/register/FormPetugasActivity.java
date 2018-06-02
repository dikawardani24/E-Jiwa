package com.puskesmascilandak.e_jiwa.activities.main.screening.register;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;

import com.puskesmascilandak.e_jiwa.R;
import com.puskesmascilandak.e_jiwa.model.Petugas;
import com.puskesmascilandak.e_jiwa.service.PetugasDbService;

public class FormPetugasActivity extends FormPersonActivity {
    private Petugas petugas;

    public FormPetugasActivity() {
        super(R.layout.activity_form_petugas);
    }


    @Override
    protected void initOnCreate() {
        super.initOnCreate();
        initUpNavigation();

        Button clearInputBtn = findViewById(R.id.clear_input_btn);
        clearInputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearInput();
            }
        });

        Button nextStepBtn = findViewById(R.id.next_step_btn);
        nextStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegisterUserActivity();
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
            PetugasDbService service = new PetugasDbService(this);
            petugas = service.findBy(noKtp);

            if (petugas != null) {
                showDialog("Gagal", "Sudah Ada Data Petugas Dengan No. Ktp : "+noKtp);
                return false;
            }

            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            showDialog("Gagal", "Tidak Dapat Terhubung Ke Database");
            return false;
        }
    }

    private void startRegisterUserActivity() {
        if (!validateAllInput()) return;

        petugas = new Petugas();
        initData(petugas);

        Intent intent = new Intent(this, FormUserActivity.class);
        intent.putExtra("action", "sign_up");
        intent.putExtra("petugas", petugas);
        startActivity(intent);
        finish();
    }
}
