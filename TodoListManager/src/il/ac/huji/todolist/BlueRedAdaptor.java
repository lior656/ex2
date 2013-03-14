package il.ac.huji.todolist;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BlueRedAdaptor extends ArrayAdapter<ListItem> {
	public BlueRedAdaptor(TodoListManagerActivity activity, List<ListItem> todoList) {
		super(activity, android.R.layout.simple_list_item_1, todoList);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListItem item = getItem(position);
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = inflater.inflate(R.layout.todo_list_row_layout, null);
		TextView mainText = (TextView)view.findViewById(R.id.txtTodoTitle);
		mainText.setText(item._todo_title);
		Date now = new Date();
		if(item._todo_date != null ){
			TextView dateText = (TextView)view.findViewById(R.id.txtTodoDueDate);
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			dateText.setText(formatter.format(item._todo_date));
			if(item._todo_date.before(now)){
				mainText.setTextColor(Color.RED);
			}
		}
		
		return view;
	}

}
