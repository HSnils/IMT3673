package com.ntnu.henrik.lab1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class A1 extends AppCompatActivity {

    //extramessage for intent
    public static final String EXTRA_MESSAGE = "com.ntnu.henrik.lab1.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a1);
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
