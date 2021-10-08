package com.todo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() throws IOException {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		l.importData("todolist.txt");
		
		boolean isList = false;
		boolean quit = false;
		
		
		
		Menu.displaymenu();
		TodoUtil.loadList(l, "todolist.txt");
		do {
			Menu.prompt();
			isList = false;
			String choice = sc.next();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name_asc":
				l.sortByName();
				System.out.println("제목순으로 정렬하였습니다.");
				isList = true;
				break;

			case "ls_name_desc":
				l.sortByName();
				l.reverseList();
				System.out.println("제목역순으로 정렬하였습니다.");
				isList = true;
				break;
				
			case "ls_date_asc":
				l.sortByDate();
				System.out.println("날짜순으로 정렬하였습니다.");
				isList = true;
				break;
			case "ls_date_desc":
				l.sortByDate();
				l.reverseList();
				System.out.println("날짜역순으로 정렬하였습니다.");
				isList = true;
				break;
				
			case "find":
				TodoUtil.find(l);
				
				System.out.println("입력한 단어가 포함된 항목을 출력 했습니다.");
				break;
			case "find_cate":
				TodoUtil.findcate(l);
				
				System.out.println("입력한 단어가 포함된 항목을 출력 했습니다.");
				break;
				
			case "help":
				Menu.displaymenu();
				break;
			
			case "exit":
				TodoUtil.saveList(l, "todolist.txt");
				quit = true;
				break;

			default:
				System.out.println("명령어를 다시 입력하세요. (도움말 - help)");
				break;
			}
			
			if(isList) l.listAll();
		} while (!quit);
	}
}
