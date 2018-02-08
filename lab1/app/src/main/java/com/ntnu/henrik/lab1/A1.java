package com.ntnu.henrik.lab1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class A1 extends AppCompatActivity {

    //extramessage for intent
    public static final String EXTRA_MESSAGE = "com.ntnu.henrik.lab1.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a1);

        //finds the dropdown-menu
        Spinner spinner = (Spinner) findViewById(R.id.L1);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.L1_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }

    //OnSelected listener for dropdown
    public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }

    //onclick function on button A1 to go to A2
    public void onClickButtonA1(View view){

        //creates intent
        Intent intent = new Intent(this, A2.class);

        //gets the text from T1
        EditText editText = (EditText) findViewById(R.id.T1);
        String message = editText.getText().toString();

        //puts the message in intent
        intent.putExtra(EXTRA_MESSAGE, message);

        //starts A2
        startActivity(intent);
    }
}
