package com.puskesmascilandak.e_jiwa.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.puskesmascilandak.e_jiwa.R;

public class DetailAnsweredActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_answered);

        setTitle("Detail Jawaban Pertanyaan");

        ListView listView = findViewById(R.id.list_answered);
    }
}
