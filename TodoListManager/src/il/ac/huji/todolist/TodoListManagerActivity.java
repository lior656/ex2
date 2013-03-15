package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TodoListManagerActivity extends Activity {

	
	private ArrayAdapter<ListItem> listTODOadapter;
	private  ListView listTodo;
	private String callStr = " ";
	private final int add_result_num = 656;
	List<ListItem> todoList;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        callStr =  getString(R.string.call_str_no_space) + callStr;
        
        todoList = new ArrayList<ListItem>();
        listTodo = (ListView)findViewById(R.id.lstTodoItems);
        
        listTODOadapter = new RedIsOldAdaptor(this, todoList);
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
    
    protected void onActivityResult(int reqCode, int resCode, Intent data) {
    	switch (reqCode) {
    	case add_result_num:
    		if(resCode == RESULT_OK ){
    			String todo_title = data.getStringExtra("title");
    			Date date = (Date) data.getSerializableExtra("dueDate");
    			listTODOadapter.add(new ListItem(todo_title, date)); 
    		}
    		break;
    	}
    }
    
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.todo_list_context_menu, menu);
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
        int pos = info.position;
        String str = listTODOadapter.getItem(pos)._todo_title;
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

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		int cheackedPos = info.position;
		switch (item.getItemId()){
			case R.id.menuItemDelete:
				if (cheackedPos !=  AdapterView.INVALID_POSITION && cheackedPos < listTODOadapter.getCount()){
    	        	listTODOadapter.remove(listTODOadapter.getItem(cheackedPos));
    	        }
				break;
			case R.id.menuItemCall:
				if (cheackedPos !=  AdapterView.INVALID_POSITION && cheackedPos < listTODOadapter.getCount()){
					String str = listTODOadapter.getItem(cheackedPos)._todo_title;
					String call = "tel:" + str.substring(callStr.length(), str.length());
					Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse(call));
					startActivity(dial);
    	        }
				break;
		}
		return true;
	}

}
