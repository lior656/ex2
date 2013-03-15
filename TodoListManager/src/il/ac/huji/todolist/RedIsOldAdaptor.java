package il.ac.huji.todolist;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RedIsOldAdaptor extends ArrayAdapter<ListItem> {
	public RedIsOldAdaptor(TodoListManagerActivity activity, List<ListItem> todoList) {
		super(activity, android.R.layout.simple_list_item_1, todoList);
	}
	
	@SuppressLint("SimpleDateFormat")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListItem item = getItem(position);
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = inflater.inflate(R.layout.todo_list_row_layout, null);
		TextView mainText = (TextView)view.findViewById(R.id.txtTodoTitle);
		mainText.setText(item._todo_title);
		if(item._todo_date != null ){
			Date now = new Date();
			TextView dateText = (TextView)view.findViewById(R.id.txtTodoDueDate);
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String dateStr = formatter.format(item._todo_date);
			dateText.setText(dateStr);
			String nowInStr = formatter.format(now);
			if(item._todo_date.before(now) && !dateStr.equals(nowInStr)){
				mainText.setTextColor(Color.RED);
				dateText.setTextColor(Color.RED);
			}
		}
		
		return view;
	}

}
