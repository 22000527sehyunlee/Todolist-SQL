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

	public int addItem(TodoItem t) {
		String sql = "insert into list(title,memo,category,  current_date,due_date,is_completed, importance , due_time) "+
						"values(?,?,?, ?,?,?, ?,?)";
		PreparedStatement pstmt;
		int count =0;
		try {
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setInt(6, t.getIs_completed());
			pstmt.setInt(7, t.getImportance());
			pstmt.setString(8, t.getDue_time());
			
			count = pstmt.executeUpdate();
			pstmt.close();
	
		}catch (SQLException e) {
			System.out.println("DB연결에 실패하거나 SQL문이 틀렸습니다.");
			System.out.print("사유: " + e.getMessage());
		}
		return count;
	}

	public int deleteItem(int index) {
		String sql = "delete from list where id = ?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
			pstmt.close();			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int editItem(TodoItem t) {
		String sql = "Update list set title=? , memo = ? , category =?, current_date=?, due_date=?, is_completed=?,  importance=? ,due_time=? " +
						"where id=?;";
		PreparedStatement pstmt;
		int count =0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setInt(6, t.getIs_completed());
			pstmt.setInt(7, t.getImportance());
			pstmt.setString(8, t.getDue_time());
			count = pstmt.executeUpdate();
			pstmt.setInt(9, t.getId());
			pstmt.close();
	
		}catch (SQLException e) {
			System.out.println("DB연결에 실패하거나 SQL문이 틀렸습니다.....?");
			System.out.print("사유: " + e.getMessage());
		}
		return count;
	}

	public int completeItem(int num) {
		String sql = "Update list set is_completed=1 where id = ? ;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			count = pstmt.executeUpdate();
			pstmt.close();
	
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int getCount() {
		Statement stmt;
		int count =0;
		try {
			stmt = conn.createStatement();
			String sql = "select count(id) from list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt ("count(id)");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
		
	}
	
	public int getCompCount() {
		Statement stmt;
		int count =0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM list WHERE is_completed = 1;";
			
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt ("count(id)");
			stmt.close();
			
	} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
		
	}

	public ArrayList<String> getCategories(){
	
	ArrayList<String> list = new ArrayList<String>();
	Statement stmt;
	try {
		stmt = conn.createStatement();
		String sql = "SELECT DISTINCT category FROM list";
		ResultSet re = stmt.executeQuery(sql);
		
		while(re.next()) {
			String cate = re.getString("category");
	
			System.out.println(cate +"  ");
				
		}
		
		re.close();
		stmt.close();
	
	}catch (SQLException e) {
		System.out.println("DB연결에 실패하거나 SQL문이 틀렸습니다.");
		System.out.print("사유: " + e.getMessage());
	}
	return list;
	}

	public ArrayList<TodoItem> getOrderedList(String orderby,int ordering) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		String check;
		
		@SuppressWarnings("unused")
		String[] star = {"","*","**","***","****","*****" };
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY "+ orderby;
			if(ordering ==0) 
				sql += " desc";
				ResultSet re = stmt.executeQuery(sql);
				
				while(re.next()) {
					int id = re.getInt("id");
					String cate = re.getString("category");
					String title =re.getString("title");
					String desc =re.getString("memo");
					String current_date =re.getString("current_date");
					String due_date =re.getString("due_date");
					int is_comp = re.getInt("is_completed");
					int importance = re.getInt("importance");
					String due_time =re.getString("due_time");
					TodoItem t = new TodoItem(title, desc, cate, due_date,is_comp,importance, due_time);
					t.setId(id);
					t.setCurrent_date(current_date);
					
					if(is_comp == 1) {
						check = "[V]";
					}else check ="";
					
					System.out.println(id+" ["+cate+"] "+star[importance] +" "+title+ check + " - "+desc+" ("+due_date+ "/ "+due_time+") "+current_date);
				}							
				re.close();
				stmt.close();
					
			}catch (SQLException e) {
				System.out.println("DB연결에 실패하거나 SQL문이 틀렸습니다...");
				System.out.print("사유: " + e.getMessage());
			}
		return list;
			
		
	}

	public ArrayList<TodoItem> getList(int num) { //완료 목록 출력
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		@SuppressWarnings("unused")
		String[] star = {"","*","**","***","****","*****" };
		String check;
		try {
			String sql = "SELECT * FROM list where is_completed = 1";
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String cate = rs.getString("category");
				String title =rs.getString("title");
				String desc =rs.getString("memo");
				String current_date =rs.getString("current_date");
				String due_date =rs.getString("due_date");
				int is_comp = rs.getInt("is_completed");
				int importance = rs.getInt("importance");
				String due_time =rs.getString("due_time");
				TodoItem t = new TodoItem(title, desc, cate, due_date,is_comp,importance, due_time);
				t.setId(id);
				t.setCurrent_date(current_date);
				if(is_comp == 1) {
					check = "[V]";
				}else check ="";
				System.out.println(id+" ["+cate+"] "+star[importance] +" "+title+ check + " - "+desc+" ("+due_date+ "/ "+due_time+") "+current_date);
			}
			
			pstmt.close();
			
		}catch (SQLException e) {
			System.out.println("DB연결에 실패하거나 SQL문이 틀렸습니다.");
			System.out.print("사유: " + e.getMessage());
		}
		return list;
	}

	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		@SuppressWarnings("unused")
		String[] star = {"","*","**","***","****","*****" };
		String check;
		try {
			String sql = "SELECT *FROM list";
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String cate = rs.getString("category");
				String title =rs.getString("title");
				String desc =rs.getString("memo");
				String current_date =rs.getString("current_date");
				String due_date =rs.getString("due_date");
				int is_comp = rs.getInt("is_completed");
				int importance = rs.getInt("importance");
				String due_time =rs.getString("due_time");
				TodoItem t = new TodoItem(title, desc, cate, due_date,is_comp,importance, due_time);
				t.setId(id);
				t.setCurrent_date(current_date);
				if(is_comp == 1) {
					check = "[V]";
				}else check ="";
				System.out.println(id+" ["+cate+"] "+star[importance] +" "+title+ check + " - "+desc+" ("+due_date+ "/ "+due_time+") "+current_date);
			}
			
			pstmt.close();
			
		}catch (SQLException e) {
			System.out.println("DB연결에 실패하거나 SQL문이 틀렸습니다.");
			System.out.print("사유: " + e.getMessage());
		}
		return list;
	}
	
	public ArrayList<TodoItem> getList(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		@SuppressWarnings("unused")
		String[] star = {"","*","**","***","****","*****" };
		String check;
		keyword = "%"+keyword +"%";
		try {
			String sql = "SELECT * from list where title like ? or memo like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet re = pstmt.executeQuery();
			
			while(re.next()) {
				int id = re.getInt("id");
				String cate = re.getString("category");
				String title =re.getString("title");
				String desc =re.getString("memo");
				String current_date =re.getString("current_date");
				String due_date =re.getString("due_date");
				System.out.print("제목과 내용에서 검색한 내용입니다.");
				int is_comp = re.getInt("is_completed");
				int importance = re.getInt("importance");
				String due_time =re.getString("due_time");
				TodoItem t = new TodoItem(title, desc, cate, due_date,is_comp,importance, due_time);
				t.setId(id);
				t.setCurrent_date(current_date);
				if(is_comp == 1) {
					check = "[V]";
				}else check ="   ";
				System.out.println(id+" ["+cate+"] "+star[importance] +" "+title+ check + " - "+desc+" ("+due_date+ "/ "+due_time+") "+current_date);
			}
			re.close();
			pstmt.close();
			
		}catch (SQLException e) {
			System.out.println("DB연결에 실패하거나 SQL문이 틀렸습니다.");
			System.out.print("사유: " + e.getMessage());
		}
		return list;		
	}
	
	public ArrayList<TodoItem> getListCategory (String keyword){
		ArrayList<TodoItem> list =new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		@SuppressWarnings("unused")
		String[] star = {"","*","**","***","****","*****" };
		String check;
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
				int is_comp = re.getInt("is_completed");
				int importance = re.getInt("importance");
				String due_time =re.getString("due_time");
				TodoItem t = new TodoItem(title, desc, cate, due_date,is_comp,importance, due_time);
				t.setId(id);
				t.setCurrent_date(current_date);
				if(is_comp == 1) {
					check = "[V]";
				}else check ="";
				System.out.println(id+" ["+cate+"] "+star[importance] +" "+title+ check + " - "+desc+" ("+due_date+ "/ "+due_time+") "+current_date);
			
			}
			re.close();
			pstmt.close();
			
		}catch (SQLException e) {
			System.out.println("DB연결에 실패하거나 SQL문이 틀렸습니다.");
			System.out.print("사유: " + e.getMessage());
		}
		return list;
	}

	public void sortByName() {
		Collections.sort(list, new TodoSortByName());
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
}
