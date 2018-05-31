package com.puskesmascilandak.e_jiwa.activities;

import android.content.res.Configuration;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.puskesmascilandak.e_jiwa.R;
import com.puskesmascilandak.e_jiwa.business.DetermineScore;
import com.puskesmascilandak.e_jiwa.model.Angket;
import com.puskesmascilandak.e_jiwa.model.CheckUp;
import com.puskesmascilandak.e_jiwa.model.DetailCheckUp;
import com.puskesmascilandak.e_jiwa.model.Pasien;
import com.puskesmascilandak.e_jiwa.model.Petugas;
import com.puskesmascilandak.e_jiwa.service.AngketDbService;
import com.puskesmascilandak.e_jiwa.service.CheckUpDbService;
import com.puskesmascilandak.e_jiwa.service.DetailCheckUpDbService;
import com.puskesmascilandak.e_jiwa.util.DialogHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CheckUpActivity extends AppCompatActivity {
    private static int lastAnswer = 0;
    private static List<DetailCheckUp> detailCheckUps;
    private CheckUp checkUp;
    private TextView numberQuestionTextView, questionTextView,
            namaPasienTextView, noTelpTextView, noKtpTextView,
            alamatTextView, resultTextView;
    private RadioButton yesRb, noRb;
    private Button prevBtn, nextBtn;
    private CardView containerDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_up);

        initCheckUp();
        detailCheckUps = new ArrayList<>();
        AngketDbService service = new AngketDbService(this);
        List<Angket> angkets = service.getAll();

        for (Angket angket : angkets) {
            DetailCheckUp detailCheckUp = new DetailCheckUp();
            detailCheckUp.setCheckUp(checkUp);
            detailCheckUp.setAngket(angket);
            detailCheckUp.setAnswer("Tidak");
            detailCheckUps.add(detailCheckUp);
        }

        numberQuestionTextView = findViewById(R.id.number_textview);
        questionTextView = findViewById(R.id.question_textview);
        namaPasienTextView = findViewById(R.id.nama_txt);
        noKtpTextView = findViewById(R.id.no_ktp_txt);
        noTelpTextView = findViewById(R.id.no_telp_txt);
        alamatTextView = findViewById(R.id.alamat_txt);
        resultTextView = findViewById(R.id.hasil_check_up_txt);

        prevBtn = findViewById(R.id.prev_question_btn);
        nextBtn = findViewById(R.id.next_question_btn);
        containerDetail = findViewById(R.id.detail_pasien_container);
        containerDetail.setVisibility(View.GONE);
        viewDetailPasien();

        prevBtn.setVisibility(View.GONE);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevQuestion();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });

        yesRb = findViewById(R.id.yes_rb);
        yesRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    DetailCheckUp detailCheckUp = detailCheckUps.get(lastAnswer);
                    detailCheckUp.setAnswer("Ya");
                }
            }
        });

        noRb = findViewById(R.id.no_rb);
        noRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    DetailCheckUp detailCheckUp = detailCheckUps.get(lastAnswer);
                    detailCheckUp.setAnswer("Tidak");
                }
            }
        });

        Button saveBtn = findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCheckUp();
            }
        });

        viewQuestion();
    }

    private void nextQuestion() {
        int max = detailCheckUps.size() - 1;
        if (lastAnswer < max) {
            lastAnswer += 1;

            if (prevBtn.getVisibility() == View.GONE) {
                prevBtn.setVisibility(View.VISIBLE);
            }
        }

        if (lastAnswer == max) {
            nextBtn.setVisibility(View.GONE);
            containerDetail.setVisibility(View.VISIBLE);
            determineScore();
        } else {
            containerDetail.setVisibility(View.GONE);
        }

        viewQuestion();
    }

    private void determineScore() {
        DetermineScore determineScore = new DetermineScore(this);
        int score = determineScore.countTotalYesAnswer(detailCheckUps);
        resultTextView.setText(String.valueOf(score));
    }

    private void prevQuestion() {
        if (lastAnswer > 0) {
            lastAnswer -= 1;

            if (nextBtn.getVisibility() == View.GONE) {
                nextBtn.setVisibility(View.VISIBLE);
            }
        }

        if (lastAnswer == 0) {
            prevBtn.setVisibility(View.GONE);
        }

        viewQuestion();
    }

    private void viewDetailPasien() {
        Pasien pasien = checkUp.getPasien();
        if (pasien != null) {
            namaPasienTextView.setText(pasien.getNama());
            noTelpTextView.setText(pasien.getNoTelp());
            noKtpTextView.setText(pasien.getNoKtp());
            alamatTextView.setText(pasien.getAlamat());
        }
    }

    private void saveCheckUp() {
        CheckUpDbService service = new CheckUpDbService(this);
        try {
            service.simpan(checkUp);
            simpanDetailCheckUp();
        } catch (SQLiteException e) {
            e.printStackTrace();
            DialogHelper.showDialog(this, "Gagal", "Tidak dapat menyimpan data checkup");
        }

    }

    private void simpanDetailCheckUp() {
        DetailCheckUpDbService service = new DetailCheckUpDbService(this);
        for (DetailCheckUp detailCheckUp : detailCheckUps) {
            try {
                service.simpan(detailCheckUp);
            } catch (SQLiteException e) {
                e.printStackTrace();
                DialogHelper.showDialog(this, "Gagal", "Tidak dapat menyimpan detil checkup");
                break;
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (lastAnswer == 0) {
            prevBtn.setVisibility(View.GONE);
        }

        if (lastAnswer > 0) {
            nextBtn.setVisibility(View.VISIBLE);
            prevBtn.setVisibility(View.VISIBLE);
        }

        if (lastAnswer == detailCheckUps.size() -1) {
            nextBtn.setVisibility(View.GONE);
        }
    }

    private void viewQuestion() {
        DetailCheckUp detailCheckUp = detailCheckUps.get(lastAnswer);
        if (detailCheckUp != null) {
            Angket angket = detailCheckUp.getAngket();
            numberQuestionTextView.setText(String.valueOf(angket.getId()));
            questionTextView.setText(angket.getQuestion());

            switch (detailCheckUp.getAnswer()) {
                case "Tidak" : noRb.setChecked(true);break;
                case "Ya" : yesRb.setChecked(true);break;
            }
        }

        Log.e("LAST PAGE", String.valueOf(lastAnswer));
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

    private String getDateInString() {
        Calendar calendar = Calendar.getInstance();

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        return day + "/" + (month + 1) + "/" + year;
    }
}
