package il.ac.huji.todolist;

import java.util.Date;

public class ListItem {
	public String _todo_title;
	public Date _todo_date;
	
	
	public ListItem(String addStr, Date todo_date) {
		_todo_title = addStr;
		_todo_date = todo_date;
	}	

}
