package com.puskesmascilandak.e_jiwa;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class EJiwaPreference {
    private SharedPreferences preferences;
    private final Context context;

    EJiwaPreference(Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    protected Context getContext() {
        return context;
    }

    void putString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    String getString(String key) {
        return preferences.getString(key, "");
    }

    public void quit() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
