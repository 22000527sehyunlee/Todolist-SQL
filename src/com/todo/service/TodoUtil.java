package com.todo.service;

import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

import java.io.*;
import java.text.SimpleDateFormat;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc, category,due_date,due_time ; int is_comp = 0,importance;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목추가]\n"
				+ "카테고리 > ");
		
		category = sc.next();// 위에서 입력한 엔터를 제거하기 위한 코드
		sc.nextLine();
		System.out.println("제목 > ");
		title = sc.next();// 위에서 입력한 엔터를 제거하기 위한 코드
		if (list.isDuplicate(title)) {
			System.out.printf("제목이 중복됩니다.");
			return;
		}
		
		sc.nextLine();
		System.out.println("내용 > ");
		desc = sc.nextLine().trim();
		
		System.out.println("중요도 > ");
		importance = sc.nextInt();sc.nextLine();
		System.out.println("마감일자 > (yyyy/mm/dd)형식으로 입력하세요");
		due_date = sc.next(); 
		System.out.println("마감시간 > (00:00)형식으로 입력하세요");
		due_time = sc.next();
  
		TodoItem t = new TodoItem(category,title, desc, due_date,is_comp,importance, due_time);
		if(list.addItem(t)>0)
			System.out.println("추가되었습니다.");
	
	}

	public static void deleteItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
	
		System.out.print("[항목 삭제]\n"+
				"삭제할 항목의 번호을 입력하세요 > ");
		int num =  sc.nextInt();
		if(l.deleteItem(num)>0)
				System.out.println("삭제되었습니다.");
	}

	public static void completeItem(TodoList l, int num) {
		
		if(l.completeItem(num)>0)
				System.out.println("완료되었습니다.");
		
		
	}

	public static void updateItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목수정]\n"
				+ "수정할 항목의 번호을 입력하세요 > ");
		int index = sc.nextInt();
		

		System.out.println("새로운 카테고리를 입력하세요 > ");
		String new_category = sc.next().trim();
		sc.nextLine();
		
		System.out.println("새로운 제목을 입력하세요 > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목이 중복됩니다.");
			return;
		}
		
		sc.nextLine();
		System.out.println("새로운 내용을 입력하세요 > ");
		String new_description = sc.nextLine().trim();
		System.out.println("중요도 > ");
		int importance = sc.nextInt();
		System.out.println("새로운 마감일을 입력하세요 (yyyy/mm/dd) > ");
		String new_duedate = sc.next();;
		System.out.println("마감시간 > (00:00)형식으로 입력하세요");
		String due_time = sc.next();
		
		
				int is_comp = 0;
				TodoItem t = new TodoItem(new_category,new_title, new_description,new_duedate, is_comp,importance, due_time);
				t.setId(index);
				if(l.editItem(t)>0)
					System.out.println("수정되었습니다~");
			}

	public static void find(TodoList l) throws IOException { 
		Scanner sc = new Scanner(System.in);
		System.out.println("키워드를 입력하세요");
		String word = sc.nextLine().trim();
		int i=1;
		String keyword = null;
		for (TodoItem myitem : l.getList()) {
			String a = myitem.toSaveString();
			if ((a.indexOf(word) >=0)) {
				System.out.println(i+ ". ["+myitem.getCategory()+ "]: "+myitem.getTitle()+" - "+ myitem.getDesc()+" - "+myitem.getDue_date()+" - "+myitem.getCurrent_date());
				i++;
			}
		}
	}
	public static void findcate(TodoList l) throws IOException { 
		Scanner sc = new Scanner(System.in);
		System.out.println("키워드를 입력하세요");
		String word = sc.nextLine().trim();
		int i=1;

		
		for (TodoItem myitem : l.getList()) {
			String a = myitem.toSaveString();
			if ((a.indexOf(word) >=0)&&(a.indexOf(word) <=myitem.getCategory().length())) {
				System.out.println(i+ ". ["+myitem.getCategory()+ "]: "+myitem.getTitle()+" - "+ myitem.getDesc()+" - "+myitem.getDue_date()+" - "+myitem.getCurrent_date());
				i++;
			}
		}
	}

	public static void listAll(TodoList l,String orderby,int ordering) {
		System.out.printf("[전체목록, 총 %d개]\n",l.getCount());
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
	  }
	}
	public static void listAll(TodoList l,int num) { //완료 체크 기능 관련
		for (TodoItem item : l.getList(num)) {
			System.out.println(item.toString());

	  }
		System.out.printf("총 %d개의 항목이 완료되었습니다.\n",l.getCompCount());
	}

	public static void listCateAll(TodoList l) { //카테고리 출력
		for (String item : l.getCategories()){
			System.out.print(item + " ");
		}
	}
	public static void findlist(TodoList l,String keyword) {
	
		for(TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
		}
	}

	public static void findCateList(TodoList l,String cate) {

		for(TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());

		}
	
	}
	
	
	
	
}
