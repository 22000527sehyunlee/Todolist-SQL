package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("<ToDoList 명령어 안내>");
        System.out.println("1. 항목 추가 ( add )");
        System.out.println("2. 항목 삭제 ( del )");
        System.out.println("3. 항목 수정  ( edit )");
        System.out.println("4. 전체 목록 ( ls )");
        System.out.println("5. 제목순 정렬 ( ls_name_asc )");
        System.out.println("6. 제목역순 정렬 ( ls_name_desc )");
        System.out.println("7. 날짜순 정렬 ( ls_date_asc )");
        System.out.println("8. 날짜역순 정렬 ( ls_date_desc )");
        System.out.println("9. 검색하기 ( find )");
        System.out.println("10. 종료하기 ( exit )");
        System.out.println("11. 명령어 열기 ( help )");
        System.out.println("입력하면 실행합니다 ");
    }

	public static void prompt() {
		// TODO Auto-generated method stub
		System.out.print("\nCommand > ");
		
	}
}
