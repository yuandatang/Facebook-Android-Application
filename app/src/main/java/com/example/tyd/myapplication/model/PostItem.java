package com.example.tyd.myapplication.model;

/**
 * Created by tyd on 2017/4/19.
 */

public class PostItem {
    public String img;
    public String name;
    public String time;
    public String summary;

    public PostItem() {
    }

    public PostItem(String img, String name, String time,String message) {
        this.img = img;
        this.name = name;
        this.time = time;
        this.summary = message;
    }

}
