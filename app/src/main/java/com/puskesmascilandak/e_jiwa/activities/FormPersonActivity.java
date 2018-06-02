package com.puskesmascilandak.e_jiwa.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import com.puskesmascilandak.e_jiwa.R;
import com.puskesmascilandak.e_jiwa.model.Person;
import com.puskesmascilandak.e_jiwa.util.DialogHelper;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class FormPersonActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener {
    @BindView(R.id.input_nama_lengkap)
    protected EditText inputNamaLengkap;
    @BindView(R.id.input_tgl_lahir)
    protected EditText inputTglLahir;
    @BindView(R.id.input_alamat)
    protected EditText inputAlamat;
    @BindView(R.id.input_no_telp)
    protected EditText inputNoTelp;
    @BindView(R.id.input_no_ktp)
    protected EditText inputNoKtp;
    @BindView(R.id.pria_rb)
    protected RadioButton priaRb;
    @BindView(R.id.wanita_rb)
    protected RadioButton wanitaRb;

    private final int layout;

    public FormPersonActivity(int layout) {
        this.layout = layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout);
        ButterKnife.bind(this);

        inputTglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    protected void initData(Person person) {
        person.setNama(getValueFrom(inputNamaLengkap));
        person.setTglLahir(getValueFrom(inputTglLahir));
        person.setNoTelp(getValueFrom(inputNoTelp));
        person.setNoKtp(getValueFrom(inputNoKtp));
        person.setAlamat(getValueFrom(inputAlamat));

        if (priaRb.isChecked()) person.setGender("Pria");
        else person.setGender("Wanita");
    }

    protected String getValueFrom(EditText editText) {
        return editText.getText().toString();
    }

    protected boolean isContainSpesialCharacter(String toCheck) {
        String forbidenChars = "!@#$%^&*()_+";

        for (char character : forbidenChars.toCharArray()) {
            String forbidenChar = String.valueOf(character);

            if (toCheck.contains(forbidenChar)) {
                return true;
            }
        }

        return false;
    }

    protected boolean validateNama() {
        String nama = getValueFrom(inputNamaLengkap);
        if (nama.isEmpty()) {
            inputNamaLengkap.setError("Nama Lengkap Masih Kosong");
            return false;
        }

        if (isContainSpesialCharacter(nama)) {
            inputNamaLengkap.setError("Nama Tidak Valid");
            return false;
        }

        return true;
    }

    protected boolean validateTglLahir() {
        String tglLahir = getValueFrom(inputTglLahir);
        if (tglLahir.isEmpty()) {
            inputTglLahir.setError("Tanggal Lahir Masih Kosong");
            return false;
        }

        return true;
    }

    protected boolean validateAlamat() {
        String alamat = getValueFrom(inputAlamat);
        if (alamat.isEmpty()) {
            inputAlamat.setError("Alamat Masih Kosong");
            return false;
        }

        if (isContainSpesialCharacter(alamat)) {
            inputAlamat.setError("Alamat Tidak Valid");
            return false;
        }

        return true;
    }

    protected boolean validateNoKtp() {
        String noKtp = getValueFrom(inputNoKtp);
        if (noKtp.isEmpty()) {
            inputNoKtp.setError("No. KTP Masih Kosong");
            return false;
        }

        return true;
    }

    protected boolean validateNoTelp() {
        String noTelp = getValueFrom(inputNoTelp);
        if (noTelp.isEmpty()) {
            inputNoTelp.setError("No. Telepon Masih Kosong");
            return false;
        }

        return true;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    protected boolean validateAllInput() {
        return validateNama() &
                validateNoKtp() &
                validateNoTelp() &
                validateTglLahir() &
                validateAlamat();
    }

    protected void clearInput() {
        inputNamaLengkap.setText("");
        inputTglLahir.setText("");
        inputAlamat.setText("");
        inputNoTelp.setText("");
        inputNoKtp.setText("");
    }

    protected void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(this, this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        dialog.show();
    }

    protected void showDialog(String title, String message) {
        DialogHelper.showDialog(this, title, message);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String chosenDate = String.valueOf(dayOfMonth) +
                "/"+
                (month + 1) +
                "/" +
                year;

        inputTglLahir.setText(chosenDate);
    }
}
