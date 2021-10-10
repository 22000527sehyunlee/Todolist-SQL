package com.todo.service;

import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

import java.io.*;
import java.text.SimpleDateFormat;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc, category,due_date;
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
		System.out.println("마감일자 > (yyyy/mm/dd)형식으로 입력하세요");
		due_date = sc.nextLine();
  
		TodoItem t = new TodoItem(category,title, desc, due_date, null);
		list.addItem(t);
		System.out.println("추가되었습니다.");
	
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		
		System.out.print("[항목 삭제]\n"+
				"삭제할 항목의 번호을 입력하세요 > ");
		int num =  sc.nextInt();
		int i=1;
		String keyword = null;
		
		for (TodoItem item : l.getList(keyword)) {
			if (l.indexOf(item) == num-1) {
				System.out.println(i+ ". ["+item.getCategory()+ "]: "+item.getTitle()+" - "+ item.getDesc()+" - "+item.getDue_date()+" - "+item.getCurrent_date());
				System.out.print("위 항목을 삭제하시겠습니끼? (y/n) ");
				String yn = sc.next().trim();
				if (yn.equals("y")) {
				l.deleteItem(item);
				System.out.println("삭제되었습니다.");
				break;
				}
			}
		}	
	}

	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목수정]\n"
				+ "수정할 항목의 번호을 입력하세요 > ");
		int num = sc.nextInt();
		int i=1;
		String keyword = null;
		
		for (TodoItem item : l.getList(keyword)) 
			i++;
		
		if (num > i) {
			System.out.println("없는 제목입니다.");
			return;
		}
		System.out.println("새로운 카테고리를 입력하세요 > ");
		String new_category = sc.next().trim();
		
		System.out.println("새로운 제목을 입력하세요 > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목이 중복됩니다.");
			return;
		}
		
		sc.nextLine();
		System.out.println("새로운 내용을 입력하세요 > ");
		String new_description = sc.nextLine().trim();
		System.out.println("새로운 마감일을 입력하세요 (yyyy/mm/dd) > ");
		String new_duedate = sc.nextLine().trim();
		
		
		for (TodoItem item : l.getList(keyword)) {
			if (l.indexOf(item) == num-1) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_category,new_title, new_description,new_duedate, null);
				l.addItem(t);
				System.out.println("수정되었습니다~");
				break;
			}
			
		}

	}

	public static void find(TodoList l) throws IOException { 
		Scanner sc = new Scanner(System.in);
		System.out.println("키워드를 입력하세요");
		String word = sc.nextLine().trim();
		int i=1;
		String keyword = null;
		for (TodoItem myitem : l.getList(keyword)) {
			String a = myitem.toSaveString();
			if ((a.indexOf(word) >=0)) {
				System.out.println(i+ ". ["+myitem.getCategory()+ "]: "+myitem.getTitle()+" - "+ myitem.getDesc()+" - "+myitem.getDue_date()+" - "+myitem.getCurrent_date());
				i++;
			}
		}
	}
	public static void findlist(TodoList l, String keyword) {
		int count =0;
		for(TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다. \n", count);
	}
	public static void findcate(TodoList l) throws IOException { 
		Scanner sc = new Scanner(System.in);
		System.out.println("키워드를 입력하세요");
		String word = sc.nextLine().trim();
		int i=1;
		String keyword = null; 
		
		for (TodoItem myitem : l.getList(keyword)) {
			String a = myitem.toSaveString();
			if ((a.indexOf(word) >=0)&&(a.indexOf(word) <=myitem.getCategory().length())) {
				System.out.println(i+ ". ["+myitem.getCategory()+ "]: "+myitem.getTitle()+" - "+ myitem.getDesc()+" - "+myitem.getDue_date()+" - "+myitem.getCurrent_date());
				i++;
			}
		}
	}

	public static void listAll(TodoList l,String orderby,int oedering) {
		System.out.printf("[전체목록, 총 %d개]\n",l.getCount());
		for (TodoItem item : l.getOrderedList(orderby, oedering)) {
			System.out.println(item.toString());
	  }
	}
	public static void listCateAll(TodoList l) {
		int count =0;
		for (String item : l.getCategories()){
			System.out.print(item + " ");
			count++;
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n",count);
	}
	public static void findCateList(TodoList l,String cate) {
		int count=0;
		for(TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n총 %d개의 항목을 찾았습니다.\n",count);
	}
	
	public static void saveList(TodoList l, String filename) {
		String keyword = null; 
		try {
			FileWriter saveList = new FileWriter("todolist.txt");
			
			for (TodoItem myitem : l.getList(keyword)) {
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
		String title, desc, date, category,due_date;
		
		try {
			//파일은 BufferedReader로 읽기 위한 장치
			FileReader readlistfr = new FileReader(filename);
			BufferedReader readlistBr = new BufferedReader(readlistfr);
			String str = null;
			int count=0;
			
			 while ((str = readlistBr.readLine()) != null) { //한줄씩 읽는 코드
				 if(str.indexOf("##") == -1) break;
				st = new StringTokenizer(str,"##");
				category = st.nextToken();
				title = st.nextToken();
				desc = st.nextToken();
				due_date = st.nextToken();
				date = st.nextToken();
					TodoItem a = new TodoItem(category,title,desc, due_date,date );
					a.setCurrent_date(date);
					l.addItem(a);
					count ++;					
				}
			 readlistBr.close();
			 System.out.println(count+"개의 항목을 읽었습니다.");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
}
