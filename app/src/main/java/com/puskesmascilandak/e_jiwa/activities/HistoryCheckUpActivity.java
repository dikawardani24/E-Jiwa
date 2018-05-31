package com.puskesmascilandak.e_jiwa.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.puskesmascilandak.e_jiwa.R;
import com.puskesmascilandak.e_jiwa.adapter.CheckUpItemAdapter;
import com.puskesmascilandak.e_jiwa.business.CSVHelper;
import com.puskesmascilandak.e_jiwa.model.CheckUp;
import com.puskesmascilandak.e_jiwa.service.CheckUpDbService;

import java.util.List;

public class HistoryCheckUpActivity extends AppCompatActivity {
    private CheckUpItemAdapter adapter;
    private List<CheckUp> checkUps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_check_up);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        CheckUpDbService service = new CheckUpDbService(this);
        checkUps = service.getAll();

        ListView listView = findViewById(R.id.list_history);
        adapter = new CheckUpItemAdapter(this);
        adapter.addAll(checkUps);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewDetailCheckup(position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu_history_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.export_csv) {
            exportToCsv();
        }

        return super.onOptionsItemSelected(item);
    }

    private void exportToCsv() {
        CSVHelper helper = new CSVHelper(this);

        helper.write(checkUps);
    }

    private void viewDetailCheckup(int position) {
        CheckUp checkUp = adapter.getItem(position);
        if (checkUp != null) {
            Intent intent = new Intent(this, DetailAnsweredActivity.class);

            intent.putExtra("check_up", checkUp);
            startActivity(intent);
        }
    }

}
