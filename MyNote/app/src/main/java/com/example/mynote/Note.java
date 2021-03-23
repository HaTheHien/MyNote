package com.example.mynote;

import java.util.Date;

public class Note {
    Date date;
    private String title = "";
    private String text = "";
    private String tag = "";

    public Note(String text,Date date){
        this.text = text;
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getText(){
        return text;
    }

    public String getDate(){
        return date.toString();
    }

    public String getTag() {
        return tag;
    }

    public String getTitle() {
        return title;
    }

    public void setText(String text) {
        this.text = text;
    }

}
