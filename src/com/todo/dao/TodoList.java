package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

import com.todo.service.DbConnection;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	private List<TodoItem> list;
	Connection conn;

	public TodoList() {
		this.list = new ArrayList<TodoItem>();
		this.conn = DbConnection.getConnection();
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
	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader (new FileReader (filename));
			String line;
			String sql = "insert into list (title, memo, category, current_date, due_date)"
						+"values (?,?,?,?,?);";
			int records = 0;
			while((line = br.readLine())!= null) {
				StringTokenizer st = new StringTokenizer(line,"##");
				String category = st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,title);
				pstmt.setString(2,description);
				pstmt.setString(3,category);
				pstmt.setString(4,current_date);
				pstmt.setString(5,due_date);
				int count = pstmt.executeUpdate();
				if(count > 0) records ++;
				pstmt.close();
			}
			System.out.println(records +" records read!!");
			br.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
