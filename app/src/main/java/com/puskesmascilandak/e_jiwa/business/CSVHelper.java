package com.puskesmascilandak.e_jiwa.business;

import android.content.Context;
import android.os.Environment;

import com.puskesmascilandak.e_jiwa.model.CheckUp;
import com.puskesmascilandak.e_jiwa.model.DetailCheckUp;
import com.puskesmascilandak.e_jiwa.model.Pasien;
import com.puskesmascilandak.e_jiwa.model.Petugas;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import de.siegmar.fastcsv.writer.CsvAppender;
import de.siegmar.fastcsv.writer.CsvWriter;

public class CSVHelper {
    private final File directory;
    private final File file;

    public CSVHelper(Context context) {
        directory = context.getExternalFilesDir("/Android/data/"+context.getPackageName());

        String fileName = context.getExternalFilesDir("/Android/data/"+context.getPackageName())+"/data_check_up.csv";
        file = new File(fileName);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void write(List<CheckUp> checkUps) {
        CsvWriter csvWriter = new CsvWriter();

        try {

            if (!directory.exists()) {
                directory.mkdir();
            }

            if (!file.exists()) {
                file.createNewFile();
            }

            CsvAppender csvAppender = csvWriter.append(file, StandardCharsets.UTF_8);

            csvAppender.appendLine("Nama Pasien", "NIK Pasien", "Alamat", "Tanggal Lahir", "Score", "Keterangan", "Pemeriksa");

            for (CheckUp checkUp : checkUps) {
                Pasien pasien = checkUp.getPasien();
                Petugas petugas = checkUp.getPetugas();

                String namaPasien = toString(pasien.getNama());
                String nik = toString(pasien.getNoKtp());
                String alamat = toString(pasien.getAlamat());
                String tglLahir = toString(pasien.getTglLahir());
                String score = toString(checkUp.getScore());
                String keterangan = toString(checkUp.getKeterangan());
                String pemeriksa = toString(petugas.getNama());

                csvAppender.appendLine(namaPasien, nik, alamat, tglLahir, score, keterangan, pemeriksa);
            }

            csvAppender.endLine();
            csvAppender.flush();
            csvAppender.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String toString(Object o) {
        if (o == null) {
            return "";
        }

        return o.toString();
    }
}
