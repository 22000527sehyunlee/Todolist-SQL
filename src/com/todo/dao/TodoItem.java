package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
    private String title;
    private String desc;
    private String current_date;


    public TodoItem(String title, String desc){
        this.title=title;
        this.desc=desc;
        Date date = new Date();
        SimpleDateFormat dateToString = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        current_date =dateToString.format(date);
    }
    

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
        
        return current_date;
    }
    
    public String toSaveString() {
    	
        return title + "##" + desc + "##"+ current_date +"\n" ;
    }


    public void setCurrent_date(String current_date) {
    	SimpleDateFormat dateToString = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String to =dateToString.format(current_date);
        this.current_date = to;
    }
}
