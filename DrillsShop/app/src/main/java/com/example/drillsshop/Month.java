package com.example.drillsshop;

public class Month {
    private String title;
    private String info;
    private int imageResourceid;

    public Month(String title, String info, int imageResourceid) {
        this.title = title;
        this.info = info;
        this.imageResourceid= imageResourceid;
    }

    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }

    public int getImageResourceid() {
        return imageResourceid;
    }

    @Override
    public String toString() {
        return title;
    }
}
