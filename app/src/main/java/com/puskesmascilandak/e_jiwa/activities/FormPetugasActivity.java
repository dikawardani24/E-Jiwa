package com.puskesmascilandak.e_jiwa.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.puskesmascilandak.e_jiwa.R;
import com.puskesmascilandak.e_jiwa.model.Petugas;
import com.puskesmascilandak.e_jiwa.service.PetugasDbService;

public class FormPetugasActivity extends FormPersonActivity {
    private Petugas petugas;

    public FormPetugasActivity() {
        super(R.layout.activity_form_petugas);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        inputTglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void startRegisterUserActivity() {
        boolean couldContinueNextStep = simpanDataPetugas();

        if (couldContinueNextStep) {
            Intent intent = new Intent(this, FormUserActivity.class);

            intent.putExtra("action", "sign_up");
            intent.putExtra("petugas", petugas);
            startActivity(intent);
        }
    }

    private boolean simpanDataPetugas() {
        if(!validateAllInput()) return false;

        petugas = new Petugas();
        initData(petugas);

        try {
            PetugasDbService service = new PetugasDbService(this);
            service.simpan(petugas);
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            Toast.makeText(this, "Gagal Menyimpan Data Petugas Dengan Nama : "+petugas.getNama(),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
