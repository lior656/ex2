package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.PushService;
import com.parse.ParseUser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TodoDAL {

	private SQLiteDatabase db;


	public TodoDAL(Context context) { 
		int a = 5;//Yes, whenever the underlying database method fails for any reason. It is also testable -- you can try updating an item that doesn't exist, or deleting an item that doesn't exist, or inserting an item with a null title, etc.
		Todo_db_helper dbHelper = new Todo_db_helper(context);
		db = dbHelper.getWritableDatabase();
		Parse.initialize(context, context.getString(R.string.parseApplication), context.getString(R.string.clientKey)); 
		ParseUser.enableAutomaticUser();
	}

	public boolean insert(ITodoItem todoItem) {
		ContentValues values = new ContentValues();
		values.put("title", todoItem.getTitle());
		values.put("due", todoItem.getDueDate().getTime());
		db.insert("todo", null, values);
		
		ParseObject parseObj = new ParseObject("todo");
		parseObj.put("title", todoItem.getTitle());
		parseObj.put("due", todoItem.getDueDate().getTime());
		parseObj.saveInBackground();
		return true;
	}

	public boolean update(ITodoItem todoItem) {
		ContentValues values = new ContentValues();
		values.put("due", todoItem.getDueDate().getTime());
		db.update("todo", values, "title = ?", new String[]{todoItem.getTitle()});
		return true;
	}
	public boolean delete(ITodoItem todoItem) { 
		db.delete("todo", "title = ?", new String[]{todoItem.getTitle()});
		
		ParseObject parseObj = new ParseObject("todo");
		parseObj.put("title", todoItem.getTitle());
		parseObj.put("due", todoItem.getDueDate().getTime());
		parseObj.deleteInBackground();
		return true;
	}
	public List<ITodoItem> all() {
		List<ITodoItem> list = new ArrayList<ITodoItem>();
		Cursor cur = db.query("todo", new String[] { "title", "due" }, null, null, null, null, null);
		if (cur.moveToFirst()) {
			do {
				Date date = new Date(cur.getLong(cur.getColumnIndex("due")) );
				ITodoItem todoItem = new ListItem(cur.getString(cur.getColumnIndex("title")), date);
				list.add(todoItem);
			} while (cur.moveToNext());
		}
		return list;

	}

	public Cursor getDbCursor(){
		return db.query("todo",	new String[] { "_id", "title", "due" },	null, null, null, null, null);
	}
}
