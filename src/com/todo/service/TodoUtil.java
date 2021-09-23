package com.todo.service;

import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

import java.io.*;
import java.text.SimpleDateFormat;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목추가]\n"
				+ "제목 > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("제목이 중복됩니다.");
			return;
		}
		
		sc.nextLine();
		System.out.println("내용 > ");
		desc = sc.nextLine().trim();
  
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		
		System.out.print("[항목 삭제]\n"+
				"삭제할 항목의 제목을 입력하세요 > ");
		String title =  sc.next();
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("삭제되었습니다.");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목수정]\n"
				+ "수정할 항목의 제목을 입력하세요 > ");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("없는 제목입니다.");
			return;
		}

		System.out.println("새로운 제목을 입력하세요 > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목이 중복됩니다.");
			return;
		}
		
		sc.nextLine();
		System.out.println("새로운 내용을 입력하세요 > ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("수정되었습니다~");
			}
		}

	}

	public static void listAll(TodoList l) throws IOException {
		System.out.println("[전체목록]");
		for (TodoItem myitem : l.getList()) {
			System.out.println("["+myitem.getTitle() +"]: "+ myitem.getDesc()+" - "+myitem.getCurrent_date());
		}
	}
	
	public static void saveList(TodoList l, String filename) {
		try {
			FileWriter saveList = new FileWriter("C:\\Users\\leesj\\git\\TodoListApp\\src\\todolist.txt");
			
			for (TodoItem myitem : l.getList()) {
				String a = myitem.toSaveString();
				saveList.write(a);
			}
			
			saveList.close();
		} catch (FileNotFoundException e) {
			// 예외처리
			e.printStackTrace();
		} catch (IOException e) { 
			// try의 ioException이 unhandle됐다고 뜨면 이부분 추가한다.
			System.out.println("잘못된 입출력입니다.");
		}
		System.out.println("저장되었습니다.");
	}
	public static void loadList(TodoList l,String filename) throws IOException {		
		StringTokenizer st = null;
		String title, desc, date ;
		
		try {
			FileReader readlistfr = new FileReader(filename);
			BufferedReader readlistBr = new BufferedReader(readlistfr);
			String str = null;
			
			 while ((str = readlistBr.readLine()) != null) {
				 if(str.indexOf("##") == -1) break;
				st = new StringTokenizer(str,"##");
				title = st.nextToken();
				desc = st.nextToken();
				date = st.nextToken();
					TodoItem a = new TodoItem(title,desc);
					l.addItem(a);
					System.out.println(a+"\n");
				}
			 

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
