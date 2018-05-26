package com.puskesmascilandak.e_jiwa.service;

import java.util.List;

public interface DatabaseService<M> {
    List<M> getAll();

    M findBy(int id);

    void simpan(M entity);

    void perbaharui(M entity);

    void delete(M entity);
}
