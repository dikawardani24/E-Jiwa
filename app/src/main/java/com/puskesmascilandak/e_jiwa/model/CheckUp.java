package com.puskesmascilandak.e_jiwa.model;

public class CheckUp extends AbstractEntity {
    private String tglCheckUp;
    private Pasien pasien;
    private Petugas petugas;
    private int score;
    private String keterangan;

    public String getTglCheckUp() {
        return tglCheckUp;
    }

    public void setTglCheckUp(String tglCheckUp) {
        this.tglCheckUp = tglCheckUp;
    }

    public Pasien getPasien() {
        return pasien;
    }

    public void setPasien(Pasien pasien) {
        this.pasien = pasien;
    }

    public Petugas getPetugas() {
        return petugas;
    }

    public void setPetugas(Petugas petugas) {
        this.petugas = petugas;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
