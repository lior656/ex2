package il.ac.huji.todolist;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
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
        			DatePicker dateGot = (DatePicker) findViewById(R.id.datePicker);
					@SuppressWarnings("deprecation")
					Date date = new Date(dateGot.getYear() - 1900, dateGot.getMonth(), dateGot.getDayOfMonth());
        			
        			result.putExtra("title", todoTextStr); 
        			result.putExtra("dueDate", date); 
        			setResult(RESULT_OK, result);
        		}
        		finish();
        	}
        });
    }


}
