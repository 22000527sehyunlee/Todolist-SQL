package com.todo.dao;

import java.util.*;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	private List<TodoItem> list;

	public TodoList() {
		this.list = new ArrayList<TodoItem>();
	}

	public void addItem(TodoItem t) {
		list.add(t);
	}

	public void deleteItem(TodoItem t) {
		list.remove(t);
	}

	void editItem(TodoItem t, TodoItem updated) {
		int index = list.indexOf(t);
		list.remove(index);
		list.add(updated);
	}

	public ArrayList<TodoItem> getList() {
		return new ArrayList<TodoItem>(list);
	}

	public void sortByName() {
		Collections.sort(list, new TodoSortByName());
	}

	public void listAll() {
		System.out.println("[전체목록]");
		int i=1;
		for (TodoItem myitem : list) {
			System.out.println(i+". ["+ myitem.getCategory()+"] : "+ myitem.getTitle()+"Duedate: "+myitem.getDue_date()+" - "+ myitem.getDesc()+"-"+myitem.getCurrent_date());			
		i++;
		}
	}	
	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}

	public Boolean isDuplicate(String new_title) {
		for (TodoItem item : list) {
			if (new_title == (item.getTitle())) return true;
		}
		return false;
	}
}
