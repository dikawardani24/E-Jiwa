package com.puskesmascilandak.e_jiwa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.puskesmascilandak.e_jiwa.R;


public class UtamaActivity extends AppCompatActivity {

    private ImageButton btn_Info, btn_Screen, btn_Map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama);

        //inisial tombol
        btn_Info = (ImageButton) findViewById(R.id.btn_info);
        btn_Screen = (ImageButton) findViewById(R.id.btn_screen);
        btn_Map = (ImageButton) findViewById(R.id.btn_map);

        // function tombol
        btn_Screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(iLogin);
            }
        });


    }

}
