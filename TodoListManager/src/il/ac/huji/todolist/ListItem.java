package il.ac.huji.todolist;

import java.util.Date;

public class ListItem implements ITodoItem{
	public String _todo_title;
	public Date _todo_date;
	
	
	public ListItem(String addStr, Date todo_date) {
		_todo_title = addStr;
		_todo_date = todo_date;
	}


	@Override
	public String getTitle() {
		return _todo_title;
	}


	@Override
	public Date getDueDate() {
		return _todo_date;
	}	

}
