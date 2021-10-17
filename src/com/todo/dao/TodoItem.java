package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
    private String title;
    private String desc;
    private String current_date;
    private String due_date;
    private String category;
    private int id;
    private int is_completed;
    private int importance;
    private String due_time;


	public TodoItem(String category ,String title , String desc, String due_date, int is_completed,int importance, String due_time){
        this.title=title;
        this.desc=desc;
        SimpleDateFormat dateToString = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date =dateToString.format(new Date());
        this.category = category;
        this.due_date = due_date;
        this.is_completed = is_completed;
        this.importance = importance;
        this.due_time = due_time;
        
    }
	public int getImportance() {
		return importance;
	}
	public void setImportance(int importance) {
		this.importance = importance;
	}
	public String getDue_time() {
		return due_time;
	}
	public void setDue_time(String due_time) {
		this.due_time = due_time;
	}
	public int getIs_completed() {
		return is_completed;
	}

	public void setIs_completed(int is_completed) {
		this.is_completed = is_completed;
	}
	public String getTitle() {
        return title;
    }
	public void setTitle(String title) {
        this.title = title;
    }
	public String getCategory() {
        return category;
    }
	public void setCategory(String category) {
        this.category = category;
    }
	public String getDue_date() {
        return due_date;
    }
	public void setDue_date() {
		this.due_date = due_date;    
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
    	//나중에 카테고리나 인셉션 추가되면서 바뀌게될거다.
        return category+"##"+ title + "##" + desc + "##"+ due_date +"##"+ current_date +"\n" ;
    }

    public void setCurrent_date(String current_date) {
        this.current_date =current_date;
    }
    
    public int getId() {
        return id;
    }

	public void setId(int id) {
		this.id = id;
		
	}
}
