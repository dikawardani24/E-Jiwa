package com.puskesmascilandak.e_jiwa.activities.main.screening.detail;

import android.support.v7.app.ActionBar;
import android.widget.ListView;

import com.puskesmascilandak.e_jiwa.R;
import com.puskesmascilandak.e_jiwa.activities.Activity;
import com.puskesmascilandak.e_jiwa.adapter.AnsweredItemAdapter;
import com.puskesmascilandak.e_jiwa.model.CheckUp;
import com.puskesmascilandak.e_jiwa.model.DetailCheckUp;
import com.puskesmascilandak.e_jiwa.service.DetailCheckUpDbService;

import java.io.Serializable;
import java.util.List;

public class DetailAnsweredActivity extends Activity {

    public DetailAnsweredActivity() {
        super(R.layout.activity_detail_answered);
    }

    @Override
    protected void initOnCreate() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setTitle("Detil Jawaban Pertanyaan");

        Serializable serializable = getIntent().getSerializableExtra("check_up");

        if (serializable != null) {
            CheckUp checkUp = (CheckUp) serializable;
            DetailCheckUpDbService service = new DetailCheckUpDbService(this);
            List<DetailCheckUp> detailCheckUps = service.findBy(checkUp);

            AnsweredItemAdapter adapter = new AnsweredItemAdapter(this);
            adapter.addAll(detailCheckUps);
            adapter.notifyDataSetChanged();

            ListView listView = findViewById(R.id.list_answered);
            listView.setAdapter(adapter);
        }
    }
}