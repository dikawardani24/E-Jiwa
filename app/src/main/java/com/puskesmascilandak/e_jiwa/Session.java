package com.puskesmascilandak.e_jiwa;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.puskesmascilandak.e_jiwa.model.User;
import com.puskesmascilandak.e_jiwa.service.UserDbService;

public class Session {
    private SharedPreferences preferences;
    private final Context context;

    public Session(Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public void setUsername(String username) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", username);
        editor.apply();
    }

    public String getUsername() {
        return preferences.getString("username", "");
    }

    public User getUser() {
        UserDbService service = new UserDbService(context);
        return service.findBy(getUsername());
    }

    public void quit() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
