package il.ac.huji.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddNewTodoItemActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_todo_layout);
        
        Button cancelButton = (Button) findViewById(R.id.btnCancel);
        cancelButton.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View arg0) {
        		setResult(RESULT_CANCELED);
        		finish();
        	}
        });
        
        
        
        Button okButton = (Button) findViewById(R.id.btnOK);
        okButton.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View arg0) {
        		EditText todoText = (EditText) findViewById(R.id.edtNewItem);
        		String todoTextStr = todoText.getText().toString();
        		if(todoTextStr != null) {
        			Intent result = new Intent();
        			result.putExtra("title", todoTextStr); //TODO add "dueDate" of type java.util.Date
        			setResult(RESULT_OK, result);
        		}
        		finish();
        	}
        });
    }


}
