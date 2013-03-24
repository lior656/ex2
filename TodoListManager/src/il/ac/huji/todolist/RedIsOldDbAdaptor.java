package il.ac.huji.todolist;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;



public class RedIsOldDbAdaptor extends SimpleCursorAdapter {

	@SuppressWarnings("deprecation")
	public RedIsOldDbAdaptor(Context context, Cursor c,	String[] from, int[] to) {
		super(context, R.layout.todo_list_row_layout, c, from, to);

	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		long dateLong = cursor.getLong(cursor.getColumnIndex("due"));
		String title = cursor.getString(cursor.getColumnIndex("title"));
		TextView mainText = (TextView)view.findViewById(R.id.txtTodoTitle);
		mainText.setText(title);

		Date todo_date = new Date(dateLong);
		Date now = new Date();
		TextView dateText = (TextView)view.findViewById(R.id.txtTodoDueDate);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateStr = formatter.format(todo_date);
		dateText.setText(dateStr);
		String nowInStr = formatter.format(now);
		if(todo_date.before(now) && !dateStr.equals(nowInStr)){
			mainText.setTextColor(Color.RED);
			dateText.setTextColor(Color.RED);
		}else {
			mainText.setTextColor(Color.BLACK);
			dateText.setTextColor(Color.BLACK);
		}

	}

}
