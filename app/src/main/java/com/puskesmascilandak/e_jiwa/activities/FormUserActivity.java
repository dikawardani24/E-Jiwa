package com.puskesmascilandak.e_jiwa.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.puskesmascilandak.e_jiwa.R;
import com.puskesmascilandak.e_jiwa.model.Petugas;
import com.puskesmascilandak.e_jiwa.model.User;
import com.puskesmascilandak.e_jiwa.service.UserDbService;

public class FormUserActivity extends AppCompatActivity {
    private Petugas petugas;
    private EditText inputUsername, inputPassword, inputRetypePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_user);

        inputUsername = findViewById(R.id.input_username);
        inputPassword = findViewById(R.id.input_password);
        inputRetypePassword = findViewById(R.id.input_retype_password);

        Button cancelBtn = findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginActivity();
            }
        });

        Button registerBtn = findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanUser();
            }
        });

        petugas = (Petugas) getIntent().getSerializableExtra("petugas");
    }

    private void simpanUser() {
        if (!validateAllInput()) return;

        if (petugas == null) {
            showToast("Tidak Ada Data Petugas");
            return;
        }

        User user = new User();
        user.setPetugas(petugas);
        user.setUsername(getValueFrom(inputUsername));
        user.setPassword(getValueFrom(inputPassword));

        UserDbService service = new UserDbService(this);
        try {
            service.simpan(user);
            startLoginActivity();
            finish();
        } catch (SQLiteException e) {
            e.printStackTrace();
            showToast("Tidak Dapat Menympan Data User");
        }
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG)
                .show();
    }

    private boolean validateUsername() {
        String username = getValueFrom(inputUsername);

        if (username.isEmpty()) {
            inputUsername.setError("Username Masih Kosong");
            return false;
        }

        UserDbService service = new UserDbService(this);
        User user = service.findBy(username);
        if (user != null) {
            inputUsername.setError("Username Sudah Ada Yang Menggunakan");
            return false;
        }

        return true;
    }

    private boolean validatePassword() {
        String password = getValueFrom(inputPassword);

        if (password.isEmpty()) {
            inputPassword.setError("Password Masih Kosong");
            return false;
        }

        if (password.length() < 6) {
            inputPassword.setError("Jumlah Karakter Password Minimal 6");
            return false;
        }

        return true;
    }

    private boolean validateRetypePassword() {
        String retypePassword = getValueFrom(inputRetypePassword);

        if (retypePassword.isEmpty()) {
            inputRetypePassword.setError("Ketik Ulang Password Anda");
            return false;
        }

        String password = getValueFrom(inputPassword);
        boolean matched = password.equals(retypePassword);
        if (!matched) {
            inputRetypePassword.setError("Password Yang Anda Ketik Tidak Cocok");
            return false;
        }

        return true;
    }

    private boolean validateAllInput() {
        return validateUsername() &
                validatePassword() &
                validateRetypePassword();
    }

    private String getValueFrom(EditText editText) {
        return editText.getText().toString();
    }
}
