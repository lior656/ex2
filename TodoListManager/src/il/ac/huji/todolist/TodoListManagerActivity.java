package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoListManagerActivity extends Activity {

	
	private ArrayAdapter<String> listTODOadapter;
	private EditText todoText;
	private  ListView listTodo;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        
        todoText = (EditText) findViewById(R.id.edtNewItem);
        List<String> todoList = new ArrayList<String>();
        listTodo = (ListView)findViewById(R.id.lstTodoItems);
        
        listTODOadapter = new BlueRedAdaptor(this, todoList);
        listTodo.setAdapter(listTODOadapter);

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
    			listTODOadapter.add(addStr);
    			break;
    		case R.id.menuItemDelete:
    	        int cheackedPos = listTodo.getCheckedItemPosition();
    	        if (cheackedPos !=  AdapterView.INVALID_POSITION && cheackedPos < listTODOadapter.getCount()){
    	        	listTODOadapter.remove(listTODOadapter.getItem(cheackedPos));
    	        }
    			break;
    	}
    	return true;
    }
}
