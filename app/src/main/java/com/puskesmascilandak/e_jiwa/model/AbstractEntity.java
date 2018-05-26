package com.puskesmascilandak.e_jiwa.model;

import java.io.Serializable;

public abstract class AbstractEntity implements Serializable {
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
