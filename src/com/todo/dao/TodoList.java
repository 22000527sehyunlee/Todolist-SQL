package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	public int getCount() {
		int count =0;
		count++;
		return count;
	}

	public ArrayList<TodoItem> getList(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%"+keyword +"%";
		try {
			String sql = "SELECT *FROM list WHERE title like ? or memo like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,keyword);
			pstmt.setString(2,keyword);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String cate = rs.getString("category");
				String title =rs.getString("title");
				String desc =rs.getString("memo");
				String current_date =rs.getString("current_date");
				String due_date =rs.getString("due_date");
				System.out.println(id+" "+cate+" "+title+" "+desc+" "+due_date+" "+current_date);
				
			}
			
			rs.close();
			pstmt.close();
			conn.close();

			
		}catch (SQLException e) {
			System.out.println("DB연결에 실패하거나 SQL문이 틀렸습니다.");
			System.out.print("사유: " + e.getMessage());
		}
		return list;
	}
	public ArrayList<String> getCategories(){
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT category FROM list";
			ResultSet re = stmt.executeQuery(sql);
			
			while(re.next()) {
				int id = re.getInt("id");
				String cate = re.getString("category");
				String title =re.getString("title");
				String desc =re.getString("memo");
				String current_date =re.getString("current_date");
				String due_date =re.getString("due_date");
				System.out.println(id+" "+cate+" "+title+" "+desc+" "+due_date+" "+current_date);
				
			}
			
			re.close();
			stmt.close();
			conn.close();
			
			
		}catch (SQLException e) {
			System.out.println("DB연결에 실패하거나 SQL문이 틀렸습니다.");
			System.out.print("사유: " + e.getMessage());
		}
		return list;
		}
	public ArrayList<TodoItem> getListCategory (String keyword){
		ArrayList<TodoItem> list =new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list WHERE category = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet re = pstmt.executeQuery();
			
			while(re.next()) {
				int id = re.getInt("id");
				String cate = re.getString("category");
				String title =re.getString("title");
				String desc =re.getString("memo");
				String current_date =re.getString("current_date");
				String due_date =re.getString("due_date");
				System.out.println(id+" "+cate+" "+title+" "+desc+" "+due_date+" "+current_date);
				
			}
			
			re.close();
			pstmt.close();
			conn.close();
			
		}catch (SQLException e) {
			System.out.println("DB연결에 실패하거나 SQL문이 틀렸습니다.");
			System.out.print("사유: " + e.getMessage());
		}
		return list;
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
			BufferedReader br = new BufferedReader (new FileReader ("todolist.txt"));
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

	public ArrayList<TodoItem> getOrderedList(String orderby,int ordering) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY "+ orderby;
			if(ordering ==0) 
				sql += "desc";
				ResultSet re = stmt.executeQuery(sql);
				
				while(re.next()) {
					int id = re.getInt("id");
					String cate = re.getString("category");
					String title =re.getString("title");
					String desc =re.getString("memo");
					String current_date =re.getString("current_date");
					String due_date =re.getString("due_date");
					System.out.println(id+" "+cate+" "+title+" "+desc+" "+due_date+" "+current_date);
					
				}
				
				re.close();
				stmt.close();
				conn.close();
				
			}catch (SQLException e) {
				System.out.println("DB연결에 실패하거나 SQL문이 틀렸습니다.");
				System.out.print("사유: " + e.getMessage());
			}
		return list;
			
		
	}
}
