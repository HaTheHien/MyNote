package com.example.mynote;

import java.util.ArrayList;
import java.util.Date;

public class Note {
    Date date;
    private String title = "";
    private String text = "";
    public ArrayList<String> tag = null;

    public Note(String text,Date date){
        this.text = text;
        this.date = date;
    }

    public Note(Note a)
    {
        date = a.date;
        title = a.title;
        text = a.text;
        tag = new ArrayList<String>(a.tag);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTag(ArrayList<String> tag) {
        this.tag = tag;
    }

    public String getText(){
        return text;
    }

    public String getDate(){
        return date.toString();
    }

    public ArrayList<String> getTag() {
        return tag;
    }

    public String getTitle() {
        return title;
    }

    public void setText(String text) {
        this.text = text;
    }

}
