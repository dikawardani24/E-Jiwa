package com.puskesmascilandak.e_jiwa.activities.main.screening;

import android.content.res.Configuration;
import android.database.sqlite.SQLiteException;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.puskesmascilandak.e_jiwa.R;
import com.puskesmascilandak.e_jiwa.activities.Activity;
import com.puskesmascilandak.e_jiwa.business.DetermineScore;
import com.puskesmascilandak.e_jiwa.model.Angket;
import com.puskesmascilandak.e_jiwa.model.CheckUp;
import com.puskesmascilandak.e_jiwa.model.DetailCheckUp;
import com.puskesmascilandak.e_jiwa.model.Pasien;
import com.puskesmascilandak.e_jiwa.model.Petugas;
import com.puskesmascilandak.e_jiwa.service.AngketDbService;
import com.puskesmascilandak.e_jiwa.service.CheckUpDbService;
import com.puskesmascilandak.e_jiwa.service.DetailCheckUpDbService;
import com.puskesmascilandak.e_jiwa.util.CalendarHelper;
import com.puskesmascilandak.e_jiwa.util.DialogHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckUpActivity extends Activity {
    @BindView(R.id.number_textview) TextView numberQuestionTextView;
    @BindView(R.id.question_textview) TextView questionTextView;
    @BindView(R.id.nama_txt) TextView namaPasienTextView;
    @BindView(R.id.no_telp_txt) TextView noTelpTextView;
    @BindView(R.id.no_ktp_txt) TextView noKtpTextView;
    @BindView(R.id.alamat_txt) TextView alamatTextView;
    @BindView(R.id.score_check_up_txt) TextView resultTextView;
    @BindView(R.id.keterangan_check_up_txt) TextView keteranganTextView;

    @BindView(R.id.yes_rb) RadioButton yesRb;
    @BindView(R.id.no_rb) RadioButton noRb;

    @BindView(R.id.prev_question_btn) Button prevBtn;
    @BindView(R.id.next_question_btn) Button nextBtn;
    @BindView(R.id.detail_pasien_container) CardView containerDetail;

    private static int lastAnswer = 0;
    private static List<DetailCheckUp> detailCheckUps;
    private CheckUp checkUp;

    public CheckUpActivity() {
        super(R.layout.activity_check_up);
    }

    @Override
    protected void initOnCreate() {
        ButterKnife.bind(this);

        lastAnswer = 0;
        initCheckUp();
        initDetailCheckUp();
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
        yesRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    DetailCheckUp detailCheckUp = detailCheckUps.get(lastAnswer);
                    detailCheckUp.setAnswer("Ya");
                }
            }
        });
        noRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    DetailCheckUp detailCheckUp = detailCheckUps.get(lastAnswer);
                    detailCheckUp.setAnswer("Tidak");
                }
            }
        });

        containerDetail.setVisibility(View.GONE);
        Button saveBtn = findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCheckUp();
            }
        });

        viewQuestion();
    }

    private void initDetailCheckUp() {
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

        } else {
            containerDetail.setVisibility(View.GONE);
        }

        viewQuestion();
        determineScore();
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

        containerDetail.setVisibility(View.GONE);
        viewQuestion();
        determineScore();
    }

    private void determineScore() {
        DetermineScore determineScore = new DetermineScore(this);
        int score = determineScore.countTotalYesAnswer(detailCheckUps);
        String keterangan = determineScore.generateKeterangan(detailCheckUps);

        checkUp.setScore(score);
        checkUp.setKeterangan(keterangan);
        resultTextView.setText(String.valueOf(score));
        keteranganTextView.setText(keterangan);
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
            finish();
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

        checkUp.setTglCheckUp(CalendarHelper.getDefaultDateInString());
    }
}
