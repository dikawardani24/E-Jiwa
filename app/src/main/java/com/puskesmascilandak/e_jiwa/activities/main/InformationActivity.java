package com.puskesmascilandak.e_jiwa.activities.main;

import android.webkit.WebView;

import com.puskesmascilandak.e_jiwa.R;
import com.puskesmascilandak.e_jiwa.activities.Activity;

public class InformationActivity extends Activity {

    public InformationActivity() {
        super(R.layout.activity_information);
    }

    @Override
    protected void initOnCreate() {
        WebView myWebView = findViewById(R.id.webview);
        myWebView.loadUrl("https://www.kompas.com");
    }
}
