package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoListManagerActivity extends Activity {

	
	private ArrayAdapter<ListItem> listTODOadapter;
	private EditText todoText;
	private  ListView listTodo;
	List<ListItem> todoList;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        
        todoText = (EditText) findViewById(R.id.edtNewItem);
        todoList = new ArrayList<ListItem>();
        listTodo = (ListView)findViewById(R.id.lstTodoItems);
        
        listTODOadapter = new BlueRedAdaptor(this, todoList);
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
    			String addStr = todoText.getText().toString();
    			listTODOadapter.add(new ListItem(addStr , null));//TODO
    			break;
    	}
    	return true;
    }
    
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.todo_list_context_menu, menu);
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
        int pos = info.position;
        String str = listTODOadapter.getItem(pos)._todo_title;
		menu.setHeaderTitle(str);
		
      
		if( str.startsWith("Call ")){
			menu.getItem(R.id.menuItemCall).setVisible(true);
			menu.getItem(R.id.menuItemCall).setEnabled(true);
		}else {
			menu.getItem(R.id.menuItemCall).setVisible(false);
			menu.getItem(R.id.menuItemCall).setEnabled(false);	
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
					
    	        }
				break;
		}
		return true;
	}

}
