package il.ac.huji.todolist;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;



public class RedIsOldDbAdaptor extends SimpleCursorAdapter {

	public RedIsOldDbAdaptor(Context context, Cursor c,	String[] from, int[] to) {
		super(context, R.layout.todo_list_row_layout, c, from, to);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		  final View row = super.getView(position, convertView, parent);
		  
	        if (position % 2 == 0)
	            row.setBackgroundResource(android.R.color.darker_gray);
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
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
	    long color = cursor.getInt(cursor.getColumnIndex("due"));
	    view.get;
	    String label = cursor.getString(cursor.getColumnIndex("GenreLabel"));
	    TextView text = (TextView) findViewById(R.id.genre_label);
	    text.setText(label);
	}

}
