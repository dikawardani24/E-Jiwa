package com.puskesmascilandak.e_jiwa.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.puskesmascilandak.e_jiwa.model.Pasien;

public class PasienDbService extends PersonDbService<Pasien> {

    public PasienDbService(Context context) {
        super(context, "pasien");
    }

    @Override
    protected ContentValues createContentValuesFrom(Pasien pasien) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("nama", pasien.getNama());
        contentValues.put("tgl_lahir", pasien.getTglLahir());
        contentValues.put("alamat", pasien.getAlamat());
        contentValues.put("no_telp", pasien.getNoTelp());
        contentValues.put("no_ktp", pasien.getNoKtp());
        contentValues.put("gender", pasien.getGender());

        return contentValues;
    }

    @Override
    protected Pasien fetchRow(Cursor cursor) {
        Pasien pasien = new Pasien();

        pasien.setId(getIntFrom(cursor, "_id"));
        pasien.setNama(getStringFrom(cursor, "nama"));
        pasien.setAlamat(getStringFrom(cursor, "alamat"));
        pasien.setNoKtp(getStringFrom(cursor, "no_ktp"));
        pasien.setNoTelp(getStringFrom(cursor, "no_telp"));
        pasien.setTglLahir(getStringFrom(cursor, "tgl_lahir"));
        pasien.setGender(getStringFrom(cursor, "gender"));

        return pasien;
    }
}
