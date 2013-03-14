package il.ac.huji.todolist.test;

import il.ac.huji.todolist.R;
import il.ac.huji.todolist.TodoListManagerActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;

public class ex2simpleTest extends ActivityInstrumentationTestCase2<TodoListManagerActivity> {

	public ex2simpleTest() {
		super(TodoListManagerActivity.class);
	}
	
	public void testStart() { 
		TodoListManagerActivity activity = getActivity(); 
		EditText todoTextGot = (EditText) activity.findViewById(il.ac.huji.todolist.R.id.edtNewItem);
		String newItmStr = todoTextGot.getText().toString();
		String shouldBeStr = "";
		assertEquals(shouldBeStr, newItmStr); 
	}

}
