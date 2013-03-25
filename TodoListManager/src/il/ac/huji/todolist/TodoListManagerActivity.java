package il.ac.huji.todolist;

import java.util.Date;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

public class TodoListManagerActivity extends Activity {

	
	private RedIsOldDbAdaptor listTODOadapter;
	private  ListView listTodo;
	private TodoDAL dbControll;
	private Cursor cursor;
	private String callStr = " ";
	private final int add_result_num = 656;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        callStr =  getString(R.string.call_str_no_space) + callStr;
        
        listTodo = (ListView)findViewById(R.id.lstTodoItems);     
        dbControll = new TodoDAL(this);
		cursor = dbControll.getDbCursor();
		String[] from = { "title", "due" };
		int[] to = { R.id.txtTodoTitle, R.id.txtTodoDueDate };
		listTODOadapter = new RedIsOldDbAdaptor(this, cursor, from, to);
		listTodo.setAdapter(listTODOadapter);
		
		registerForContextMenu(listTodo);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo_list_manager, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    		case R.id.menuItemAdd:
    			Intent intent = new Intent(this, AddNewTodoItemActivity.class);
    			startActivityForResult(intent, add_result_num);
    			break;
    	}
    	return true;
    }
    
    @SuppressWarnings("deprecation")
	protected void onActivityResult(int reqCode, int resCode, Intent data) {
    	switch (reqCode) {
    	case add_result_num:
    		if(resCode == RESULT_OK ){
    			ITodoItem item = new ListItem(data.getStringExtra("title") , (Date) data.getSerializableExtra("dueDate"));
    			dbControll.insert(item);
    			cursor.requery();
    		}
    		break;
    	}
    }
    
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.todo_list_context_menu, menu);
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		
		Cursor cur = (Cursor) listTODOadapter.getItem(info.position);
        String str = cur.getString(cur.getColumnIndex("title"));
		menu.setHeaderTitle(str);
		
      
		if( str.startsWith(callStr)){
			menu.findItem(R.id.menuItemCall).setTitle(str);
			menu.findItem(R.id.menuItemCall).setVisible(true);
			menu.findItem(R.id.menuItemCall).setEnabled(true);
		}else {
			menu.findItem(R.id.menuItemCall).setVisible(false);
			menu.findItem(R.id.menuItemCall).setEnabled(false);	
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		Cursor cur = (Cursor) listTODOadapter.getItem(info.position);
		if (cur.isClosed()){
			return true;
		}
		
		switch (item.getItemId()){
			case R.id.menuItemDelete:
				ITodoItem todoItem = new ListItem(cur.getString(cur.getColumnIndex("title")) , null);
    			dbControll.delete(todoItem);
				cursor.requery();
				break;
			case R.id.menuItemCall:
				String str = cur.getString(cur.getColumnIndex("title"));
				String call = "tel:" + str.substring(callStr.length(), str.length());
				Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse(call));
				startActivity(dial);
				break;
		}
		return true;
	}

}
