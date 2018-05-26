package com.puskesmascilandak.e_jiwa.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.puskesmascilandak.e_jiwa.model.Angket;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ejiwa.db";
    private static final int DATABASE_VERSION = 1;

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTablePasien = "CREATE TABLE pasien(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nama TEXT, " +
                "tgl_lahir TEXT, " +
                "alamat TEXT, " +
                "no_telp TEXT, " +
                "no_ktp TEXT, " +
                "gender TEXT)";
        sqLiteDatabase.execSQL(createTablePasien);

        String createTablePetugas = "CREATE TABLE petugas(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nama TEXT, " +
                "tgl_lahir TEXT, " +
                "alamat TEXT, " +
                "no_telp TEXT, " +
                "no_ktp TEXT, " +
                "gender TEXT)";
        sqLiteDatabase.execSQL(createTablePetugas);

        String createTableCheckUp = "CREATE TABLE check_up(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tgl_check_up TEXT, " +
                "idpasien INTEGER, " +
                "idpetugas INTEGER, " +
                "score INTEGER, " +
                "keterangan TEXT)";
        sqLiteDatabase.execSQL(createTableCheckUp);

        String createTableUser = "CREATE TABLE user(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, " +
                "password TEXT, " +
                "idpetugas INTEGER)";
        sqLiteDatabase.execSQL(createTableUser);

        String createTableAngket = "CREATE TABLE angket(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "question TEXT)";
        sqLiteDatabase.execSQL(createTableAngket);

        String createTableDetailCheckUp = "CREATE TABLE detail_check_up(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idcheck_up INTEGER, " +
                "idangket INTEGER, " +
                "answer TEXT)";
        sqLiteDatabase.execSQL(createTableDetailCheckUp);

        List<Angket> angkets = collectQuestions();
        String sqlInsertDataToTableAngket = generateQueryInsertDataAngket(angkets);
        sqLiteDatabase.execSQL(sqlInsertDataToTableAngket);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String dropTable = "DROP TABLE IF EXISTS ";

        sqLiteDatabase.execSQL(dropTable + "pasien");
        sqLiteDatabase.execSQL(dropTable + "petugas");
        sqLiteDatabase.execSQL(dropTable + "check_up");
        sqLiteDatabase.execSQL(dropTable + "user");
        sqLiteDatabase.execSQL(dropTable + "angket");
        sqLiteDatabase.execSQL(dropTable + "detail_check_up");
    }

    private String generateQueryInsertDataAngket(List<Angket> angkets) {
        StringBuilder defaultAngket = new StringBuilder("INSERT INTO angket(question) VALUES");

        for (int i=0; i<angkets.size()-1; i++) {
            defaultAngket.append("('").append(angkets.get(i).getQuestion()).append("'), ");
        }
        defaultAngket.append("('").append(angkets.get(angkets.size()-1).getQuestion()).append("')");

        return defaultAngket.toString();
    }

    private Angket createNewAngket(String question) {
        Angket angket = new Angket();
        angket.setQuestion(question);
        return angket;
    }

    private List<Angket> collectQuestions() {
        List<Angket> angkets = new ArrayList<>();

        angkets.add(createNewAngket("Apakah Anda sering menderita sakit kepala?"));
        angkets.add(createNewAngket("Apakah Anda kehilangan nafsu makan?"));
        angkets.add(createNewAngket("Apakah tidur Anda tidak lelap?"));
        angkets.add(createNewAngket("Apakah Anda mudah menjadi takut?"));
        angkets.add(createNewAngket("Apakah Anda merasa cemas. tegang dan khawatir?"));
        angkets.add(createNewAngket("Apakah tangan Anda gemetar?"));
        angkets.add(createNewAngket("Apakah Anda mengalami gangguan pencernaan?"));
        angkets.add(createNewAngket("Apakah Anda merasa sulit berpikir jernih?"));
        angkets.add(createNewAngket("Apakah Anda merasa tidak bahagia?"));
        angkets.add(createNewAngket("Apakah Anda Iebih sering menangis?"));
        angkets.add(createNewAngket("Apakah Anda merasa sulit untuk menikmati aktivitas sehari-hari?"));
        angkets.add(createNewAngket("Apakah Anda mengalami kesulitan untuk mengambil keputusan?"));
        angkets.add(createNewAngket("Apakah aktivitas/tugas sehari-hari Anda terbengkalai?"));
        angkets.add(createNewAngket("Apakah Anda merasa tidak mampu berperan dalam kehidupan ini?"));
        angkets.add(createNewAngket("Apakah Anda kehilangan minat terhadap banyak hal?"));
        angkets.add(createNewAngket("Apakah Anda merasa tidak berharga?"));
        angkets.add(createNewAngket("Apakah Anda mempunyai pikiran untuk mengakhiri hidup Anda?"));
        angkets.add(createNewAngket("Apakah Anda merasa lelah sepanjang waktu?"));
        angkets.add(createNewAngket("Apakah Anda merasa tidak enak di perut?"));
        angkets.add(createNewAngket("Apakah Anda mudah Ielah?"));

        angkets.add(createNewAngket("Apakah Anda minum alkohol lebih banyak dari biasanya atau Apakah Anda menggunakan narkoba?"));

        angkets.add(createNewAngket("Apakah Anda yakin bahwa seseorang mencoba rnencelakai Anda dengan cara tertentu?"));
        angkets.add(createNewAngket("Apakah ada yang mengganggu atau hal yang tidak biasa dalam pikiran Anda?"));
        angkets.add(createNewAngket("Apakah Anda pernah mendengar suara tanpa tahu sumbernya atau yang orang lain tidak dapat mendengar?"));

        angkets.add(createNewAngket("Apakah Anda mengalami mimpi yang mengganggu tentang suatu bencana/musibah atau adakah saat-saat Anda seolah mengalami kembali kejadian bencana itu?"));
        angkets.add(createNewAngket("Apakah Anda menghindari kegiatan, tempat, orang atau pikiran yang mengingatkan Anda akan bencana tersebut?"));
        angkets.add(createNewAngket("Apakah minat Anda terhadap teman dan kegiatan yang biasa Anda lakukan berkurang?"));
        angkets.add(createNewAngket("Apakah Anda merasa sangat terganggu jika berada dalam situasi yang mengingatkan Anda akan bencana atau jika Anda berpikir tentang bencana itu?"));
        angkets.add(createNewAngket("Apakah Anda kesulitan memahami atau mengekspresikan perasaan Anda?"));

        return angkets;
    }
}
