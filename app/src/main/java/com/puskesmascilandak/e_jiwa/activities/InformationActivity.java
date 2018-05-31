package com.puskesmascilandak.e_jiwa.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.puskesmascilandak.e_jiwa.R;

import org.w3c.dom.Text;

public class InformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        WebView myWebView = findViewById(R.id.webview);
        myWebView.loadUrl("https://www.google.com/maps/search/maps/@37.418478,-122.0894254,15z/data=!3m1!4b1");

    }
}
