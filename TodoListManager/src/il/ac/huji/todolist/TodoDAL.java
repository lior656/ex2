package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TodoDAL {

	private SQLiteDatabase db;


	public TodoDAL(Context context) { 
		Todo_db_helper dbHelper = new Todo_db_helper(context);
		db = dbHelper.getWritableDatabase();
		Parse.initialize(context, context.getString(R.string.parseApplication), context.getString(R.string.clientKey)); 
		ParseUser.enableAutomaticUser();
	}

	public boolean insert(ITodoItem todoItem) {
		if(todoItem.getTitle() == null || todoItem.getDueDate()==null) return false;
		ContentValues values = new ContentValues();
		values.put("title", todoItem.getTitle());
		values.put("due", todoItem.getDueDate().getTime());
		if (-1 == db.insert("todo", null, values)) return false;
		
		ParseObject parseObj = new ParseObject("todo");
		parseObj.put("title", todoItem.getTitle());
		parseObj.put("due", todoItem.getDueDate().getTime());
		parseObj.saveInBackground();
		return true;
	}

	public boolean update(ITodoItem todoItem) {
		if(todoItem.getTitle() == null || todoItem.getDueDate()==null) return false;
		final long timeLong = todoItem.getDueDate().getTime();
		ContentValues values = new ContentValues();
		values.put("due", timeLong);
		int numRowsEffected = db.update("todo", values, "title = ?", new String[]{todoItem.getTitle()});
		if (numRowsEffected == 0) return false;
		
		ParseQuery query = new ParseQuery("todo");
		query.whereEqualTo("title", todoItem.getTitle());
		query.findInBackground(new FindCallback() {
		    public void done(List<ParseObject> scoreList, ParseException e) {
		        if (e == null) {
		        	for(ParseObject pObj:scoreList){
		        		pObj.put("due", timeLong);
		        		pObj.saveInBackground();
		        	}
		        } else {
		            System.out.println("-----Error in delete from parse!");
		        }
		    }
		});
		return true;
	}
	
	public boolean delete(ITodoItem todoItem) { 
		if(todoItem.getTitle() == null) return false;
		int numRowsEffected = db.delete("todo", "title = ?", new String[]{todoItem.getTitle()});
		if (numRowsEffected == 0) return false;
		
		ParseQuery query = new ParseQuery("todo");
		query.whereEqualTo("title", todoItem.getTitle());
		query.findInBackground(new FindCallback() {
		    public void done(List<ParseObject> scoreList, ParseException e) {
		        if (e == null) {
		        	for(ParseObject pObj:scoreList){
		        		pObj.deleteInBackground();
		        	}
		        } else {
		            System.out.println("-----Error in delete from parse!");
		        }
		    }
		});
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
