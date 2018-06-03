package com.puskesmascilandak.e_jiwa.activities.main.screening;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.puskesmascilandak.e_jiwa.R;
import com.puskesmascilandak.e_jiwa.activities.Activity;
import com.puskesmascilandak.e_jiwa.activities.main.screening.detail.DetailAnsweredActivity;
import com.puskesmascilandak.e_jiwa.adapter.CheckUpItemAdapter;
import com.puskesmascilandak.e_jiwa.model.CheckUp;
import com.puskesmascilandak.e_jiwa.service.CheckUpDbService;
import com.puskesmascilandak.e_jiwa.util.PopupUtil;

import java.util.List;

public class HistoryCheckUpActivity extends Activity {
    private CheckUpItemAdapter adapter;

    public HistoryCheckUpActivity() {
        super(R.layout.activity_history_check_up);
    }

    private void loadDataCheckUp() {
        CheckUpDbService service = new CheckUpDbService(this);
        List<CheckUp> checkUps = service.getAll();
        adapter.addAll(checkUps);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void initOnCreate() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ListView listView = findViewById(R.id.list_history);
        adapter = new CheckUpItemAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewDetailCheckup(position);
            }
        });

        PopupUtil.showLoading(this, "Memuat Data Check Up", "Mohon Tunggu...");
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                loadDataCheckUp();
                PopupUtil.dismissDialog();
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
