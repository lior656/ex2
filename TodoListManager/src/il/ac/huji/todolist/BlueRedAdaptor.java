package il.ac.huji.todolist;

import java.util.List;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BlueRedAdaptor extends ArrayAdapter<String> {
	public BlueRedAdaptor(TodoListManagerActivity activity, List<String> todoList) {
		super(activity, android.R.layout.simple_list_item_1, todoList);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String str = getItem(position);
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = inflater.inflate(R.layout.todo_list_row_layout, null);
		TextView mainText = (TextView)view.findViewById(R.id.main_text);
		mainText.setText(str);
		if((position %2) == 0){
			mainText.setTextColor(Color.RED);
		} else{
			mainText.setTextColor(Color.BLUE);
		}
		return view;
	}

}
