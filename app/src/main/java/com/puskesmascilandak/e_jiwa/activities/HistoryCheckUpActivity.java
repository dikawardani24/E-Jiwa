package com.puskesmascilandak.e_jiwa.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.puskesmascilandak.e_jiwa.R;
import com.puskesmascilandak.e_jiwa.adapter.CheckUpItemAdapter;
import com.puskesmascilandak.e_jiwa.model.CheckUp;
import com.puskesmascilandak.e_jiwa.service.CheckUpDbService;

import java.util.List;

public class HistoryCheckUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_check_up);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        CheckUpDbService service = new CheckUpDbService(this);
        List<CheckUp> checkUps = service.getAll();

        ListView listView = findViewById(R.id.list_history);
        CheckUpItemAdapter adapter = new CheckUpItemAdapter(this);
        adapter.addAll(checkUps);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }
}
