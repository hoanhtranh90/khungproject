package com.dgtt.business.utils;

public class TikTok {
    private long tik;
    private long tok;

    public long getTik() {
        return tik;
    }

    public void tick() {
        this.tik = System.currentTimeMillis();
    }

    public long tok() {
        tok = System.currentTimeMillis();
        return tok - tik;
    }

    public void setTok(long tok) {
        this.tok = tok;
    }
}
